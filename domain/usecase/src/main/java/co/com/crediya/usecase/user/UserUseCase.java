package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.error.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser(User newUser) {
        return newUser.validate()
                .map(User::setDefaultValues)
                .flatMap(user -> userRepository.existsByEmailOrIdentification(
                        user.getEmail(),
                        user.getIdentification()))
                .flatMap(exists -> exists
                        ? Mono.error(BusinessException.Type.EMAIL_ALREADY_EXISTS.build())
                        : userRepository.save(newUser)
                );
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<Boolean> existUserByEmail(String email) {
        return userRepository.existsByEmailOrIdentification(email, null);
    }
}
