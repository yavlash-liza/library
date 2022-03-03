package by.library.yavlash.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    //    @Enumerated(EnumType.STRING) //todo
    @Column(name = "order_status", length = 64)
    private String orderStatus;

    @Column(name = "start_date", columnDefinition = "DATETIME")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATETIME")
    private LocalDate endDate;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL)
    private List<BookDamage> bookDamages;

    @ManyToMany
    @JoinTable(name = "order_book_copy_links",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_copy_id"))
    private Set<BookCopy> bookCopies;
}