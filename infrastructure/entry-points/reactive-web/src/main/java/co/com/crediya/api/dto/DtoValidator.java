package co.com.crediya.api.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DtoValidator {

    private final Validator validator;
    

    public Mono<IDto> validateDto(IDto dto) {
        Set<ConstraintViolation<IDto>> violations = validator.validate(dto) ;
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        return Mono.just(dto);

    }
    
}
