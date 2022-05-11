package by.library.yavlash.controller.impl;

import by.library.yavlash.controller.BaseControllerTest;
import by.library.yavlash.dto.AuthorListDto;
import by.library.yavlash.dto.BookCopyDto;
import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.BookCopySaveDto;
import by.library.yavlash.dto.BookSaveDto;
import by.library.yavlash.dto.GenreDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookCopyControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(username = "user", authorities = "BOOK_READ")
    void findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        BookCopyDto bookCopyDto = BookCopyDto.builder().id(id)
                .title("War and peace")
                .pagesNumber(290)
                .status("AVAILABLE")
                .registrationDate(LocalDate.of(2020, 3, 5))
                .pricePerDay(2)
                .imagePath("image path")
                .authors(new ArrayList<>() {{
                    add(AuthorListDto.builder().id(1L).build());
                }})
                .genres(new ArrayList<>() {{
                    add(GenreDto.builder().id(1L).build());
                }})
                .bookDamagesId(new ArrayList<>() {{
                    add(1L);
                }}).build();

        //when
        when(bookCopyService.findById(id)).thenReturn(bookCopyDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/books/3"))
                .andExpect(jsonPath("$.title").value("War and peace"))
                .andExpect(jsonPath("$.pagesNumber").value(290))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.registrationDate").value("2020-03-05"))
                .andExpect(jsonPath("$.pricePerDay").value(2))
                .andExpect(jsonPath("$.imagePath").value("image path"))
                .andExpect(jsonPath("$.authors").isArray())
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.bookDamagesId").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        BookCopyDto bookCopyDto = BookCopyDto.builder().id(id)
                .title("War and peace")
                .pagesNumber(290)
                .status("AVAILABLE")
                .registrationDate(LocalDate.of(2020, 3, 5))
                .pricePerDay(2)
                .imagePath("image path")
                .authors(new ArrayList<>() {{
                    add(AuthorListDto.builder().id(1L).build());
                }})
                .genres(new ArrayList<>() {{
                    add(GenreDto.builder().id(1L).build());
                }})
                .bookDamagesId(new ArrayList<>() {{
                    add(1L);
                }}).build();

        //when
        when(bookCopyService.findById(id)).thenReturn(bookCopyDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/books/3"))
                .andExpect(jsonPath("$.title").value("War and peace"))
                .andExpect(jsonPath("$.pagesNumber").value(290))
                .andExpect(jsonPath("$.status").value("AVAILABLE"))
                .andExpect(jsonPath("$.registrationDate").value("2020-03-05"))
                .andExpect(jsonPath("$.pricePerDay").value(2))
                .andExpect(jsonPath("$.imagePath").value("image path"))
                .andExpect(jsonPath("$.authors").isArray())
                .andExpect(jsonPath("$.genres").isArray())
                .andExpect(jsonPath("$.bookDamagesId").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_READ")
    void findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<BookCopyListDto> bookCopyListDtos = new ArrayList<>() {{
            add(BookCopyListDto.builder().id(1L).title("War and peace").imagePath("image path").pricePerDay(2).build());
            add(BookCopyListDto.builder().id(2L).title("Eugene Onegin").imagePath("image path").pricePerDay(2).build());
        }};

        //when
        when(bookCopyService.findAll()).thenReturn(bookCopyListDtos);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("War and peace"))
                .andExpect(jsonPath("$[0].imagePath").value("image path"))
                .andExpect(jsonPath("$[0].pricePerDay").value(2))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Eugene Onegin"))
                .andExpect(jsonPath("$[1].imagePath").value("image path"))
                .andExpect(jsonPath("$[1].pricePerDay").value(2))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<BookCopyListDto> bookCopyListDtos = new ArrayList<>() {{
            add(BookCopyListDto.builder().id(1L).title("War and peace").imagePath("image path").pricePerDay(2).build());
            add(BookCopyListDto.builder().id(2L).title("Eugene Onegin").imagePath("image path").pricePerDay(2).build());
        }};

        //when
        when(bookCopyService.findAll()).thenReturn(bookCopyListDtos);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("War and peace"))
                .andExpect(jsonPath("$[0].imagePath").value("image path"))
                .andExpect(jsonPath("$[0].pricePerDay").value(2))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Eugene Onegin"))
                .andExpect(jsonPath("$[1].imagePath").value("image path"))
                .andExpect(jsonPath("$[1].pricePerDay").value(2))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_WRITE")
    void addBookCopy_shouldReturnHttpStatusOk() throws Exception {
        //given
        BookCopySaveDto bookCopySaveDto = BookCopySaveDto.builder()
                .status("AVAILABLE")
                .registrationDate(LocalDate.of(2020, 3, 5))
                .pricePerDay(2)
                .imagePath("image path")
                .bookId(2L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/books/copies")
                        .content(mapper.writeValueAsString(bookCopySaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_addBookCopy_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given
        BookCopySaveDto bookCopySaveDto = BookCopySaveDto.builder()
                .status("AVAILABLE")
                .registrationDate(LocalDate.of(2020, 3, 5))
                .pricePerDay(2)
                .imagePath("image path")
                .bookId(2L)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/books/copies")
                        .content(mapper.writeValueAsString(bookCopySaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_WRITE")
    void addBook_shouldReturnHttpStatusOk() throws Exception {
        //given
        BookSaveDto bookSaveDto = BookSaveDto.builder()
                .title("Hamlet")
                .pagesNumber(290)
                .imagePath("image path")
                .genresId(new ArrayList<>() {{
                    add(1L);
                }})
                .authorsId(new ArrayList<>() {{
                    add(1L);
                }})
                .build();

        ObjectMapper mapper = new ObjectMapper();

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(bookSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_addBook_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given
        BookSaveDto bookSaveDto = BookSaveDto.builder()
                .title("Hamlet")
                .pagesNumber(290)
                .imagePath("image path")
                .genresId(new ArrayList<>() {{
                    add(1L);
                }})
                .authorsId(new ArrayList<>() {{
                    add(1L);
                }})
                .build();

        ObjectMapper mapper = new ObjectMapper();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(bookSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_WRITE")
    void updateBookCopy_shouldReturnHttpStatusOk() throws Exception {
        //given
        BookCopySaveDto bookCopySaveDto = BookCopySaveDto.builder()
                .id(4L)
                .imagePath("image")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        //when
        when(bookCopyService.update(bookCopySaveDto)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/books")
                        .content(mapper.writeValueAsString(bookCopySaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_updateBookCopy_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given
        BookCopySaveDto bookCopySaveDto = BookCopySaveDto.builder()
                .id(4L)
                .imagePath("image")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                        .content(mapper.writeValueAsString(bookCopySaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "BOOK_DELETE")
    void deleteBookCopy_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;

        //when
        when(bookCopyService.softDelete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/books/copies/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_deleteBookCopy_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given && when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/copies/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}