package cookie;

import com.slxca.dto.DtoConverter;

public class CookieConverter implements DtoConverter<Object, String> {
    @Override
    public String convert(Object source) {
        return "PLAIN COOKIE";
    }
}
