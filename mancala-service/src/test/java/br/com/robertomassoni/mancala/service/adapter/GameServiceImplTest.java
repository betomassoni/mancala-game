package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.core.domain.Board;
import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import mock.BoardMock;
import mock.GameMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GameServiceImplTest {
    private GameServiceImpl service;

    @BeforeEach
    public void setUp() {
        service = new GameServiceImpl();
    }

    private Board getPlayerBoard(List<Board> playersBoard, Player player) {
        return playersBoard.stream().filter(board -> board.getPlayer().equals(player)).findFirst().orElse(null);
    }

    @Test
    public void shouldCollectOneSeedOnBigPit() {
        var actualGame = GameMock.createNewGame();

        var expectedGame = service.sow(actualGame, Player.PLAYER_1, 1, 0);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(1);
    }

    @Test
    public void shouldCollectTwoSeedsOnBigPit() {
        var actualGame = GameMock.createNewGame();

        var expectedGameAfterFirstPlay = service.sow(actualGame, Player.PLAYER_1, 1, 0);
        var expectedGameAfterSecondPlay = service.sow(expectedGameAfterFirstPlay, Player.PLAYER_1, 2, 0);

        assertThat(expectedGameAfterSecondPlay.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(2);
    }

    @Test
    public void shouldRemoveAllSeedsFromSmallPitAfterPlay() {
        var actualGame = GameMock.createNewGame();

        var expectedGameAfterFirstPlay = service.sow(actualGame, Player.PLAYER_1, 1, 0);
        var expectedGameAfterSecondPlay = service.sow(expectedGameAfterFirstPlay, Player.PLAYER_1, 2, 0);

        assertThat(expectedGameAfterSecondPlay.getBoard(Player.PLAYER_1).getSmallPits().get(0).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyPit() {
        var actualBoardPlayer1 = BoardMock.createBoard(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 5),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 0)));
        var actualBoardPlayer2 = BoardMock.createBoard(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6)));
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        var expectedGame = service.sow(actualGame, Player.PLAYER_1, 1, 0);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(0).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldSow15SeedsAcrossTheBoardBothPlayers() {
        var actualBoardPlayer1 = BoardMock.createBoard(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 15)));
        var actualBoardPlayer2 = BoardMock.createBoard(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 0)));
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        var expectedGame = service.sow(actualGame, Player.PLAYER_1, 6, 0);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(2);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(5).getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(0).getSeedCount()).isEqualTo(2);
    }

    @Test
    public void shouldNotScoreWhenSowTheLastSeedOnOpponentEmptyPit() {
        var actualBoardPlayer1 = BoardMock.createBoard(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 6),
                new Pit(6, 3)));
        var actualBoardPlayer2 = BoardMock.createBoard(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 0)));
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));

        var expectedGame = service.sow(actualGame, Player.PLAYER_1, 6, 0);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(1).getSeedCount()).isEqualTo(1);
    }
}