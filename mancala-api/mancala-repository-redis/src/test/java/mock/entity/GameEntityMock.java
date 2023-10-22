package mock.entity;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.domain.enums.Status;
import br.com.robertomassoni.mancala.repository.redis.entity.GameEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameEntityMock {

    public static GameEntity createNewGame() {
        var game = new GameEntity();
        game.setId(UUID.randomUUID());
        game.setPlayersBoard(Arrays.asList(BoardEntityMock.create(Player.PLAYER_1),
                BoardEntityMock.create(Player.PLAYER_2)));
        game.setPlayerTurn(Player.PLAYER_1.name());
        game.setWinner(Player.PLAYER_1.name());
        game.setStatus(Status.IN_PROGRESS.name());
        return game;
    }
}
