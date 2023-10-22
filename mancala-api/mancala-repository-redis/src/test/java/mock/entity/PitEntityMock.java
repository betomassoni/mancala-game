package mock.entity;

import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.repository.redis.entity.PitEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PitEntityMock {

    public static List<PitEntity> createSmallPits() {
        return Arrays.asList(new PitEntity(1, 6),
                new PitEntity(2, 6),
                new PitEntity(3, 6),
                new PitEntity(4, 6),
                new PitEntity(5, 6),
                new PitEntity(6, 6));
    }

    public static PitEntity createBigPit() {
        return new PitEntity(0, 0);
    }
}
