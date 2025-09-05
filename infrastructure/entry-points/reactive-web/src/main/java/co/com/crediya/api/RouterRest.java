package co.com.crediya.api;

import co.com.crediya.api.docs.OpenApiControllerDoc;
import co.com.crediya.api.config.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest implements OpenApiControllerDoc {

    private final Path path;
    private final UserHandler userHandler;
    private final AuthHandler authHandler;


    @Bean
    public RouterFunction<ServerResponse> routerUserFunction(UserHandler userHandler) {
        return route(GET(path.getUsers()), this.userHandler::listenGetAllUsers)
                .andRoute(POST(path.getUsers()), this.userHandler::listenSaveUser)
                .andRoute(GET(path.getExistUserByEmail()), this.userHandler::listenExistUserByEmail);
    }

    @Bean
    public RouterFunction<ServerResponse> routerAuthFunction(AuthHandler authHandler) {
        return route(POST(path.getLogin()), this.authHandler::listenLogIn);
    }
    
}
