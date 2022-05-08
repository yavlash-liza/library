package by.library.yavlash.controller;

import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.UserDto;
import by.library.yavlash.dto.UserListDto;
import by.library.yavlash.dto.UserSaveDto;
import by.library.yavlash.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user", authorities = "admin")
    void givenAdmin_findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        UserDto userDto = UserDto.builder()
                .id(id)
                .firstName("Sergei")
                .lastName("Smirnov")
                .passportNumber("123456789")
                .email("email1")
                .address("address1")
                .birthDate(LocalDate.of(2003, 4, 1))
                .orders(new ArrayList<>() {{
                    add(OrderListDto.builder().id(2L).build());
                }})
                .rolesId(new ArrayList<>() {{
                    add(2L);
                }})
                .build();

        //when
        when(userService.findById(id)).thenReturn(userDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/3"))
                .andExpect(jsonPath("$.firstName").value("Sergei"))
                .andExpect(jsonPath("$.lastName").value("Smirnov"))
                .andExpect(jsonPath("$.passportNumber").value("123456789"))
                .andExpect(jsonPath("$.email").value("email1"))
                .andExpect(jsonPath("$.address").value("address1"))
                .andExpect(jsonPath("$.birthDate").value("2003-04-01"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.rolesId").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "user")
    void givenUser_findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        UserDto userDto = UserDto.builder()
                .id(id)
                .firstName("Sergei")
                .lastName("Smirnov")
                .passportNumber("123456789")
                .email("email1")
                .address("address1")
                .birthDate(LocalDate.of(2003, 4, 1))
                .orders(new ArrayList<>() {{
                    add(OrderListDto.builder().id(2L).build());
                }})
                .rolesId(new ArrayList<>() {{
                    add(2L);
                }})
                .build();

        //when
        when(userService.findById(id)).thenReturn(userDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/3"))
                .andExpect(jsonPath("$.firstName").value("Sergei"))
                .andExpect(jsonPath("$.lastName").value("Smirnov"))
                .andExpect(jsonPath("$.passportNumber").value("123456789"))
                .andExpect(jsonPath("$.email").value("email1"))
                .andExpect(jsonPath("$.address").value("address1"))
                .andExpect(jsonPath("$.birthDate").value("2003-04-01"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.rolesId").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findById_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given && when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "admin")
    void givenAdmin_findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<UserListDto> userListDtos = new ArrayList<>() {{
            add(UserListDto.builder().id(1L).firstName("Sergei").lastName("Smirnov").email("email1").address("address1").build());
            add(UserListDto.builder().id(2L).firstName("Dima").lastName("Petrov").email("email2").address("address2").build());
        }};

        //when
        when(userService.findAll()).thenReturn(userListDtos);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Sergei"))
                .andExpect(jsonPath("$[0].lastName").value("Smirnov"))
                .andExpect(jsonPath("$[0].email").value("email1"))
                .andExpect(jsonPath("$[0].address").value("address1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Dima"))
                .andExpect(jsonPath("$[1].lastName").value("Petrov"))
                .andExpect(jsonPath("$[1].email").value("email2"))
                .andExpect(jsonPath("$[1].address").value("address2"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findAll_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given && when & then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "user")
    void givenUser_findAll_shouldReturnHttpStatusForbidden() throws Exception {
        //given && when & then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "admin")
    void givenAdmin_add_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<Long> roleList = new ArrayList<>() {{
            add(1L);
        }};
        UserSaveDto userSaveDto = UserSaveDto.builder()
                .id(4L)
                .firstName("Sergei")
                .lastName("Smirnov")
                .passportNumber("123456789")
                .birthDate(LocalDate.of(2003, 4, 1))
                .roleId(roleList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(mapper.writeValueAsString(userSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "user")
    void givenUser_add_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<Long> roleList = new ArrayList<>() {{
            add(1L);
        }};
        UserSaveDto userSaveDto = UserSaveDto.builder()
                .id(4L)
                .firstName("Sergei")
                .lastName("Smirnov")
                .passportNumber("123456789")
                .birthDate(LocalDate.of(2003, 4, 1))
                .roleId(roleList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(mapper.writeValueAsString(userSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_add_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<Long> roleList = new ArrayList<>() {{
            add(1L);
        }};
        UserSaveDto userSaveDto = UserSaveDto.builder()
                .id(4L)
                .firstName("Sergei")
                .lastName("Smirnov")
                .passportNumber("123456789")
                .birthDate(LocalDate.of(2003, 4, 1))
                .roleId(roleList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(mapper.writeValueAsString(userSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "admin")
    void givenAdmin_update_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<Long> roleList = new ArrayList<>() {{
            add(1L);
        }};
        UserSaveDto userDto = UserSaveDto.builder()
                .id(4L)
                .firstName("Sergei")
                .lastName("Smirnov")
                .birthDate(LocalDate.of(2003, 4, 1))
                .roleId(roleList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(userService.update(userDto)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "user")
    void givenUser_update_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<Long> roleList = new ArrayList<>() {{
            add(1L);
        }};
        UserSaveDto userDto = UserSaveDto.builder()
                .id(4L)
                .firstName("Sergei")
                .lastName("Smirnov")
                .birthDate(LocalDate.of(2003, 4, 1))
                .roleId(roleList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(userService.update(userDto)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_update_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<Long> roleList = new ArrayList<>() {{
            add(1L);
        }};
        UserSaveDto userDto = UserSaveDto.builder()
                .id(4L)
                .firstName("Sergei")
                .lastName("Smirnov")
                .birthDate(LocalDate.of(2003, 4, 1))
                .roleId(roleList)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "admin")
    void givenAdmin_delete_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;

        //when
        when(userService.softDelete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_delete_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given && when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "user")
    void givenUser_delete_shouldReturnHttpStatusForbidden() throws Exception {
        //given && when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/3"))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}