package api.v1.dto;

import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class SearchDto {

    private CityEnum city;

    private Bussiness bussiness;

    private String name;
}
