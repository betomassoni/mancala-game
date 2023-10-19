package mock;

import br.com.robertomassoni.mancala.core.domain.Pit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PitMock {

    public static List<Pit> createSmallPits() {
        return Arrays.asList(new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6));
    }

    public static Pit createBigPit() {
        return new Pit(0, 0);
    }
}
