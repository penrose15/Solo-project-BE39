package api.v1.mapper;

import api.v1.dto.PatchUserDto;
import api.v1.dto.PostUserDto;
import api.v1.dto.ResponseUserDto;
import api.v1.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User postDtoToUser(PostUserDto postUserDto);

    User patchDtoToUser(PatchUserDto patchDto);

    ResponseUserDto userToResponseDto(User user);

    default List<ResponseUserDto> userToResponseDtos(List<User> list) {
        List<ResponseUserDto> response = list.stream()
                .map(user -> userToResponseDto(user))
                .collect(Collectors.toList());
        return response;
    }
}
