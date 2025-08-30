package co.com.crediya.usecase.user;

import co.com.crediya.model.error.BusinessException;
import co.com.crediya.model.error.DatabaseException;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.UserValidations;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id("1").name("Juan")
                .lastName("Perez").identification(123L)
                .email("juan@mail.com").baseSalary(1000)
                .build();
    }

    @Test
    void saveUser_shouldSave_whenUserIsValidAndNotExists() {
        when(userRepository.existsByEmailOrIdentification("juan@mail.com", 123L)).thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectNextMatches(u -> u.getId().equals("1"))
                .verifyComplete();
    }

    @Test
    void saveUser_shouldReturnError_whenUserAlreadyExists() {
        when(userRepository.existsByEmailOrIdentification("juan@mail.com", 123L))
                .thenReturn(Mono.just(true));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    void saveUser_shouldReturnError_whenUserIsInvalid() {
        User invalidUser = User.builder().id("1").name("").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        StepVerifier.create(userUseCase.saveUser(invalidUser))
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_NAME.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void getAllUsers_shouldReturnUsers() {
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

    @Test
    void existUserByEmail_shouldReturnExists() {
        when(userRepository.existsByEmailOrIdentification("correo@example.com", null)).thenReturn(Mono.just(true));
        StepVerifier.create(userUseCase.existUserByEmail("correo@example.com"))
                .expectNext(true)
                .verifyComplete();
        verify(userRepository, times(1))
                .existsByEmailOrIdentification("correo@example.com", null);
    }

    @Test
    void existUserByEmail_shouldReturnNotExists() {
        when(userRepository.existsByEmailOrIdentification("correo@example.com", null))
                .thenReturn(Mono.just(false));
        StepVerifier.create(userUseCase.existUserByEmail("correo@example.com"))
                .expectNext(false)
                .verifyComplete();
        verify(userRepository, times(1))
                .existsByEmailOrIdentification("correo@example.com", null);
    }

    @Test
    void existUserByEmail_ReturnError() {
        when(userRepository.existsByEmailOrIdentification("correo@example.com", null))
                .thenReturn(Mono.error(DatabaseException.Type.DATABASE_ERROR.build()));
        StepVerifier.create(userUseCase.existUserByEmail("correo@example.com"))
                .expectErrorMatches(ex-> ex instanceof DatabaseException)
                .verify();
        verify(userRepository, times(1))
                .existsByEmailOrIdentification("correo@example.com", null);
    }

}
