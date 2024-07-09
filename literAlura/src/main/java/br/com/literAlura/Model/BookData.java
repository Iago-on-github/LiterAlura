package br.com.literAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
                       Long id,
                       @JsonAlias("title") String title,
                       @JsonAlias("languages") List<String> language,
                       @JsonAlias("download_count") String downloadCount){

}
