package br.com.robertomassoni.mancala.service.factory;

import br.com.robertomassoni.mancala.core.domain.Board;
import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameFactory {
    public static Game createGame() {
        var game = new Game();
        game.setId(UUID.randomUUID());
        game.setPlayersBoard(createPlayersBoard());
        game.setPlayerTurn(Player.PLAYER_1);
        return game;
    }

    private static HashMap<Player, Board> createPlayersBoard() {
        var boardPlayer1 = createBoard(Player.PLAYER_1);
        var boardPlayer2 = createBoard(Player.PLAYER_2);

        var playersBoard = new HashMap<Player, Board>();
        playersBoard.put(boardPlayer1.getPlayer(), boardPlayer1);
        playersBoard.put(boardPlayer2.getPlayer(), boardPlayer2);
        return playersBoard;
    }

    private static Board createBoard(final Player player) {
        var board = new Board();
        //board.setId(2);
        board.setPlayer(player);
        board.setBigPit(new Pit(0, 0));
        board.setSmallPits(Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6)));
        return board;
    }
}
