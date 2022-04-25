package by.library.yavlash.service.impl;

import by.library.yavlash.converter.RoleConverter;
import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() throws ServiceException {
        try {
            List<Role> roles = roleRepository.findAll();
            return RoleConverter.toListDto(roles);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "));
        }
    }
}