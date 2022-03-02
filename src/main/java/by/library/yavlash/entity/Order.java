package by.library.yavlash.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
public class Order extends BaseEntity {
    private Long id;
    private String orderStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private Long userId;
}