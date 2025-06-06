package donut;

import com.slxca.annotation.Dto;
import com.slxca.annotation.DtoProperty;

@Dto
public class DonutObject {

    @DtoProperty({"DONUT"})
    public String name = "Glazed";

    @DtoProperty(profile = "DONUT", name = "donutType")
    public DonutEnum type = DonutEnum.CHOCOLATE;

    public DonutObject(String name, DonutEnum type) {
        this.name = name;
        this.type = type;
    }
}
