package cookie;

import com.slxca.Dto4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieTest {

    Dto4j dto = Dto4j.builder()
            .list(List.of(
                    new CookieObject("Chocolate", CookieEnum.CHOCOLATE),
                    new CookieObject("Peanut Butter", CookieEnum.PEANUT_BUTTER)
            ));

    @Test
    void addition() {
        List<Object> result = dto.toList();
        List<Map<?,?>> expected = new ArrayList<>();

        Map<String, Object> cookie1 = new HashMap<>();
        cookie1.put("name", "CHOCOLATE");

        Map<String, Object> cookie2 = new HashMap<>();
        cookie2.put("name", "PEANUT BUTTER");

        expected.add(cookie1);
        expected.add(cookie2);

        assertEquals(expected, result);
    }

}
