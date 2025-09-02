package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.UserSecurity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface UserRepository {

    Mono<User> save(User newUser);

    Flux<User> findAll();

    Mono<Boolean> existsByEmailOrIdentification(String email, Long identification);

    Mono<UserSecurity> findByEmailWithRole(String email);
}
