package api.v1.repository;

import api.v1.entity.User;
import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserCustomRepository {
    List<User> findByRegionOrBusinessOrName(CityEnum city, Bussiness bussiness, String name);
}
