package co.com.crediya.r2dbc;

import co.com.crediya.model.error.DatabaseException;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
@Slf4j
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
        > implements UserRepository {

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, userEntity -> mapper.map(userEntity, User.class));
    }

    @Override
    public Mono<User> save(User user) {
        log.info("Guardando usuario en base de datos");
        return super.save(user)
                .doOnError(UserReactiveRepositoryAdapter::logError)
                .onErrorMap(DataIntegrityViolationException.class, DatabaseException.Type.ROL_NOT_EXISTS::build);

    }

    @Override
    public Flux<User> findAll() {
        log.info("Recuperando usuarios de la base de datos");
        return super.findAll()
                .doOnError(UserReactiveRepositoryAdapter::logError);
    }

    @Override
    public Mono<Boolean> existsByEmailOrIdentification(String email, Long identification) {
        return repository.existsByEmailOrIdentification(email, identification)
                .doOnError(UserReactiveRepositoryAdapter::logError);
    }

    private static void logError(Throwable exception) {
        log.error("Error Message: {} \n Stack trace: {}", exception.getMessage(), exception.getStackTrace());
    }

}
