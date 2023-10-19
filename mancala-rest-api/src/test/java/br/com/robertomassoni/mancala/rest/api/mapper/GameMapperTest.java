package br.com.robertomassoni.mancala.rest.api.mapper;

import mock.GameMock;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameMapperTest {

    @Test
    public void shouldMapGameSuccess() {
        final var actualGame = GameMock.createNewGame();

        final var expectedGameResponse = GameMapper.INSTANCE.mapFrom(actualGame);

        assertThat(expectedGameResponse.getId()).isEqualTo(actualGame.getId().toString());
        assertThat(expectedGameResponse.getStatus()).isEqualTo(actualGame.getStatus().name());
        assertThat(expectedGameResponse.getPlayersBoard().size()).isEqualTo(2);
        assertThat(expectedGameResponse.getWinner()).isEqualTo(actualGame.getWinner().name());
        assertThat(expectedGameResponse.getPlayerTurn()).isEqualTo(actualGame.getPlayerTurn().name());;
    }

    @Test
    public void shouldNotMapGameWhenSourceIsNull() {
        final var expectedGameResponse = GameMapper.INSTANCE.mapFrom(null);

        assertThat(expectedGameResponse).isNull();
    }
}