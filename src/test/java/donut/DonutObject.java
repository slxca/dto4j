package donut;

import com.slxca.dto.Dto;
import com.slxca.dto.DtoProperty;

@Dto
public class DonutObject {

    @DtoProperty("GET_DONUT")
    public String name = "Glazed";

    @DtoProperty(profile = "GET_DONUT", name = "donutType")
    public DonutEnum type = DonutEnum.CHOCOLATE;

}
