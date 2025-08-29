package co.com.crediya.api;

import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.UserDto;
import co.com.crediya.api.error.ErrorPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final UserPath userPath;
    private final UserHandler userHandler;


    
    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "listenGetAllUsers",
                    operation = @Operation(operationId = "GetAllUsers",
                            summary = "Obtener todos los usuarios",
                            tags = {"API Usuarios"},
                            responses = {@ApiResponse(responseCode = "200", description = "Ususarios obtenidos correctamente",
                                    content = @Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))})
            ),
            @RouterOperation(path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "listenSaveUser",
                    operation = @Operation(operationId = "SaveUser",
                            summary = "Registrar un nuevo usuario",
                            tags = {"API Usuarios"},
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos del usuario a guardar",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDto.class)
                                    )
                            ),
                            responses = {@ApiResponse(responseCode = "200", description = "Usuario registrado correctamente.",
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Error de validacion",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPayload.class)))})
            )})
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(GET(userPath.getUsers()), this.userHandler::listenGetAllUsers)
                .andRoute(POST(userPath.getUsers()), this.userHandler::listenSaveUser);
    }
    
}
