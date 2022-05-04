package by.library.yavlash.service.impl;

import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.UserMapper;
import by.library.yavlash.repository.UserRepository;
import by.library.yavlash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findById(Long userId) throws ServiceException {
        return userRepository.findById(userId).map(userMapper::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<UserListDto> findAll() throws ServiceException {
        try {
            List<User> users = userRepository.findAll();
            return userMapper.toListDto(users);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean add(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = userMapper.fromSaveDto(userSaveDto);
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean update(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = userMapper.fromSaveDto(userSaveDto);
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not updated "));
        }
    }

    @Override
    public boolean delete(Long userId) throws ServiceException {
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            user.setDeleted(true);
            userRepository.save(user);
            return true;
        } else {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}