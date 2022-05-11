package by.library.yavlash.controller.impl;

import by.library.yavlash.controller.BaseControllerTest;
import by.library.yavlash.dto.RoleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(username = "user", authorities = "ROLE_READ")
    void findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<RoleDto> authors = new ArrayList<>() {{
            add(RoleDto.builder().id(1L).roleName("user").build());
            add(RoleDto.builder().id(2L).roleName("admin").build());
        }};

        //when
        when(roleService.findAll()).thenReturn(authors);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].roleName").value("user"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].roleName").value("admin"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findAll_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given && when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}