package co.com.crediya.usecase.user;

import co.com.crediya.model.user.UserSecurity;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {
    
    private final UserRepository userRepository;

    public Mono<UserSecurity> execute(UserSecurity credentials) {
        return userRepository.findByEmailWithRole(credentials.getEmail());
    }
}
