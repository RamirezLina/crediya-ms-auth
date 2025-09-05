package co.com.crediya.model.security.gateways;

import co.com.crediya.model.security.UserSecurity;

public interface JwtProvider {

    String generateToken(UserSecurity userSecurity);
}
