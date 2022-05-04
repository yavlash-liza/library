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
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('admin', 'user')")
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable Long id) throws ServiceException {
        return orderService.findById(id);
    }

    @GetMapping
    public List<OrderListDto> findAll() throws ServiceException {
        return orderService.findAll();
    }

    @PostMapping
    public boolean add(@RequestBody OrderSaveDto order) throws ServiceException {
        return orderService.add(order);
    }

    @PutMapping
    public boolean update(@RequestBody OrderSaveDto orderSaveDto) throws ServiceException {
        return orderService.update(orderSaveDto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return orderService.delete(id);
    }
}