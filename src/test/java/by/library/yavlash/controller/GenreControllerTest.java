package by.library.yavlash.controller;

import by.library.yavlash.dto.GenreDto;
import by.library.yavlash.service.GenreService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    @WithMockUser(username = "user", authorities = "GENRE_READ")
    void findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<GenreDto> genres = new ArrayList<>() {{
            add(GenreDto.builder().id(1L).genreName("NOVEL").build());
            add(GenreDto.builder().id(2L).genreName("ROMANCE").build());
        }};

        //when
        when(genreService.findAll()).thenReturn(genres);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].genreName").value("NOVEL"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].genreName").value("ROMANCE"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<GenreDto> genres = new ArrayList<>() {{
            add(GenreDto.builder().id(1L).genreName("NOVEL").build());
            add(GenreDto.builder().id(2L).genreName("ROMANCE").build());
        }};

        //when
        when(genreService.findAll()).thenReturn(genres);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].genreName").value("NOVEL"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].genreName").value("ROMANCE"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "GENRE_WRITE")
    void add_shouldReturnHttpStatusOk() throws Exception {
        //given
        GenreDto genreDto = GenreDto.builder().id(1L).genreName("NOVEL").build();

        ObjectMapper mapper = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/genres")
                        .content(mapper.writeValueAsString(genreDto))
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
        GenreDto genreDto = GenreDto.builder().id(1L).genreName("NOVEL").build();

        ObjectMapper mapper = new ObjectMapper();

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/genres")
                        .content(mapper.writeValueAsString(genreDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "GENRE_DELETE")
    void delete_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;

        //when
        when(genreService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/genres/3"))
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
        mockMvc.perform(MockMvcRequestBuilders.delete("/genres/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}