package br.com.literAlura.Service;

import br.com.literAlura.Model.AuthorData;
import br.com.literAlura.Model.BookData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvertData {
    ObjectMapper mapper = new ObjectMapper();

    public <T> T getData(String json, Class<T> classe) {
        T resultObject = null; //conterá o objeto convertido
        try {
            JsonNode node = mapper.readTree(json);

            if (classe == BookData.class) {
                JsonNode recovery = node.get("results").get(0);
                resultObject = mapper.treeToValue(recovery, classe);
            } else if (classe == AuthorData.class) {
                JsonNode recovery = node.get("results").get(0).get("authors").get(0);
                resultObject = mapper.treeToValue(recovery, classe);
            } else {
                resultObject = mapper.readValue(json, classe);
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return resultObject;
    }
}


