package shop;

import com.slxca.annotation.Dto;
import com.slxca.annotation.DtoProperty;
import cookie.CookieEnum;
import cookie.CookieObject;

import java.util.List;

@Dto
public class ShopObject {
    @DtoProperty
    public String name = "Bakery Shop";

    @DtoProperty
    public List<CookieObject> cookies = List.of(
            new CookieObject("Chocolate", CookieEnum.CHOCOLATE),
            new CookieObject("Peanut Butter", CookieEnum.PEANUT_BUTTER)
    );
}
