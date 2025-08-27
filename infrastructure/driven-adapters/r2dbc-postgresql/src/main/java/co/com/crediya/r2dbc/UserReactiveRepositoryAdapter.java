package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
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
    public Mono<User> save(User User) {
        return super.save(User);
    }
    @Override
    public Flux<User> findAll() {
        return super.findAll();
    }
    
}
