package donut;

import com.slxca.dto.Dto;
import com.slxca.dto.DtoProperty;

@Dto
public class DonutObject {

    @DtoProperty(profile = "GET_DONUT")
    public String name = "Glazed";

    @DtoProperty(value = "GET_DONUT", name = "donutType")
    public DonutEnum type = DonutEnum.CHOCOLATE;

}
