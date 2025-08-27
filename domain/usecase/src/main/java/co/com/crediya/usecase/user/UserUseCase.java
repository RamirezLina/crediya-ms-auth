package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {
    
    private final UserRepository userRepository;

    public Mono<User> saveUser(User newUser) {
        newUser.validate();
        return userRepository.save(newUser);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
