package co.com.crediya.usecase.user;

import co.com.crediya.model.security.gateways.PasswordEncoderGateway;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.error.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public Mono<User> saveUser(User newUser) {
        return newUser.validate()
                .map(User::setDefaultValues)
                .flatMap(user -> userRepository.existsByEmailOrIdentification(
                        user.getEmail(),
                        user.getIdentification()))
                .flatMap(exists -> exists
                        ? Mono.error(BusinessException.Type.EMAIL_ALREADY_EXISTS.build())
                        : saveNewUser(newUser)
                );
    }

    private Mono<User> saveNewUser(User newUser) {
        newUser.setPassword(passwordEncoderGateway.encodePassword(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.getByEmail(email)
                .switchIfEmpty(Mono.error(BusinessException.Type.EMAIL_NOT_EXISTS.build()));
    }

    public Mono<Boolean> existUserByEmail(String email) {
        return userRepository.existsByEmailOrIdentification(email, null);
    }
}
