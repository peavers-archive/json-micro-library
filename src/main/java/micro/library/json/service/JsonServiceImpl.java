package micro.library.json.service;

import com.github.jasminb.jsonapi.DeserializationFeature;
import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.ResourceConverter;
import com.github.jasminb.jsonapi.exceptions.DocumentSerializationException;
import com.github.jasminb.jsonapi.models.errors.Error;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JsonServiceImpl implements JsonService {

    private final ResourceConverter converter;

    public JsonServiceImpl(ResourceConverter converter) {
        this.converter = converter;

        converter.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID);
    }

    @Override
    public <T> T read(Class<T> cls, String payload) {

        JSONAPIDocument<?> document = validate(cls, payload);

        return (T) document;
    }

    @Override
    public <T> JSONAPIDocument<List<T>> readList(Class<T> cls, String payload) {
        return converter.readDocumentCollection(payload.getBytes(), cls);
    }

    @Override
    public String write(Object object) {
        JSONAPIDocument<Object> document = new JSONAPIDocument<>(object);

        byte[] bytes = new byte[0];
        try {
            bytes = converter.writeDocument(document);
        } catch (DocumentSerializationException e) {
            e.printStackTrace();
        }

        return new String(bytes);
    }

    @Override
    public String writeList(Iterable<?> iterable) {
        JSONAPIDocument<Collection<?>> listJSONAPIDocument = new JSONAPIDocument<>(
                (Collection<?>) iterable);

        byte[] bytes = new byte[0];
        try {
            bytes = converter.writeDocumentCollection(listJSONAPIDocument);
        } catch (DocumentSerializationException e) {
            e.printStackTrace();
        }

        return new String(bytes);
    }

    private <T> JSONAPIDocument<?> validate(Class<T> cls, String payload) {
        T document = converter.readDocument(payload.getBytes(), cls).get();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(document);

        if (violations.isEmpty()) {
            return (JSONAPIDocument<?>) document;
        } else {
            Error error = new Error();
            error.setDetail(violations.iterator().next().getMessage());

            return JSONAPIDocument.createErrorDocument(Collections.singleton(error));
        }
    }
}