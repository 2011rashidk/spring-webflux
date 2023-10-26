package com.happiest.minds.springwebflux.router;

import com.happiest.minds.springwebflux.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoute(UserHandler userHandler) {
        return route()
                .nest(path("api/webflux/user"), builder -> {
                    builder.POST("", request -> userHandler.createUser(request))
                            .GET("", request -> userHandler.getUsers())
                            .GET("/{userId}", request -> userHandler.getUserById(request))
                            .PUT("/{userId}", request -> userHandler.updateUserById(request))
                            .DELETE("/{userId}", request -> userHandler.deleteUserById(request));
                })
                .build();
    }

}
