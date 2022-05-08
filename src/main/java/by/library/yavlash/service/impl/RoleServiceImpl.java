package by.library.yavlash.service.impl;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.RoleMapper;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public List<RoleDto> findAll() throws ServiceException {
        try {
            List<Role> roles = roleRepository.findAll();
            return roleMapper.toListDto(roles);
        } catch (Exception exception) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), " were not found "), exception);
        }
    }
}