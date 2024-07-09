package br.com.literAlura.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity(name = "book")
@Table(name = "Book")
public class Book {
    @Id
    private Long id;
    private String title;
    @Column(name = "language")
    private List<String> language;
    private String downloadCount;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(Long id, String title, List<String> language, String downloadCount) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.downloadCount = downloadCount;
    }
}

