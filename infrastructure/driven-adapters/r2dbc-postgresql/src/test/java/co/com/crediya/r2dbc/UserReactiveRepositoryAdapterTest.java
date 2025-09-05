package co.com.crediya.r2dbc;

import co.com.crediya.model.error.DatabaseException;
import co.com.crediya.model.user.User;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;
    
    User user;
    
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .identification(123L)
                .email("juan@mail.com")
                .baseSalary(1000)
                .build();
    }

    @Test
    void save_shouldReturnUser_whenSuccess() {
        when(mapper.map(any(User.class), any())).thenReturn(new UserEntity());
        when(repository.save(any())).thenReturn(Mono.just(new UserEntity()));
        when(mapper.map(any(UserEntity.class), any())).thenReturn(user);
        
        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getId().equals(1L) && u.getName().equals("Juan"))
                .verifyComplete();
        verify(repository, times(1)).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void save_shouldReturnDatabaseException_whenDataIntegrityViolation() {
        when(mapper.map(any(User.class), any())).thenReturn(new UserEntity());
        when(repository.save(any())).thenReturn(Mono.error(new DataIntegrityViolationException("Rol not exists")));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectError(DatabaseException.class)
                .verify();
        verify(repository, times(1)).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll_shouldReturnUsers_whenSuccess() {
        when(repository.findAll()).thenReturn(Flux.just(new UserEntity()));
        when(mapper.map(any(), any())).thenReturn(user);

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void findAll_shouldLogError_whenException() {
        when(repository.findAll()).thenReturn(Flux.error(new RuntimeException("DB error")));
        Flux<User> result = repositoryAdapter.findAll();
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void existsByEmailOrIdentification_shouldReturnTrue_whenExists() {
        when(repository.existsByEmailOrIdentification("test@mail.com", 123L))
                .thenReturn(Mono.just(true));
        
        Mono<Boolean> result = repositoryAdapter.existsByEmailOrIdentification("test@mail.com", 123L);
        
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsByEmailOrIdentification_shouldReturnError_whenException() {
        when(repository.existsByEmailOrIdentification("test@mail.com", 123L))
                .thenReturn(Mono.error(new RuntimeException("DB error")));
        
        Mono<Boolean> result = repositoryAdapter.existsByEmailOrIdentification("test@mail.com", 123L);
        
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}
