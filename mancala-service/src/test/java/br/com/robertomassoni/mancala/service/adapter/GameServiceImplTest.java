package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.exception.DuplicatePlayerMoveException;
import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import mock.BoardMock;
import mock.GameMock;
import mock.SowPitMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        var actualGame = GameMock.createNewGame();
        when(persistence.save(any())).thenReturn(actualGame);

        service.createGame();

        Mockito.verify(persistence, only()).save(any());
    }

    @Test
    public void shouldCreateNewGameWith6PitsAnd6SeedsEachForPlayer1() {
        var actualGame = GameMock.createNewGame();
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().size()).isEqualTo(6);
        expectedGame.getBoard(Player.PLAYER_1).getSmallPits().forEach(pit -> {
            assertThat(pit.getSeedCount()).isEqualTo(6);
        });
    }

    @Test
    public void shouldCreateNewGameWith6PitsAnd6SeedsEachForPlayer2() {
        var actualGame = GameMock.createNewGame();
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().size()).isEqualTo(6);
        expectedGame.getBoard(Player.PLAYER_2).getSmallPits().forEach(pit -> {
            assertThat(pit.getSeedCount()).isEqualTo(6);
        });
    }

    @Test
    public void shouldCreateNewGameWith0SeedsInBigPitForPlayer1() {
        var actualGame = GameMock.createNewGame();
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isZero();
    }

    @Test
    public void shouldCreateNewGameWith0SeedsInBigPitForPlayer2() {
        var actualGame = GameMock.createNewGame();
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.createGame();

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getBigPit().getSeedCount()).isZero();
    }

    @Test
    public void shouldCallRepositoryTwiceWhenIsSowed() {
        var actualGame = GameMock.createNewGame();
        var actualSowGame = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(1);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        service.sow(actualSowGame);

        Mockito.verify(persistence, times(1)).findById(any());
        Mockito.verify(persistence, times(1)).save(any());
    }

    @Test
    public void shouldCollect1SeedsOnBitPit() {
        var actualGame = GameMock.createNewGame();
        var actualSowGame = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(1);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowGame);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(1);
    }

    @Test
    public void shouldCollectTwoSeedsOnBigPit() {
        var actualGame = GameMock.createNewGame();
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        service.sow(SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(2));
        service.sow(SowPitMock.create().withPlayer(Player.PLAYER_2).withPitIndex(2));
        var expectedGameAfterThirdPlay = service.sow(SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(3));

        assertThat(expectedGameAfterThirdPlay.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(2);
    }

    @Test
    public void shouldRemoveAllSeedsFromSmallPitAfterPlay() {
        var actualGame = GameMock.createNewGame();
        var actualSowPit = SowPitMock.create();
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        service.sow(actualSowPit.withPitIndex(2).withPlayer(Player.PLAYER_1));
        var expectedGame = service.sow(actualSowPit.withPitIndex(3).withPlayer(Player.PLAYER_2));

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(2).getSeedCount()).isZero();
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyOnTheSixthPitOnPlayer1() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 5),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 0)));
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6)));
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));
        var actualSowPit = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(1);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowPit);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(0).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyOnTheSixthPitOnPlayer2() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6)));
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 5),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 0)));
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2))
                .withPlayerTurn(Player.PLAYER_2);
        var actualSowPit = SowPitMock.create().withPlayer(Player.PLAYER_2).withPitIndex(1);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowPit);

        assertThat(expectedGame.getBoard(Player.PLAYER_2).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(0).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldCollectAllSeedsFromTheOpponentPitWhenTheLastSeedIsInEmptyPitOnTheFifthPit() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 1),
                new Pit(5, 0),
                new Pit(6, 6)));
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6)));
        var actualGame = GameMock.createNewGame()
                .withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));
        var actualSowPit = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(4);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowPit);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(7);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(1).getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldSow15SeedsAcrossTheBoardBothPlayers() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 15)));
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 0)));
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));
        var actualSowPit = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(6);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowPit);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(2);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(5).getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(0).getSeedCount()).isEqualTo(2);
    }

    @Test
    public void shouldNotScoreWhenSowTheLastSeedOnOpponentEmptyPit() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 6),
                new Pit(6, 3)));
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 0),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 0)));
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));
        var actualSowPit = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(6);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowPit);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_2).getSmallPits().get(1).getSeedCount()).isEqualTo(1);
    }


    @Test
    public void shouldSowOneSeedOnFourthPitAndNotScore() {
        var actualBoardPlayer1 = BoardMock.create(Player.PLAYER_1).withSmallPits(Arrays.asList(new Pit(1, 3),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 0)));
        var actualBoardPlayer2 = BoardMock.create(Player.PLAYER_2).withSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 0),
                new Pit(3, 0),
                new Pit(4, 0),
                new Pit(5, 0),
                new Pit(6, 0)));
        var actualGame = GameMock.createNewGame().withPlayersBoard(Arrays.asList(actualBoardPlayer1, actualBoardPlayer2));
        var actualSowPit = SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(1);
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        var expectedGame = service.sow(actualSowPit);

        assertThat(expectedGame.getBoard(Player.PLAYER_1).getSmallPits().get(3).getSeedCount()).isEqualTo(1);
        assertThat(expectedGame.getBoard(Player.PLAYER_1).getBigPit().getSeedCount()).isEqualTo(0);
    }

    @Test
    public void shouldThrowExceptionWhenPlayer1PlaysTwice() {
        var actualGame = GameMock.createNewGame();
        when(persistence.findById(any())).thenReturn(actualGame);
        when(persistence.save(any())).thenReturn(actualGame);

        assertThatThrownBy(() -> {
            service.sow(SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(2));
            service.sow(SowPitMock.create().withPlayer(Player.PLAYER_1).withPitIndex(3));
        }).isInstanceOf(DuplicatePlayerMoveException.class)
                .hasMessage("The same player cannot play again");
    }
}