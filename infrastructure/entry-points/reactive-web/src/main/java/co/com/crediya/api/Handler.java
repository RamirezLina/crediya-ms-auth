package co.com.crediya.api;

import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.UserDto;
import co.com.crediya.api.mapper.UserDtoMapper;
import co.com.crediya.usecase.user.UserUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final UserUseCase userUseCase;
    private final UserDtoMapper userDtoMapper;
    private final Validator validator;
    private final UserPath userPath;
    

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDto.class)
                .doOnNext(this::validateDto)
                .map(userDtoMapper::toModel)
                .flatMap(userUseCase::saveUser)
                .map(userDtoMapper::toDto)
                .flatMap(savedTask -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedTask));
    }

    private void validateDto(UserDto userDto) {
        var errors = new BeanPropertyBindingResult(userDto, UserDto.class.getName());
        new SpringValidatorAdapter(validator).validate(userDto, errors);
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        log.info(" POST  {} : Se inicia el guardado del usuario con email {}", userPath.getUsers(), userDto.email() );
        
    }


    public Mono<ServerResponse> listenGetAllUsers(ServerRequest serverRequest) {
        log.info("GET  {} : Obteniendo los usuarios registrados", userPath.getUsers() );
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(userUseCase.getAllUsers().map(userDtoMapper::toDto), UserDto.class);
    }
}
