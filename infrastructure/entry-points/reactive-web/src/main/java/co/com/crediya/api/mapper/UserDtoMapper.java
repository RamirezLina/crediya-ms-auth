package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.CreateUserDto;
import co.com.crediya.api.dto.UserLoginDto;
import co.com.crediya.api.dto.UserResponseDto;
import co.com.crediya.model.user.User;
import co.com.crediya.model.security.UserSecurity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    
    User toModel(CreateUserDto dto);
    UserResponseDto toResponseDto(User user);
    UserSecurity toLoginModel(UserLoginDto dtoCredentials);
    
}
