package co.com.crediya.usecase.user;

import co.com.crediya.model.error.BusinessException;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveUser_shouldSave_whenUserIsValidAndNotExists() {
        User user = User.builder().id("1").name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        when(userRepository.existsByEmailOrIdentification(eq("juan@mail.com"), eq(123L))).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectNextMatches(u -> u.getId().equals("1"))
                .verifyComplete();
    }

    @Test
    void saveUser_shouldReturnError_whenUserAlreadyExists() {
        User user = User.builder().id("1").name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        when(userRepository.existsByEmailOrIdentification(eq("juan@mail.com"), eq(123L))).thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    void saveUser_shouldReturnError_whenUserIsInvalid() {
        User user = User.builder().id("1").name("").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(IllegalArgumentException.class)
                .verify();
    }

    @Test
    void getAllUsers_shouldReturnUsers() {
        User user = User.builder().id("1").name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        when(userRepository.findAll()).thenReturn(Flux.just(user));
        StepVerifier.create(userUseCase.getAllUsers())
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void getAllUsers_shouldReturnError_whenRepositoryFails() {
        when(userRepository.findAll()).thenReturn(Flux.error(new RuntimeException("DB error")));
        StepVerifier.create(userUseCase.getAllUsers())
                .expectError(RuntimeException.class)
                .verify();
    }
}

