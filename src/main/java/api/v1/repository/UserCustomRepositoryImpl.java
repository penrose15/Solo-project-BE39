package api.v1.repository;

import api.v1.entity.QUser;
import api.v1.entity.User;
import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository{
//where 조건이 1개 이상인 경우와 동적 쿼리를 위해 querydsl 사용
    private final JPAQueryFactory queryFactory;
    QUser user = QUser.user;

    @Override
    public List<User> findByRegionOrBusinessOrName(CityEnum city, Bussiness bussiness, String name) {
        List<User> userList = queryFactory.selectFrom(user)
                .where(eqCity(city),
                        eqBusiness(bussiness),
                        eqName(name)).fetch();

        return userList;
    }

    private BooleanExpression eqCity(CityEnum city) {
        if(city == null) {
            return null;
        }
        return user.city.eq(city);
    }

    private BooleanExpression eqBusiness(Bussiness bussiness) {
        if(bussiness ==null) {
            return null;
        }
        return user.bussiness.eq(bussiness);
    }
    private BooleanExpression eqName(String name) {
        if(!StringUtils.hasText(name)) {
            return user.name.eq(name);
        }
        return null;
    }
}
