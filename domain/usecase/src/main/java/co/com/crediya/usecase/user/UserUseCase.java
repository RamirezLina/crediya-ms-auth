package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase{
    
    private final UserRepository userRepository;


    @Override
    public Mono<User> save(User newUser) {
        newUser.validate();
        return userRepository.save(newUser);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
