package br.com.robertomassoni.mancala.repository.redis.adapter;

import br.com.robertomassoni.mancala.core.exception.GameNotFoundException;
import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import br.com.robertomassoni.mancala.repository.redis.repository.GameRepository;
import mock.domain.GameMock;
import mock.entity.GameEntityMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GamePersistenceImplTest {

    private GameRepository repository;
    private GamePersistence persistence;

    @BeforeEach
    public void setUp() {
        repository = mock(GameRepository.class);
        persistence = new GamePersistenceImpl(repository);
    }

    @Test
    public void shouldCallRepositorySave() {
        final var actualGame = GameMock.createNewGame();
        final var actualGameEntity = GameEntityMock.createNewGame();
        when(repository.save(any())).thenReturn(actualGameEntity);

        persistence.save(actualGame);

        verify(repository, only()).save(any());
    }

    @Test
    public void shouldCallRepositoryFindById() {
        final var actualGameEntity = GameEntityMock.createNewGame();
        when(repository.findById(any())).thenReturn(Optional.of(actualGameEntity));

        persistence.findById(UUID.randomUUID());

        verify(repository, only()).findById(any());
    }

    @Test
    public void shouldThrowExceptionWhenGameDoesNotExists() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            persistence.findById(UUID.randomUUID());
        }).isInstanceOf(GameNotFoundException.class);
    }
}