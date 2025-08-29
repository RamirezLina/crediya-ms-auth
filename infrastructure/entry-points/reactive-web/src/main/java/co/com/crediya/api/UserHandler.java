package co.com.crediya.api;

import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.UserDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.usecase.user.UserUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {
    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;
    private final Validator validator;
    private final UserPath userPath;

    private static void logError(Throwable exception) {
        log.error("Error Message: {} \n Stack trace: {}", exception.getMessage(), exception.getStackTrace());
    }
    
    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDto.class)
                .switchIfEmpty(Mono.error(new ServerWebInputException("El cuerpo de la solicitud es requerido")))
                .flatMap(this::validateDto)                   
                .map(userDtoMapper::toModel)
                .flatMap(userUseCase::saveUser)
                .map(userDtoMapper::toDto)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask))
                .doOnError(UserHandler::logError);
    }

    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {
        log.info("GET  {} : Obteniendo los usuarios registrados", userPath.getUsers() );
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.getAllUsers()
                        .map(userDtoMapper::toDto)
                        .doOnError(UserHandler::logError), UserDto.class);

    }

    private Mono<UserDto> validateDto(UserDto dto) {
        Set<ConstraintViolation<UserDto>> violations = validator.validate(dto) ;
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        log.info("POST {} : Se inicia el guardado del usuario ", userPath.getUsers());
        return Mono.just(dto);
                
    }


    
}
