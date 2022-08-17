package api.v1.converter;

import api.v1.entity.region.CityEnum;
import org.springframework.core.convert.converter.Converter;

public class CityRequestConverter implements Converter<String, CityEnum> {
    @Override
    public CityEnum convert(String city) {
        city = city.replaceAll(" ","");
        return CityEnum.of(city);
    }
}
