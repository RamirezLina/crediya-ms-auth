package co.com.crediya.api;

import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.UserDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.model.user.User;
import co.com.crediya.usecase.user.UserUseCase;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {
    @Mock
    private UserUseCase userUseCase;
    @Mock
    private UserDtoMapper userDtoMapper;
    @Mock
    private Validator validator;
    @Mock
    private UserPath userPath;
    @Mock
    private ServerRequest serverRequest;

    @InjectMocks
    private UserHandler userHandler;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id("1")
                .name("Juan")
                .lastName("Perez")
                .identification(123L)
                .email("juan@mail.com")
                .baseSalary(1000)
                .build();
        userDto = new UserDto("Juan", "Serrano",
                1111L, LocalDate.of(2024, 6, 10),
                null, 3333L, "juan@mail.com", 3L,
                10000);
    }

    @Test
    void listenGetAllUsers_shouldReturnUsers() {
        when(userUseCase.getAllUsers()).thenReturn(Flux.just(user));
        when(userPath.getUsers()).thenReturn("/users");

        Mono<ServerResponse> response = userHandler.listenGetAllUsers(serverRequest);

        StepVerifier.create(response)
                .assertNext(serverResponse -> assertEquals(MediaType.APPLICATION_JSON, serverResponse
                        .headers().getContentType()))
                .verifyComplete();

        verify(userDtoMapper, times(0)).toDto(any(User.class));
        verify(userUseCase, times(1)).getAllUsers();
        verifyNoMoreInteractions(userUseCase);
    }

    @Test
    void listenSaveUser_Successfully() {
        when(userUseCase.saveUser(any(User.class))).thenReturn(Mono.just(user));
        when(userDtoMapper.toDto(user)).thenReturn(userDto);
        when(userDtoMapper.toModel(userDto)).thenReturn(user);
        when(userPath.getUsers()).thenReturn("/users");
        when(serverRequest.bodyToMono(UserDto.class)).thenReturn(Mono.just(userDto));

        Mono<ServerResponse> response = userHandler.listenSaveUser(serverRequest);

        StepVerifier.create(response)
                .assertNext(serverResponse -> assertEquals(MediaType.APPLICATION_JSON, serverResponse
                        .headers().getContentType()))
                .verifyComplete();

        verify(userDtoMapper, times(1)).toDto(any(User.class));
        verify(userDtoMapper, times(1)).toModel(any(UserDto.class));
        verify(userUseCase, times(1)).saveUser(any(User.class));
        verifyNoMoreInteractions(userUseCase);
    }

   


}
