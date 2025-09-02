package co.com.crediya.api;

import co.com.crediya.api.config.Path;
import co.com.crediya.api.dto.DtoValidator;
import co.com.crediya.api.dto.UserLoginDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.usecase.user.LoginUseCase;
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
public class AuthHandler {
    private final LoginUseCase loginUseCase;
    private final UserDtoMapper userDtoMapper;
    private final DtoValidator validator;
    private final Path path;

    private static void logError(Throwable exception) {
        log.error("Error Message: {} \n Stack trace: {}", exception.getMessage(), exception.getStackTrace());
    }
    
    public Mono<ServerResponse> listenLogIn(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserLoginDto.class)
                .switchIfEmpty(Mono.error(new ServerWebInputException("El cuerpo de la solicitud es requerido")))
                .flatMap(validator::validateDto)
                .map(dto -> (UserLoginDto) dto)
                .doOnNext(user-> log.info("POST  {} [LOGIN USER]: Validando usuario para inicio de sesion", path.getUsers()))
                .flatMap(Dto->  loginUseCase.execute())
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result))
                .doOnError(AuthHandler::logError);
    }
        

    


    
}
