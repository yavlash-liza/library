package by.library.yavlash.service.impl;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.RoleMapper;
import by.library.yavlash.mapper.UserMapper;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final static String USER_CACHE = "users";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    @Cacheable(value = USER_CACHE, key = "#userId")
    @Transactional
    public UserDto findById(Long userId) {
        return userRepository.findById(userId).map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("User was not found. id = %d", userId)));
    }

    @Override
    @Cacheable(value = USER_CACHE)
    @Transactional
    public List<UserListDto> findAll() {
        try {
            List<User> users = userRepository.findAll();
            return userMapper.toListDto(users);
        } catch (Exception e) {
            throw new ServiceException("Users were not found.", e);
        }
    }

    @Override
    @CacheEvict(value = USER_CACHE, key = "{ #root.methodName, #page, #size,  #deleted }")
    @Transactional
    public List<UserListDto> findListUsers(int page, int size, boolean deleted) {
        try {
            PageRequest pageReq = PageRequest.of(page, size);
            Page<UserListDto> bookCopyListDtos = userRepository.findAllByDeleted(deleted, pageReq)
                    .map(userMapper::toListDto);
            return bookCopyListDtos.getContent();
        } catch (Exception e) {
            throw new ServiceException("Users were not found.", e);
        }
    }

    @Override
    @CacheEvict(value = USER_CACHE, key = "{ #root.methodName, #page, #size,  #deleted, #search? }")
    @Transactional
    public List<UserListDto> findListUsersBySearch(int page, int size, boolean deleted, String search) {
        try {
            PageRequest pageReq = PageRequest.of(page, size);
            Page<UserListDto> bookCopyListDtos = userRepository.findAllByDeletedAndLastName(deleted, search, pageReq)
                    .map(userMapper::toListDto);
            return bookCopyListDtos.getContent();
        } catch (Exception e) {
            throw new ServiceException("Users were not found.", e);
        }
    }

    @Override
    @CacheEvict(value = USER_CACHE, key = "#userSaveDto.id")
    @Transactional
    public boolean add(UserSaveDto userSaveDto) {
        try {
            User user = userMapper.fromSaveDto(userSaveDto);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("User was not saved. %s", userSaveDto), e);
        }
    }

    @Override
    @CacheEvict(value = USER_CACHE, key = "#userSaveDto.id")
    @Transactional
    public boolean update(UserSaveDto userSaveDto) {
        User user = userRepository.findById(userSaveDto.getId())
                .orElseThrow(() -> new ServiceException(
                        String.format("User was not updated. User was not found. id = %d", userSaveDto.getId())
                ));
        try {
            settingUpdateFields(user, userSaveDto);
            userRepository.flush();
        } catch (Exception e) {
            throw new ServiceException(String.format("User was not updated. %s", userSaveDto), e);
        }
        return true;
    }

    private void settingUpdateFields(User beforeUpdate, UserSaveDto saveDto) {
        if (saveDto.getFirstName() != null) {
            beforeUpdate.setFirstName(saveDto.getFirstName());
        }
        if (saveDto.getLastName() != null) {
            beforeUpdate.setLastName(saveDto.getLastName());
        }
        if (saveDto.getPassportNumber() != null) {
            beforeUpdate.setPassportNumber(saveDto.getPassportNumber());
        }
        if (saveDto.getEmail() != null) {
            beforeUpdate.setEmail(saveDto.getEmail());
        }
        if (saveDto.getAddress() != null) {
            beforeUpdate.setAddress(saveDto.getAddress());
        }
        if (saveDto.getBirthDate() != null) {
            beforeUpdate.setBirthDate(saveDto.getBirthDate());
        }
        if (saveDto.getRoleId() != null) {
            beforeUpdate.setRoles(roleMapper.fromListLong(saveDto.getRoleId()));
        }
    }

    @Override
    @CacheEvict(value = USER_CACHE, key = "#userId")
    @Transactional
    public boolean softDelete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(
                        String.format("User was not softly deleted. User was not found. id = %d", userId)
                ));
        try {
            user.setDeleted(true);
            userRepository.flush();
            return true;
        } catch (Exception e) {
            throw new ServiceException(String.format("User was not softly deleted. id = %d", userId), e);
        }
    }
}