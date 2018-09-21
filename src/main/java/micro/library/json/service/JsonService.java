package micro.library.json.service;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import java.util.List;

public interface JsonService {

    <T> T read(Class<T> cls, String payload);

    <T> JSONAPIDocument<List<T>> readList(Class<T> cls, String payload);

    String write(Object object);

    String writeList(Iterable<?> iterable);
}