package api.v1.entity.region;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;


import java.util.List;
@Getter
public enum CityEnum implements RegionModelExt{
    //api문서 작성이 목적이므로 편의상 일부 행정구역만 표기
    고양시(1, RegionEnum.경기도,"고양시"),
    수원시(2, RegionEnum.경기도, "수원시"),
    강남구(3, RegionEnum.서울특별시,"강남구"),
    송파구(4, RegionEnum.서울특별시,"송파구"),
    서초구(5, RegionEnum.서울특별시,"서초구"),
    안산시(6,RegionEnum.경기도,"안산시")
    ;

    private final int key;
    private final RegionEnum regionEnum;
    private final String value;

    CityEnum(int key, RegionEnum regionEnum, String value) {
        this.key = key;
        this.regionEnum = regionEnum;
        this.value = value;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public RegionEnum getParent() {
        return regionEnum;
    }


    public static List<CityEnum> getCityEnumByGroup(RegionEnum regionEnum) {
        return RegionGroup.getRegionByGroup(CityEnum.class,regionEnum);
    }//부모 코드에 해당되는 지역리스트

    public static List<RegionEnum> getRegionEnum() {
        return RegionGroup.getParentEnum(RegionEnum.class);
    }//부모 코드
}
