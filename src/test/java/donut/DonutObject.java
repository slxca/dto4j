package donut;

import com.slxca.dto.Dto;
import com.slxca.dto.DtoProperty;

@Dto
public class DonutObject {

    @DtoProperty
    public String name = "Glazed";

    @DtoProperty(name = "donutType")
    public DonutEnum type = DonutEnum.CHOCOLATE;

}
