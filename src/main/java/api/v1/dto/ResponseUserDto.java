package api.v1.dto;

import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseUserDto {
    private Long userId;

    private String name;

    private String email;

    private CityEnum city;

    private RegionEnum region;

    private Bussiness bussiness;

}
