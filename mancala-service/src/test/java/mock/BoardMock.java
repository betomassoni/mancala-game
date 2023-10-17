package mock;

import br.com.robertomassoni.mancala.core.domain.Board;
import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardMock {

    public static Board createBoard(final Player player) {
        return new Board()
                .withPlayer(player)
                .withBigPit(PitMock.createBigPit())
                .withSmallPits(PitMock.createSmallPits());
    }
}
