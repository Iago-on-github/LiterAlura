package br.com.literAlura.Service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IConvertData {
    <T> T getData(String json, Class<T> classe);
}
