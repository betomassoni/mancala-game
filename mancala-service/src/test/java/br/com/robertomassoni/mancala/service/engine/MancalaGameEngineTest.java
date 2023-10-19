package br.com.robertomassoni.mancala.service.engine;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.domain.enums.Status;
import br.com.robertomassoni.mancala.core.exception.InvalidMoveException;
import br.com.robertomassoni.mancala.core.exception.PlayerCannotPlayException;
import mock.BoardMock;
import mock.GameMock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MancalaGameEngineTest {

    @Test
    public void shouldCollect1SeedsOnBitPit() {
        var actualGame = GameMock.createNewGame();

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 1);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(1);
    }

    @Test
    public void shouldCollectTwoSeedsOnBigPit() {
        var actualGame = GameMock.createNewGame();

        final var expectedGameAfterMove1 = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 2);
        final var expectedGameAfterMove2 = MancalaGameEngine.play(expectedGameAfterMove1, Player.PLAYER_2, 2);
        final var expectedGameAfterMove3 = MancalaGameEngine.play(expectedGameAfterMove2, Player.PLAYER_1, 3);

        assertThat(expectedGameAfterMove3.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(2);
    }

    @Test
    public void shouldRemoveAllSeedsFromSmallPitAfterPlay() {
        var actualGame = GameMock.createNewGame();

        final var expectedGameAfterMove1 = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 2);
        final var expectedGameAfterMove2 = MancalaGameEngine.play(expectedGameAfterMove1, Player.PLAYER_2, 3);

        assertThat(expectedGameAfterMove2.getBoard(Player.PLAYER_2).getSmallPits().get(2).getSeedCount()).isZero();
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyOnTheSixthPitOnPlayer1() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 5, 6, 6, 6, 6, 0, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 6, 6, 6, 6, 6, 6, 0);
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 1);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(0).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyOnTheSixthPitOnPlayer2() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 6, 6, 6, 6, 6, 6, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 5, 6, 6, 6, 6, 0, 0);
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2))
                .withPlayerTurn(Player.PLAYER_2);

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_2, 1);

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(0).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyPitOnTheFifthPit() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 6, 6, 6, 1, 0, 6, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 6, 6, 6, 6, 6, 6, 0);
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 4);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(1).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldSow15SeedsAcrossTheBoardBothPlayers() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 0, 0, 0, 0, 0, 15, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 0, 0, 0, 0, 0, 1, 0);
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 6);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(2);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(5).getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(0).getSeedCount()).isEqualTo(2);
    }

    @Test
    public void shouldNotScoreWhenSowTheLastSeedOnOpponentEmptyPit() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 0, 0, 0, 0, 6, 3, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 0, 0, 0, 0, 0, 1, 0);
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 6);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(1).getSeedCount()).isEqualTo(1);
    }


    @Test
    public void shouldSowOneSeedOnFourthPitAndNotScore() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 3, 0, 0, 0, 0, 0, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 6, 0, 0, 0, 0, 6, 0);
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 1);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(3).getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldThrowExceptionWhenPlayer1PlaysTwice() {
        var actualGame = GameMock.createNewGame();

        assertThatThrownBy(() -> {
            final var expectedGameAfterMove1 = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 2);
            MancalaGameEngine.play(expectedGameAfterMove1, Player.PLAYER_1, 3);
        }).isInstanceOf(PlayerCannotPlayException.class)
                .hasMessage("This player cannot play this round");
    }

    @Test
    public void shouldThrowExceptionWhenPlayerTryToSowWithoutSeeds() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 0, 0, 0, 0, 0, 6, 0);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 0, 0, 0, 0, 0, 6, 0);
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        assertThatThrownBy(() -> {
            MancalaGameEngine.play(actualGame, Player.PLAYER_1, 1);
        }).isInstanceOf(InvalidMoveException.class)
                .hasMessage("Invalid move because there is no seeds in this pit");
    }

    @Test
    public void shouldSowOneSeedOnFourthPitAndNotScoreXXXXX() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 0, 0, 0, 0, 0, 1, 10);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 0, 0, 0, 0, 0, 6, 10);
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        final var expectedGame = MancalaGameEngine.play(actualGame, Player.PLAYER_1, 6);

        assertThat(expectedGame.getWinner()).isEqualTo(Player.PLAYER_2);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getBigPit().getSeedCount()).isEqualTo(16);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(11);
    }

    @Test
    public void shouldThrowExceptionWhenPlayerTryToSowAndGameIsAlreadyFinished() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1, 0, 0, 0, 0, 1, 0, 10);
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2, 0, 0, 0, 0, 0, 6, 10);
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2))
                .withStatus(Status.FINISHED);

        assertThatThrownBy(() -> {
            MancalaGameEngine.play(actualGame, Player.PLAYER_1, 5);
        }).isInstanceOf(InvalidMoveException.class)
                .hasMessage("Invalid move because this game is already finished");
    }
}