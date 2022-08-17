package api.v1.controller;

import api.v1.dto.PatchUserDto;
import api.v1.dto.PostUserDto;
import api.v1.entity.User;
import api.v1.entity.bussinessType.Bussiness;
import api.v1.entity.region.CityEnum;
import api.v1.entity.region.RegionEnum;
import api.v1.exception.BusinessLogicException;
import api.v1.exception.ErrorCode;
import api.v1.mapper.UserMapper;
import api.v1.responsedtos.MultiResponseDto;
import api.v1.responsedtos.SingleResponseDto;
import api.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;


    @PostMapping
    public ResponseEntity postUser(@RequestBody PostUserDto postUserDto) {
        User user = mapper.postDtoToUser(postUserDto);
        User response = userService.saveUser(user);

        return new ResponseEntity(new SingleResponseDto<>(mapper.userToResponseDto(response)), HttpStatus.CREATED);
    }

    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(@PathVariable("user-id") Long userId,
                                    @RequestBody PatchUserDto patchUserDto) {
        patchUserDto.setUserId(userId);
        User user = mapper.patchDtoToUser(patchUserDto);
        User response = userService.updateUser(user);
//        verified(response.getRegion(),response.getCity());
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.userToResponseDto(response)),HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") Long userId) {
        User user = userService.findUserById(userId);
        return new ResponseEntity(new SingleResponseDto<>(mapper.userToResponseDto(user)),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity search(@Nullable @RequestParam(value = "city",required = false) String city,
                                      @Nullable @RequestParam(value = "bussiness",required = false) String bussiness,
                                      @Nullable @RequestParam(value = "name",required = false) String name,
                                       @RequestParam int page,
                                       @RequestParam int size) {
        Page<User> userPage = userService.findAllByUserCustomRepositoryImpl(page-1,size, CityEnum.valueOf(CityEnum.class,city), Bussiness.valueOf(Bussiness.class,bussiness),name);
        List<User> list = userPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.userToResponseDtos(list),userPage),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllUsers(@RequestParam int page,
                                      @RequestParam int size) {
        Page<User> user = userService.findAll(page-1, size);
        List<User> list = user.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.userToResponseDtos(list),user),
        HttpStatus.OK);
    }
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser (@PathVariable("user-id") Long userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    public void verified(RegionEnum region, CityEnum city) {
//
//        if(city.getParent() == region) {
//            return;
//        }
//        else {
//            throw new BusinessLogicException(ErrorCode.REGION_CITY_NOT_MATCH);
//        }
//    }


}
