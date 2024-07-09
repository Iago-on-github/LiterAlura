package br.com.literAlura.Repository;

import br.com.literAlura.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);

    Optional<Author> findByName(String name);

    @Query("select a from author a where a.birthYear <= :year and (a.deathYear is null or a.deathYear >= :year)")
    List <Author> findByLivingInGivenYear(int year);
}
