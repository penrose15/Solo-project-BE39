package api.v1.entity.region;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class RegionGroup{
    //부모코드에 해당되는 EnumSet를 리스트  형태로 반환
    public static <T extends Enum<T> & RegionModelExt, E extends Enum<E> & RegionModel> List<T> getRegionByGroup(Class<T> regionClass, E parentCode) {
        return EnumSet.allOf(regionClass)
                .stream()
                .filter(type -> type.getParent().equals(parentCode))
                .collect(Collectors.toList());
    }
    //부모 EnumSet를 리스트로 반환
    public static <E extends Enum<E> & RegionModel> List<E> getParentEnum(Class<E> enumClass) {
        return EnumSet.allOf(enumClass)
                .stream()
                .collect(Collectors.toList());
    }
}
