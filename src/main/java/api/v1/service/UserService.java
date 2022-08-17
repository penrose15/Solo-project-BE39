package api.v1.service;

import api.v1.entity.User;
import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import api.v1.exception.BusinessLogicException;
import api.v1.exception.ErrorCode;
import api.v1.repository.UserCustomRepositoryImpl;
import api.v1.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserCustomRepositoryImpl userCustomRepository;

    public UserService(UserRepository userRepository, UserCustomRepositoryImpl userCustomRepository) {
        this.userRepository = userRepository;
        this.userCustomRepository = userCustomRepository;
    }

    public User saveUser(User user) {
        existUser(user.getEmail());
        return userRepository.save(user);
    }
    public User updateUser(User user) {
        User findUser = verifiedUser(user.getUserId());
        Optional.ofNullable(user.getName())
                .ifPresent(name -> findUser.setName(name));
        Optional.ofNullable(user.getBussiness())
                .ifPresent(business -> findUser.setBussiness(business));
        Optional.ofNullable(user.getCity())
                .ifPresent(city -> findUser.setCity(city));
        Optional.ofNullable(user.getRegion())
                .ifPresent(region -> findUser.setRegion(region));

        return findUser;
    }
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC,"name");
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }
    public Page<User> findAllByUserCustomRepositoryImpl(int page, int size, CityEnum city, Bussiness business,String name) {
        //이가 없으면 잇몸으로 하면 된다
        List<User> userList = userCustomRepository.findByRegionOrBusinessOrName(city,business,name);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC,"name");
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start+pageable.getPageSize()), userList.size());
        final Page<User> pages = new PageImpl<>(userList.subList(start,end), pageable, userList.size());
        return pages;
    }

    public void deleteUser(Long id) {
        verifiedUserExist(id);
        userRepository.deleteById(id);
    }
    public User findUserById(Long id) {
        return verifiedUser(id);
    }

    private User verifiedUser(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        User user = findUser.orElseThrow(() -> new BusinessLogicException(ErrorCode.MEMBER_NOT_FOUND));
        return user;
    }

    private void verifiedUserExist(Long id) {
        Optional<User> findUser = userRepository.findById(id);
        if(findUser.isPresent()) {
            return;
        }
        else {
            throw new BusinessLogicException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private void existUser(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isPresent()){
            throw new BusinessLogicException(ErrorCode.MEMBER_ALREADY_EXIST);
        }
    }



}
