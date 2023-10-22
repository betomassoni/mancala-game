package mock.entity;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.repository.redis.entity.BoardEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardEntityMock {

    public static BoardEntity create(final Player player) {
        return new BoardEntity()
                .withPlayer("PLAYER_1")
                .withBigPit(PitEntityMock.createBigPit())
                .withSmallPits(PitEntityMock.createSmallPits());
    }
}
