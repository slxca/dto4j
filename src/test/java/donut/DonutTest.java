package donut;

import com.slxca.Dto4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DonutTest {

    Dto4j dto = Dto4j.builder()
            .profile("DONUT")
            .list(List.of(
                    new DonutObject("Chocolate", DonutEnum.CHOCOLATE),
                    new DonutObject("Vanilla", DonutEnum.VANILLA)
            ));

    @Test
    void addition() {
        List<Object> result = dto.toList();
        List<Map<?,?>> expected = new ArrayList<>();

        Map<String, Object> donut1 = new HashMap<>();
        donut1.put("name", "Chocolate");
        donut1.put("donutType", DonutEnum.CHOCOLATE);

        Map<String, Object> donut2 = new HashMap<>();
        donut2.put("name", "Vanilla");
        donut2.put("donutType", DonutEnum.VANILLA);

        expected.add(donut1);
        expected.add(donut2);

        assertEquals(expected, result);
    }

}
