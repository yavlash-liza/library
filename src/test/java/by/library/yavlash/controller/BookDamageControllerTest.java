package by.library.yavlash.controller;

import by.library.yavlash.dto.BookDamageDto;
import by.library.yavlash.service.BookDamageService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookDamageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookDamageService bookDamageService;

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_DAMAGE_READ")
    void findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        BookDamageDto bookDamage = BookDamageDto.builder().id(id).imagePath("imagePath").damageDescription("broken").userId(1L).bookCopyId(1L).orderId(1L).build();

        //when
        when(bookDamageService.findById(id)).thenReturn(bookDamage);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/damages/3"))
                .andExpect(jsonPath("$.imagePath").value("imagePath"))
                .andExpect(jsonPath("$.damageDescription").value("broken"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.bookCopyId").value(1L))
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
        mockMvc.perform(MockMvcRequestBuilders.get("/damages/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_DAMAGE_WRITE")
    void add_shouldReturnHttpStatusOk() throws Exception {
        //given
        BookDamageDto bookDamageDto = BookDamageDto.builder().imagePath("imagePath").damageDescription("broken").userId(1L).bookCopyId(1L).orderId(1L).build();

        ObjectMapper mapper = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/damages")
                        .content(mapper.writeValueAsString(bookDamageDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_add_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given
        BookDamageDto bookDamageDto = BookDamageDto.builder().imagePath("imagePath").damageDescription("broken").userId(1L).bookCopyId(1L).orderId(1L).build();

        ObjectMapper mapper = new ObjectMapper();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/damages")
                        .content(mapper.writeValueAsString(bookDamageDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_DAMAGE_DELETE")
    void delete_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookDamageService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/damages/3"))
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/damages/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}