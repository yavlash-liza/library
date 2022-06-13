package by.library.yavlash.controller;

import by.library.yavlash.dto.OrderDto;
import by.library.yavlash.dto.OrderListDto;
import by.library.yavlash.dto.OrderSaveDto;
import by.library.yavlash.exception.ServiceException;
import by.library.yavlash.service.OrderService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final Bucket bucket;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @PreAuthorize("hasAuthority('ORDER_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            OrderDto order = orderService.findById(id);
            if (order == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(order);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('ORDER_READ')")
    @GetMapping
    public ResponseEntity<List<OrderListDto>> findAll() throws ServiceException {
        if (bucket.tryConsume(1)) {
            List<OrderListDto> orders = orderService.findAll();
            if (orders == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(orders);
            }
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('ORDER_WRITE')")
    @PostMapping
    public ResponseEntity<Boolean> add(
            @RequestBody OrderSaveDto order
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(orderService.add(order));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('ORDER_WRITE')")
    @PutMapping
    public ResponseEntity<Boolean> update(
            @RequestBody OrderSaveDto orderSaveDto
    ) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(orderService.update(orderSaveDto));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) throws ServiceException {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(orderService.softDelete(id));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}