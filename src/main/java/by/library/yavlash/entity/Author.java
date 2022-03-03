package by.library.yavlash.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    //todo спросить зачем автору дата рождения?
    @Column(name = "birth_date", columnDefinition = "DATETIME")
    private LocalDate birthDate;

    @Column(name = "image_path", length = 512)
    private String imagePath;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}