package mock;

import br.com.robertomassoni.mancala.core.domain.Board;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardMock {

    public static Board create(final Player player) {
        return new Board()
                .withPlayer(player)
                .withBigPit(PitMock.createBigPit())
                .withSmallPits(PitMock.createSmallPits());
    }
}
