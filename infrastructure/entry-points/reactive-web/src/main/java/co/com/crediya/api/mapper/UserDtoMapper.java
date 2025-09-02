package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.UserDto;
import co.com.crediya.api.dto.UserLoginDto;
import co.com.crediya.api.dto.UserResponseDto;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.UserSecurity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    
    User toModel(UserDto dto);
    UserResponseDto toResponseDto(User user);
    UserSecurity toLoginModel(UserLoginDto dtoCredentials);
    
}
