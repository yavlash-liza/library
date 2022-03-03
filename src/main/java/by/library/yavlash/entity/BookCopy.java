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
@Table(name = "book_copies")
public class BookCopy extends BaseEntity {
    @Column(name = "status", length = 512)
    private String status;

    @Column(name = "registration_date", columnDefinition = "DATETIME")
    private LocalDate registrationDate;

    @Column(name = "price")
    private int price;

    @Column(name = "price_per_day")
    private int pricePerDay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToMany(mappedBy = "bookCopies")
    private Set<Order> orders;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "bookCopy",
            cascade = CascadeType.ALL)
    private List<BookDamage> bookDamages;
}