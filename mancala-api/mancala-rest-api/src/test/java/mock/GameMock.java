package mock;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.domain.enums.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameMock {

    public static Game createNewGame() {
        var game = new Game();
        game.setId(UUID.randomUUID());
        game.setPlayersBoard(Arrays.asList(BoardMock.create(Player.PLAYER_1),
                BoardMock.create(Player.PLAYER_2)));
        game.setPlayerTurn(Player.PLAYER_1);
        game.setWinner(Player.PLAYER_1);
        game.setStatus(Status.IN_PROGRESS);
        return game;
    }
}
