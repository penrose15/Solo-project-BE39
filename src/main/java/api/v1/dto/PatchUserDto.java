package api.v1.dto;

import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Builder
public class PatchUserDto {

    private Long userId;

    private String name;

    @Email
    private String email;

    private CityEnum city;

    private RegionEnum region;

    private Bussiness bussiness;


    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
