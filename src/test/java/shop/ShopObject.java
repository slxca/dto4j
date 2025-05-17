package shop;

import com.slxca.dto.Dto;
import com.slxca.dto.DtoProperty;
import cookie.CookieEnum;
import cookie.CookieObject;
import donut.DonutEnum;
import donut.DonutObject;

import java.util.List;

@Dto
public class ShopObject {

    @DtoProperty
    public String name = "Bakery Shop";

    @DtoProperty
    public List<DonutObject> donuts = List.of(
            new DonutObject()
    );

    @DtoProperty
    public List<DonutEnum> donutTypes = List.of(
            DonutEnum.CHOCOLATE,
            DonutEnum.VANILLA
    );

    @DtoProperty
    public List<CookieObject> cookies = List.of(
            new CookieObject()
    );

    @DtoProperty
    public List<CookieEnum> cookieTypes = List.of(
            CookieEnum.CHOCOLATE,
            CookieEnum.PEANUT_BUTTER
    );
}
