package cookie;

import com.slxca.dto.Dto;
import com.slxca.dto.DtoProperty;
import donut.DonutEnum;

@Dto
public class CookieObject {

    @DtoProperty(converter = CookieConverter.class)
    public String name = "Chocolate Chip";

    @DtoProperty(ignore = true, name = "cookieType")
    public CookieEnum type = CookieEnum.CHOCOLATE;

}
