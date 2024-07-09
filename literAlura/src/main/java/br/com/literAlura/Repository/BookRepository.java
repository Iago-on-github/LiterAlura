package br.com.literAlura.Repository;

import br.com.literAlura.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitle(String title);
    Optional<Book> findByTitle(String title);

    @Query("select distinct b.language from book b order by b.language")
    List<String> requestLanguage();
    @Query("select b from book b where b.language = :language")
    List<Book> findByBooksInGivenLanguage(String language);
}
