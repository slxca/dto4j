package cookie;

import com.slxca.converter.DtoConverter;

public class CookieConverter implements DtoConverter<Object, String> {
    @Override
    public String convert(Object source) {
        return source.toString().toUpperCase();
    }
}
