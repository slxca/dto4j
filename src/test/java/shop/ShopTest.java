package shop;

import com.slxca.Dto4j;
import cookie.CookieEnum;
import cookie.CookieObject;
import donut.DonutEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopTest {

    Dto4j dto = Dto4j.builder()
            .object(new ShopObject());

    @Test
    void addition() {

        Map<String, Object> result = dto.toMap();
        Map<String, Object> expected = new HashMap<>();

        expected.put("name", "Bakery Shop");

        List<Map<?,?>> cookies = new ArrayList<>();

        Map<String, Object> cookie1 = new HashMap<>();
        cookie1.put("name", "CHOCOLATE");

        Map<String, Object> cookie2 = new HashMap<>();
        cookie2.put("name", "PEANUT BUTTER");

        cookies.add(cookie1);
        cookies.add(cookie2);

        expected.put("cookies", cookies);

        assertEquals(expected, result);
    }

}
