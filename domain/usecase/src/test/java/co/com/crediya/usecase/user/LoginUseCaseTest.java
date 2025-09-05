package co.com.crediya.usecase.user;

import co.com.crediya.model.error.BusinessException;
import co.com.crediya.model.security.UserSecurity;
import co.com.crediya.model.security.gateways.JwtProvider;
import co.com.crediya.model.security.gateways.PasswordEncoderGateway;
import co.com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoderGateway passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private LoginUseCase loginUseCase;

    private UserSecurity creds;
    private UserSecurity stored;

    @BeforeEach
    void setUp() {
        creds = UserSecurity.builder()
                .email("user@mail.com")
                .password("plain")
                .build();

        stored = UserSecurity.builder()
                .email("user@mail.com")
                .password("encoded")
                .rol("USER")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpire(true)
                .build();
    }

    @Test
    void execute_shouldReturnToken_onValidCredentials() {
        when(userRepository.findByEmailWithRole("user@mail.com")).thenReturn(Mono.just(stored));
        when(passwordEncoder.isPasswordCorrect("plain", "encoded")).thenReturn(true);
        when(jwtProvider.generateToken(any(UserSecurity.class))).thenReturn("jwt-token");

        StepVerifier.create(loginUseCase.execute(creds))
                .expectNext("jwt-token")
                .verifyComplete();
    }

    @Test
    void execute_shouldError_whenEmailNotFound() {
        when(userRepository.findByEmailWithRole("user@mail.com")).thenReturn(Mono.empty());

        StepVerifier.create(loginUseCase.execute(creds))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    void execute_shouldError_whenBadCredentials() {
        when(userRepository.findByEmailWithRole("user@mail.com")).thenReturn(Mono.just(stored));
        when(passwordEncoder.isPasswordCorrect(eq("plain"), eq("encoded"))).thenReturn(false);

        StepVerifier.create(loginUseCase.execute(creds))
                .expectError(BusinessException.class)
                .verify();
    }
}

