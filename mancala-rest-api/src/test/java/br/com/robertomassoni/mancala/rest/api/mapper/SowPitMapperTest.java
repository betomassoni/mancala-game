package br.com.robertomassoni.mancala.rest.api.mapper;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import mock.SowPitRequestMock;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SowPitMapperTest {

    @Test
    public void shouldMapSowPitSuccess() {
        final var actualSowPitRequest = SowPitRequestMock.create();

        final var expectedSowPit = SowPitMapper.INSTANCE.mapFrom(actualSowPitRequest);

        assertThat(expectedSowPit.getPitIndex()).isEqualTo(actualSowPitRequest.getPitIndex());
        assertThat(expectedSowPit.getPlayer()).isEqualTo(Player.valueOf(actualSowPitRequest.getPlayer()));
        assertThat(expectedSowPit.getGameId().toString()).isEqualTo(actualSowPitRequest.getGameId());
    }

    @Test
    public void shouldNotMapSowPitWhenSourceIsNull() {
        final var expectedSowPit = SowPitMapper.INSTANCE.mapFrom(null);

        assertThat(expectedSowPit).isNull();
    }
}