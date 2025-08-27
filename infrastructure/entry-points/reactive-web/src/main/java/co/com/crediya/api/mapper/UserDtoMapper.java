package co.com.crediya.api.mapper;

import co.com.crediya.api.dto.UserDto;
import co.com.crediya.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    
    User toModel(UserDto dto);
    UserDto toDto(User user);
    
}
