package mock;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.rest.api.request.SowPitRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SowPitRequestMock {

    public static SowPitRequest create() {
        return new SowPitRequest()
                .withPitIndex(1)
                .withGameId(UUID.randomUUID().toString())
                .withPlayer(Player.PLAYER_1.name());
    }
}
