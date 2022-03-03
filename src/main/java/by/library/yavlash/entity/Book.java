package by.library.yavlash.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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
@Table(name = "books")
public class Book extends BaseEntity {
    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "pages_number")
    private int pagesNumber;

    @Column(name = "image_path", length = 512)
    private String imagePath;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "book",
            cascade = CascadeType.ALL)
    private List<BookCopy> bookCopies;

    @ManyToMany
    @JoinTable(name = "book_author_links",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(name = "book_genre_links",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
}