package br.com.robertomassoni.mancala.service.factory;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.service.engine.MancalaGameEngine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameFactoryTest {

    @Test
    public void shouldCreateNewGameWith6PitsAnd6SeedsEachForPlayer1() {
        var expectedGame = MancalaGameEngine.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().size()).isEqualTo(6);
        expectedGame.getBoard(Player.PLAYER_1).getSmallPits().forEach(pit -> {
            assertThat(pit.getSeedCount()).isEqualTo(6);
        });
    }

    @Test
    public void shouldCreateNewGameWith6PitsAnd6SeedsEachForPlayer2() {
        var expectedGame = MancalaGameEngine.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().size()).isEqualTo(6);
        expectedGame.getBoard(Player.PLAYER_2).getSmallPits().forEach(pit -> {
            assertThat(pit.getSeedCount()).isEqualTo(6);
        });
    }

    @Test
    public void shouldCreateNewGameWith0SeedsInBigPitForPlayer1() {
        var expectedGame = MancalaGameEngine.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isZero();
    }

    @Test
    public void shouldCreateNewGameWith0SeedsInBigPitForPlayer2() {
        var expectedGame = MancalaGameEngine.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getBigPit().getSeedCount()).isZero();
    }
}