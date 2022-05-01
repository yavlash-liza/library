package by.library.yavlash.controller;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) throws ServiceException {
        return orderService.findById(id);
    }

    @GetMapping
    public List<OrderListDto> findAll() throws ServiceException {
        return orderService.findAll();
    }

    @PreAuthorize("hasRole(('admin'))")
    @PostMapping
    public boolean addOrder(@RequestBody OrderSaveDto order) throws ServiceException {
        return orderService.add(order);
    }

    @PreAuthorize("hasRole(('admin'))")
    @PutMapping
    public boolean updateOrder(@RequestBody OrderDto orderDto) throws ServiceException {
        return orderService.update(orderDto);
    }

    @PreAuthorize("hasRole(('admin'))")
    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable Long id) throws ServiceException {
        return orderService.delete(id);
    }
}