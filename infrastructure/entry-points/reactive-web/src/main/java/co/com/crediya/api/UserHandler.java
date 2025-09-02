package co.com.crediya.api;

import co.com.crediya.api.config.Path;
import co.com.crediya.api.dto.DtoValidator;
import co.com.crediya.api.dto.UserDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserHandler {
    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;
    private final DtoValidator validator;
    private final Path path;

    private static void logError(Throwable exception) {
        log.error("Error Message: {} \n Stack trace: {}", exception.getMessage(), exception.getStackTrace());
    }
    
    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDto.class)
                .switchIfEmpty(Mono.error(new ServerWebInputException("El cuerpo de la solicitud es requerido")))
                .flatMap(validator::validateDto)
                .map(dto -> (UserDto) dto)
                .map(userDtoMapper::toModel)
                .doOnNext(user-> log.info("POST  {} [SAVE USER]: Iniciando el guardado del usuario", path.getUsers()))
                .flatMap(userUseCase::saveUser)
                .map(userDtoMapper::toResponseDto)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask))
                .doOnError(UserHandler::logError);
    }

    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {
        log.info("GET  {} [GET ALL USERS]: Obteniendo los usuarios registrados", path.getUsers() );
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userUseCase.getAllUsers()
                        .map(userDtoMapper::toResponseDto)
                        .doOnError(UserHandler::logError), UserDto.class);

    }

    public Mono<ServerResponse> listenExistUserByEmail(ServerRequest serverRequest) {
        log.info("GET  {} [EXIST USER BY EMAIL] : Consultando el usuario por email", path.getExistUserByEmail());
        String email = serverRequest.pathVariable("email");
        return userUseCase.existUserByEmail(email)
                .flatMap(exists -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(exists))
                .doOnError(UserHandler::logError);
    }
}
