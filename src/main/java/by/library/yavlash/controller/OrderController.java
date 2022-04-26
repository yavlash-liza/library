package by.library.yavlash.controller;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderListDto>> findAll() throws ServiceException {
        List<OrderListDto> orderListDtos = orderService.findAll();
        return orderListDtos != null ? new ResponseEntity<>(orderListDtos, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody OrderSaveDto order) {
        try {
            return new ResponseEntity<>(orderService.add(order), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) {
        try {
            return new ResponseEntity<>(orderService.update(orderDto), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
        } catch (ServiceException exception) {
            return getResponseEntityForException(exception);
        }
    }

    private ResponseEntity<?> getResponseEntityForException(ServiceException exception) {
        return exception.getMessage() != null
                ? new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>("Nothing is found", HttpStatus.NOT_FOUND);
    }
}