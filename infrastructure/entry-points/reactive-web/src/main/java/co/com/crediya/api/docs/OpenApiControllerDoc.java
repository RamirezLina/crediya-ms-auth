package co.com.crediya.api.docs;

import co.com.crediya.api.AuthHandler;
import co.com.crediya.api.UserHandler;
import co.com.crediya.api.dto.CreateUserDto;
import co.com.crediya.api.dto.TokenDto;
import co.com.crediya.api.dto.UserLoginDto;
import co.com.crediya.api.error.ErrorPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface OpenApiControllerDoc {

    @RouterOperations({
            @RouterOperation(path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "listenGetAllUsers",
                    operation = @Operation(operationId = "GetAllUsers",
                            summary = "Obtener todos los usuarios",
                            tags = {"API Usuarios"},
                            responses = {@ApiResponse(responseCode = "200", description = "Ususarios obtenidos correctamente",
                                    content = @Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = CreateUserDto.class)))),
                                    @ApiResponse(responseCode = "401", description = "No autenticado",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "403", description = "Acceso prohibido: el usuario no esta autorizado",
                                            content = @Content(mediaType = "application/json"))
                            })
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
                                            schema = @Schema(implementation = CreateUserDto.class)
                                    )
                            ),
                            responses = {@ApiResponse(responseCode = "200", description = "Usuario registrado correctamente.",
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Error de validacion",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPayload.class))),
                                    @ApiResponse(responseCode = "401", description = "No autenticado",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "403", description = "Acceso prohibido: el usuario no esta autorizado",
                                            content = @Content(mediaType = "application/json"))})
            ),
            @RouterOperation(path = "/api/v1/usuarios/email/{email}",
                    produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "listenExistUserByEmail",
                    operation = @Operation(operationId = "ExistsUserByEmail",
                            summary = "Validar la existencia de un usuario segun el email",
                            tags = {"API Usuarios"},
                            parameters = {@Parameter(name = "email", description = "Email del usuario a buscar", required = true, in = ParameterIn.PATH)},
                            responses = {@ApiResponse(responseCode = "200", description = "Respuesta encontrada",
                                    content = @Content(mediaType = "application/json", schema = @Schema(type = "boolean"))),
                                    @ApiResponse(responseCode = "401", description = "No autenticado",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "403", description = "Acceso prohibido: el usuario no esta autorizado",
                                            content = @Content(mediaType = "application/json"))})

            ),
            @RouterOperation(path = "/api/v1/usuarios/detail/{email}",
                    produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "listenGetUserByEmail",
                    operation = @Operation(operationId = "GetUserByEmail",
                            summary = "Obtener la inforamcion de un usuario segun el email",
                            tags = {"API Usuarios"},
                            parameters = {@Parameter(name = "email", description = "Email del usuario a buscar", required = true, in = ParameterIn.PATH)},
                            responses = {@ApiResponse(responseCode = "200", description = "Usuario encontrado",
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Error de validacion",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPayload.class))),
                                    @ApiResponse(responseCode = "401", description = "No autenticado",
                                            content = @Content(mediaType = "application/json")),
                                    @ApiResponse(responseCode = "403", description = "Acceso prohibido: el usuario no esta autorizado",
                                            content = @Content(mediaType = "application/json"))})
    )})
    RouterFunction<ServerResponse> routerUserFunction(UserHandler userHandler);

    @RouterOperations({
            @RouterOperation(path = "/api/v1/login",
                    produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST, beanClass = AuthHandler.class, beanMethod = "listenLogIn",
                    operation = @Operation(operationId = "Login",
                            summary = "Inicio de sesion de usuario de la plataforma",
                            tags = {"API LogIn"},
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Crendenciales del usuario",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserLoginDto.class)
                                    )
                            ),
                            responses = {@ApiResponse(responseCode = "200", description = "El usuario a ingresado correctamente",
                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Error de validacion",
                                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPayload.class)))
                            })
            )})
    RouterFunction<ServerResponse> routerAuthFunction(AuthHandler authHandler);
}
