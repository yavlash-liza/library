package by.library.yavlash.service.impl;

import by.library.yavlash.converter.UserConverter;
import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.entity.User;
import by.library.yavlash.exception.ServiceException;
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

    @Override
    public UserDto findUserById(Long userId) throws ServiceException {
        Optional<User> user = userRepository.findById(userId);
        return user.map(UserConverter::toDto)
                .orElseThrow(() -> new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), "was not found")));
    }

    @Override
    public List<UserListDto> findAllUsers() throws ServiceException {
        try {
            List<User> users = userRepository.findAll();
            return UserConverter.toListDtos(users);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }

    @Override
    public boolean addUser(UserSaveDto userSaveDto) throws ServiceException {
        try {
            User user = UserConverter.fromSaveDto(userSaveDto);
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not added "));
        }
    }

    @Override
    public boolean updateUser(UserDto userDto) throws ServiceException {
        try {
            User user = UserConverter.fromDto(userDto);
            userRepository.save(user);
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not updated "));
        }
    }

    @Override
    public boolean deleteUser(Long userId) throws ServiceException {
        try {
            Optional<User> user = userRepository.findById(userId);
            user.ifPresent(value -> value.setDeleted(true));
            return true;
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " was not deleted "));
        }
    }
}