package mock;

import br.com.robertomassoni.mancala.core.domain.SowPit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SowPitMock {

    public static SowPit create() {
        var sowPit = new SowPit();
        sowPit.setPitIndex(1);
        sowPit.setGameId(UUID.randomUUID());
        sowPit.setPlayer(Player.PLAYER_1);
        return sowPit;
    }
}
