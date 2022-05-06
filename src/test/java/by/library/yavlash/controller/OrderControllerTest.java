package by.library.yavlash.controller;

import by.library.yavlash.dto.BookCopyListDto;
import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.service.OrderService;
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
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "user", authorities = "ORDER_READ")
    void findById_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;
        OrderDto orderDto = OrderDto.builder().id(id)
                .orderStatus("NEW")
                .startDate(LocalDate.of(2003, 4, 1))
                .endDate(LocalDate.of(2003, 4, 1))
                .price(10)
                .userId(2L)
                .bookCopies(new ArrayList<>() {{
                    add(BookCopyListDto.builder().id(2L).build());
                }})
                .bookDamages(new ArrayList<>() {{
                    add(2L);
                }})
                .build();

        //when
        when(orderService.findById(id)).thenReturn(orderDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/3"))
                .andExpect(jsonPath("$.orderStatus").value("NEW"))
                .andExpect(jsonPath("$.startDate").value("2003-04-01"))
                .andExpect(jsonPath("$.endDate").value("2003-04-01"))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.userId").value(2))
                .andExpect(jsonPath("$.bookCopies").isArray())
                .andExpect(jsonPath("$.bookDamages").isArray())
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
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "ORDER_READ")
    void findAll_shouldReturnHttpStatusOk() throws Exception {
        //given
        List<OrderListDto> orderListDtos = new ArrayList<>() {{
            add(OrderListDto.builder().id(1L).orderStatus("NEW").startDate(LocalDate.of(2003, 4, 1)).endDate(LocalDate.of(2003, 4, 1)).price(20).build());
            add(OrderListDto.builder().id(2L).orderStatus("NEW").startDate(LocalDate.of(2003, 4, 1)).endDate(LocalDate.of(2003, 4, 1)).price(10).build());
        }};

        //when
        when(orderService.findAll()).thenReturn(orderListDtos);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].orderStatus").value("NEW"))
                .andExpect(jsonPath("$[0].startDate").value("2003-04-01"))
                .andExpect(jsonPath("$[0].endDate").value("2003-04-01"))
                .andExpect(jsonPath("$[0].price").value(20))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].orderStatus").value("NEW"))
                .andExpect(jsonPath("$[1].startDate").value("2003-04-01"))
                .andExpect(jsonPath("$[1].endDate").value("2003-04-01"))
                .andExpect(jsonPath("$[1].price").value(10))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_findAll_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given && when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "ORDER_WRITE")
    void add_shouldReturnHttpStatusOk() throws Exception {
        //given
        OrderSaveDto orderSaveDto = OrderSaveDto.builder()
                .startDate(LocalDate.of(2003, 3, 1))
                .endDate(LocalDate.of(2003, 4, 1))
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>() {{
                    add(2L);
                }}).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(mapper.writeValueAsString(orderSaveDto))
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
        OrderSaveDto orderSaveDto = OrderSaveDto.builder()
                .startDate(LocalDate.of(2003, 3, 1))
                .endDate(LocalDate.of(2003, 4, 1))
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>() {{
                    add(2L);
                }}).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(mapper.writeValueAsString(orderSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "ORDER_WRITE")
    void update_shouldReturnHttpStatusOk() throws Exception {
        //given
        OrderSaveDto orderSaveDto = OrderSaveDto.builder()
                .startDate(LocalDate.of(2003, 3, 1))
                .endDate(LocalDate.of(2003, 4, 1))
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>() {{
                    add(2L);
                }}).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(orderService.update(orderSaveDto)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                        .content(mapper.writeValueAsString(orderSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_update_shouldReturnHttpStatusUnauthorized() throws Exception {
        //given
        OrderSaveDto orderSaveDto = OrderSaveDto.builder()
                .startDate(LocalDate.of(2003, 3, 1))
                .endDate(LocalDate.of(2003, 4, 1))
                .price(13)
                .userId(1L)
                .bookCopiesId(new ArrayList<>() {{
                    add(2L);
                }}).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                        .content(mapper.writeValueAsString(orderSaveDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", authorities = "ORDER_DELETE")
    void delete_shouldReturnHttpStatusOk() throws Exception {
        //given
        Long id = 3L;

        //when
        when(orderService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/orders/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_delete_shouldReturnHttpStatusOk() throws Exception {
        //given
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/3"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
}