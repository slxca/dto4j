package shop;

import com.slxca.Dto4j;
import cookie.CookieEnum;
import donut.DonutEnum;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopTest {

    Dto4j dto = Dto4j.builder()
            .object(new ShopObject());

    @Test
    void addition() {
        Map<String, Object> shop = dto.toMap();

        System.out.println(shop);

        Map<String, Object> expected = new HashMap<>();
        expected.put("name", "Bakery Shop");
        expected.put("donuts", List.of(
                Map.of(
                        "name", "Glazed",
                        "donutType", DonutEnum.CHOCOLATE
                )
        ));
        expected.put("donutTypes", List.of(
                DonutEnum.CHOCOLATE,
                DonutEnum.VANILLA
        ));
        expected.put("cookies", List.of(
                Map.of(
                        "name", "PLAIN COOKIE"
                )
        ));
        expected.put("cookieTypes", List.of(
                CookieEnum.CHOCOLATE,
                CookieEnum.PEANUT_BUTTER
        ));

        assertEquals(expected, shop);
    }

}
