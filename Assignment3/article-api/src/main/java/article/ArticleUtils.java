package article;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class ArticleUtils {

    private ArticleUtils() {

    }

    public static byte[] serializeArticle(ArticleJson a) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(a);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArticleJson deserializeArticle(byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, ArticleJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
