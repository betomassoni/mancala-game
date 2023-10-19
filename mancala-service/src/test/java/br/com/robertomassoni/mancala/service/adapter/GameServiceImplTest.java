package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import mock.GameMock;
import mock.SowPitMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class GameServiceImplTest {

    private GamePersistence persistence;
    private GameServiceImpl service;

    @BeforeEach
    public void setUp() {
        persistence = Mockito.mock(GamePersistence.class);
        service = new GameServiceImpl(persistence);
    }

    @Test
    public void shouldCallRepositoryWhenNewGameIsCreated() {
        service.createGame();

        Mockito.verify(persistence, only()).save(any());
    }

    @Test
    public void shouldCallRepositoryWhenPlayerPlays() {
        var actualSowPit = SowPitMock.create();
        var actualGame = GameMock.createNewGame();
        when(persistence.findById(any())).thenReturn(actualGame);

        service.sow(actualSowPit);

        Mockito.verify(persistence, times(1)).save(any());
        Mockito.verify(persistence, times(1)).findById(any());
    }
}