package by.library.yavlash.service.impl;

import by.library.yavlash.converter.RoleConverter;
import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.service.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAllRoles() throws ServiceException {
        try {
            List<Role> roles = roleRepository.findAll();
            return RoleConverter.toListDto(roles);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s were not found: {%s}", getClass().getSimpleName(), exception.getMessage()));
        }
    }
}