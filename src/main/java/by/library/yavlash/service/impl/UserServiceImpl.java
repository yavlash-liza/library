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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public UserDto findById(Long userId) throws ServiceException {
        return userRepository.findById(userId).map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    @Transactional
    public List<UserListDto> findAll() throws ServiceException {
        try {
            List<User> users = userRepository.findAll();
            return userMapper.toListDto(users);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "), exception);
        }
    }

    @Override
    @Transactional
    public boolean add(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = userMapper.fromSaveDto(userSaveDto);
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "), exception);
        }
    }

    @Override
    @Transactional
    public boolean update(UserSaveDto userSaveDto) throws ServiceException {
        User user = userRepository.findById(userSaveDto.getId())
                .orElseThrow(() -> new ServiceException(
                        String.format("%s:{%s}", getClass().getSimpleName(), " was not updated and was not found.")
                ));
        try {
            settingUpdateFields(user, userSaveDto);
            userRepository.flush();
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s:{%s}", getClass().getSimpleName(), "was not updated. "), exception);
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
    @Transactional
    public boolean softDelete(Long userId) throws ServiceException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(
                        String.format("%s:{%s}", getClass().getSimpleName(), " was not softly deleted and was not found.")
                ));
        try {
            user.setDeleted(true);
            userRepository.flush();
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s:{%s}", getClass().getSimpleName(), " was not softly deleted."), exception);
        }
    }
}