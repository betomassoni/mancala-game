package br.com.robertomassoni.mancala.repository.redis.mapper;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.repository.redis.entity.GameEntity;
import mock.domain.GameMock;
import mock.entity.GameEntityMock;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameEntityMapperTest {

    @Test
    public void shouldMapGameEntitySuccess() {
        final var actualGame = GameEntityMock.createNewGame();

        final var expectedGameResponse = GameEntityMapper.INSTANCE.mapFrom(actualGame);

        assertThat(expectedGameResponse.getId().toString()).isEqualTo(actualGame.getId().toString());
        assertThat(expectedGameResponse.getStatus().name()).isEqualTo(actualGame.getStatus());
        assertThat(expectedGameResponse.getPlayersBoard().size()).isEqualTo(2);
        assertThat(expectedGameResponse.getWinner().name()).isEqualTo(actualGame.getWinner());
        assertThat(expectedGameResponse.getPlayerTurn().name()).isEqualTo(actualGame.getPlayerTurn());
    }

    @Test
    public void shouldNotMapGameWhenSourceIsNull() {
        final var expectedGame = GameEntityMapper.INSTANCE.mapFrom((GameEntity) null);

        assertThat(expectedGame).isNull();
    }

    @Test
    public void shouldMapGameSuccess() {
        final var actualGame = GameMock.createNewGame();

        final var expectedGameResponse = GameEntityMapper.INSTANCE.mapFrom(actualGame);

        assertThat(expectedGameResponse.getId().toString()).isEqualTo(actualGame.getId().toString());
        assertThat(expectedGameResponse.getStatus()).isEqualTo(actualGame.getStatus().name());
        assertThat(expectedGameResponse.getPlayersBoard().size()).isEqualTo(2);
        assertThat(expectedGameResponse.getWinner()).isEqualTo(actualGame.getWinner().name());
        assertThat(expectedGameResponse.getPlayerTurn()).isEqualTo(actualGame.getPlayerTurn().name());
    }

    @Test
    public void shouldNotMapGameEntityWhenSourceIsNull() {
        final var expectedGame = GameEntityMapper.INSTANCE.mapFrom((Game) null);

        assertThat(expectedGame).isNull();
    }
}