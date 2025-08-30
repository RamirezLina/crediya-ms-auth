package co.com.crediya.api;

import co.com.crediya.api.config.OpenApiControllerDoc;
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
public class RouterRest implements OpenApiControllerDoc {

    private final UserPath userPath;
    private final UserHandler userHandler;


    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(GET(userPath.getUsers()), this.userHandler::listenGetAllUsers)
                .andRoute(POST(userPath.getUsers()), this.userHandler::listenSaveUser)
                .andRoute(GET(userPath.getExistUserByEmail()), this.userHandler::listenExistUserByEmail);
    }

}
