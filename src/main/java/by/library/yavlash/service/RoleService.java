package by.library.yavlash.service;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.exception.ServiceException;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAllRoles() throws ServiceException;
}