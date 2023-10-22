package mock.domain;

import br.com.robertomassoni.mancala.core.domain.Board;
import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardMock {

    public static Board create(final Player player) {
        return new Board()
                .withPlayer(player)
                .withBigPit(PitMock.createBigPit())
                .withSmallPits(PitMock.createSmallPits());
    }

    public static Board create(final Player player, int pit1, int pit2, int pit3, int pit4, int pit5, int pit6, int bigPit) {
        return  new Board()
                .withPlayer(player)
                .withBigPit(new Pit(0, bigPit))
                .withSmallPits(createSmallPits(pit1, pit2, pit3, pit4, pit5, pit6));
    }

    private static List<Pit> createSmallPits(int pit1, int pit2, int pit3, int pit4, int pit5, int pit6) {
        return Arrays.asList(new Pit(1, pit1),
                new Pit(2, pit2),
                new Pit(3, pit3),
                new Pit(4, pit4),
                new Pit(5, pit5),
                new Pit(6, pit6));
    }
}
