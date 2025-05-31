package donut;

import com.slxca.Dto4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DonutTest {

    Dto4j dto = Dto4j.builder()
            .profile("GET_DONUT")
            .object(new DonutObject());

    @Test
    void addition() {
        Map<String, Object> donut = dto.toMap();

        Map<String, Object> expected = new HashMap<>();
        expected.put("name", "Glazed");
        expected.put("donutType", DonutEnum.CHOCOLATE);

        assertEquals(expected, donut);
    }

}
