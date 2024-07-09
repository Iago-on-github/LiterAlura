package br.com.literAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData(@JsonAlias("name") String name,
                         @JsonAlias("birth_year") String birthYear,
                         @JsonAlias("death_year") String deathYear)
{
}
