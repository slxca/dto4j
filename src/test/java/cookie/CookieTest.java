package cookie;

import com.slxca.Dto4j;
import donut.DonutEnum;
import donut.DonutObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieTest {

    Dto4j dto = Dto4j.builder()
            .object(new CookieObject());

    @Test
    void addition() {
        Map<String, Object> cookie = dto.toMap();

        Map<String, Object> expected = new HashMap<>();
        expected.put("name", "PLAIN COOKIE");

        assertEquals(expected, cookie);
    }

}
