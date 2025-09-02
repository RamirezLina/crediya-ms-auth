package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.user.UserSecurity;
import co.com.crediya.r2dbc.entity.UserWithRole;
import org.springframework.stereotype.Component;

@Component
public class UserWithRoleMapper {

    public UserSecurity toModel(UserWithRole userWithRole) {
        if (userWithRole == null) {
            return null;
        }
        return UserSecurity.builder()
                .email(userWithRole.email())
                .password(userWithRole.password())
                .rol(userWithRole.rol())
                .isEnabled(userWithRole.enabled())
                .accountNoExpired(userWithRole.noexpired())
                .accountNoLocked(userWithRole.nolocked())
                .credentialNoExpire(userWithRole.credentialnoexpired())
                .build();
    }
}
