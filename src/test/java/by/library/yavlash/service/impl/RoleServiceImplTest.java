package by.library.yavlash.service.impl;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.exception.RepositoryException;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.RoleMapperImpl;
import by.library.yavlash.repository.RoleRepository;
import by.library.yavlash.repository.impl.RoleRepositoryImpl;
import by.library.yavlash.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    private final RoleRepository roleRepository;
    private final RoleService roleService;

    public RoleServiceImplTest() {
        roleRepository = mock(RoleRepositoryImpl.class);
        roleService = new RoleServiceImpl(roleRepository, new RoleMapperImpl());
    }

    @Test
    void findAllRoles() throws RepositoryException, ServiceException {
        //given
        List<RoleDto> expected = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).build());
            add(RoleDto.builder().id(2L).build());
        }};

        //when
        when(roleRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(Role.builder().id(1L).build());
            add(Role.builder().id(2L).build());
        }});
        List<RoleDto> actual = roleService.findAllRoles();

        //then
        Assertions.assertEquals(expected, actual);
    }
}