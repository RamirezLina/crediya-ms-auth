package co.com.crediya.r2dbc;

import co.com.crediya.model.error.DatabaseException;
import co.com.crediya.model.user.User;
import co.com.crediya.model.security.UserSecurity;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.mapper.UserWithRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
@Transactional
@Slf4j
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
        > implements UserRepository {

    private final UserWithRoleMapper userWithRoleMapper;
    
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, UserWithRoleMapper userWithRoleMapper) {
        super(repository, mapper, userEntity -> mapper.map(userEntity, User.class));
        this.userWithRoleMapper = userWithRoleMapper;
    }

    @Override
    public Mono<User> save(User user) {
        log.info("[SAVE USER]: Guardando usuario en base de datos");
        return super.save(user)
                .doOnError(UserReactiveRepositoryAdapter::logError)
                .onErrorMap(DataIntegrityViolationException.class, DatabaseException.Type.ROL_NOT_EXISTS::build);

    }

    @Override
    public Flux<User> findAll() {
        log.info("[GET ALL USERS]: Recuperando usuarios de la base de datos");
        return super.findAll()
                .doOnError(UserReactiveRepositoryAdapter::logError);
    }

    @Override
    public Mono<User> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(super::toEntity)
                .filter(Objects::nonNull)
                .doOnError(UserReactiveRepositoryAdapter::logError);
    }

    @Override
    public Mono<Boolean> existsByEmailOrIdentification(String email, Long identification) {
        return repository.existsByEmailOrIdentification(email, identification)
                .doOnError(UserReactiveRepositoryAdapter::logError);
    }

    @Override
    public Mono<UserSecurity> findByEmailWithRole(String email) {
        return repository.findByEmailWithRole(email)
                .map(userWithRoleMapper::toModel)
                .filter(Objects::nonNull)
                .doOnError(UserReactiveRepositoryAdapter::logError);
    }

    private static void logError(Throwable exception) {
        log.error("Error Message: {} \n Stack trace: {}", exception.getMessage(), exception.getStackTrace());
    }

}
