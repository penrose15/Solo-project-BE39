package api.v1.entity;

import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User extends CommonEntity{



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "CITY")
    private CityEnum city;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "REGION")
    private RegionEnum region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "BUSINESS_TYPE")
    private Bussiness bussiness;

    @Builder
    public User(Long userId, String name, String email, CityEnum city,RegionEnum region, Bussiness bussiness) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.city = city;
        this.region = region;
        this.bussiness = bussiness;
    }
}
