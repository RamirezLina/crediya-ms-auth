package co.com.crediya.usecase.user;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoginUseCase {

    public Mono<String> execute() {
        return Mono.just("Intento de loggeo exitoso");
    }
}
