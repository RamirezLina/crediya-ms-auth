package co.com.crediya.usecase.user;

import co.com.crediya.model.error.BusinessException;
import co.com.crediya.model.security.UserSecurity;
import co.com.crediya.model.security.gateways.JwtProvider;
import co.com.crediya.model.security.gateways.PasswordEncoderGateway;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderGateway passwordEncoder;
    private final JwtProvider jwtProvider;


    public Mono<String> execute(UserSecurity credentials) {
        return userRepository.findByEmailWithRole(credentials.getEmail())
                .switchIfEmpty(Mono.error(BusinessException.Type.EMAIL_NOT_EXISTS.build()))
                .filter(user -> passwordEncoder.isPasswordCorrect(credentials.getPassword(), user.getPassword()))
                .map(jwtProvider::generateToken)
                .switchIfEmpty(Mono.error(BusinessException.Type.BAD_CREDENTIALS.build()));
    }
}
