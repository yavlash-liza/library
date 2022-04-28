package by.library.yavlash.controller;

import by.library.yavlash.dto.AuthorDto;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.AuthorSaveDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.service.AuthorService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        List<BookCopyListDto> books = new ArrayList<>(){{add(BookCopyListDto.builder().id(id).build());}};
        AuthorDto authorDto = AuthorDto.builder().id(id).firstName("Liza").lastName("Yavlash")
                .birthDate(LocalDate.of(2003,4,1)).imagePath("imagePath").books(books).build();

        //when
        when(authorService.findById(id)).thenReturn(authorDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/authors/3"))
                .andExpect(jsonPath("$.firstName").value("Liza"))
                .andExpect(jsonPath("$.lastName").value("Yavlash"))
                .andExpect(jsonPath("$.birthDate").value("2003-04-01"))
                .andExpect(jsonPath("$.imagePath").value("imagePath"))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void findById_whenGetNotExistingAuthor_returnStatus404() throws Exception {
        //given && then && when
        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        AuthorListDto author1 = AuthorListDto.builder().id(1L).firstName("Alexander").lastName("Pushkin").build();
        AuthorListDto author2 = AuthorListDto.builder().id(2L).firstName("Janka").lastName("Kupala").build();
        List<AuthorListDto> authors = new ArrayList<>(){{
            add(author1);
            add(author2);
        }};

        //when
        when(authorService.findAll()).thenReturn(authors);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Alexander"))
                .andExpect(jsonPath("$[0].lastName").value("Pushkin"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Janka"))
                .andExpect(jsonPath("$[1].lastName").value("Kupala"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void add_shouldReturnHttpStatusOk() throws Exception {
        //given
        AuthorSaveDto authorWithoutId = AuthorSaveDto.builder().firstName("Alexander").lastName("Pushkin").birthDate(LocalDate.of(2002,1,3)).imagePath("path").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .content(mapper.writeValueAsString(authorWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void delete_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;

        //when
        when(authorService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/authors/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}