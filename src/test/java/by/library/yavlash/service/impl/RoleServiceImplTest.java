package by.library.yavlash.service.impl;

import by.library.yavlash.dto.RoleDto;
import by.library.yavlash.entity.Role;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.mapper.RoleMapper;
import by.library.yavlash.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @Spy
    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void findAllTest_shouldReturnListOfAllRoles() throws ServiceException {
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
        List<RoleDto> actual = roleService.findAll();

        //then
        Assertions.assertEquals(expected, actual);
    }
}