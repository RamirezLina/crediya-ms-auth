package co.com.crediya.api;

import co.com.crediya.api.docs.OpenApiControllerDoc;
import co.com.crediya.api.config.UserPath;
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

    private final UserPath userPath;
    private final UserHandler userHandler;


    @Bean
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(GET(userPath.getUsers()), this.userHandler::listenGetAllUsers)
                .andRoute(POST(userPath.getUsers()), this.userHandler::listenSaveUser)
                .andRoute(GET(userPath.getExistUserByEmail()), this.userHandler::listenExistUserByEmail);
    }

}
