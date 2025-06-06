package cookie;

import com.slxca.annotation.Dto;
import com.slxca.annotation.DtoProperty;

@Dto
public class CookieObject {

    @DtoProperty(converter = CookieConverter.class)
    public String name;

    @DtoProperty(ignore = true, name = "cookieType")
    public CookieEnum type;

    public CookieObject(String name, CookieEnum type) {
        this.name = name;
        this.type = type;
    }
}
