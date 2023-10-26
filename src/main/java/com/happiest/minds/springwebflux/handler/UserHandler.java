package com.happiest.minds.springwebflux.handler;

import com.happiest.minds.springwebflux.exception.UserNotFoundException;
import com.happiest.minds.springwebflux.entity.User;
import com.happiest.minds.springwebflux.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserHandler {

    @Autowired
    UserRepository userRepository;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(userRepository::save)
                .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
    }

    public Mono<ServerResponse> getUsers() {
        Flux<User> users = userRepository.findAll();
        return ServerResponse.ok().body(users, User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        Mono<User> user = userRepository.findById(userId).switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
        log.info("user: {}", user);
        return ServerResponse.ok().body(user, User.class);
    }

    public Mono<ServerResponse> updateUserById(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        Mono<User> existingUser = userRepository.findById(userId).switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
        return existingUser
                .flatMap(user -> serverRequest.bodyToMono(User.class)
                        .map(userReq -> {
                            user.setFirstname(userReq.getFirstname());
                            user.setLastname(userReq.getLastname());
                            user.setAge(userReq.getAge());
                            user.setEmail(userReq.getEmail());
                            user.setMobile(userReq.getMobile());
                            user.setCity(userReq.getCity());
                            return user;
                        })
                        .flatMap(userRepository::save)
                        .flatMap(savedReview -> ServerResponse.ok().bodyValue(savedReview)));
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("userId");
        Mono<User> user = userRepository.findById(userId).switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
        return user
                .flatMap(user1 -> userRepository.deleteById(userId))
                .then(ServerResponse.noContent().build());
    }

}
