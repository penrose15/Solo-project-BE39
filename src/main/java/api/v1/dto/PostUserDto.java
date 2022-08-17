package api.v1.dto;

import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Validated
@Builder
public class PostUserDto {

    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]*$")
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private CityEnum city;

    @NotNull
    private RegionEnum region;

    @NotNull
    private Bussiness bussiness;


}
