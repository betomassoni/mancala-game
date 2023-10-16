package br.com.robertomassoni.mancala.core.domain;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Board {
    private List<Pit> smallPits;
    private Pit bigPit;
    private Player player;

    public Pit getSmallPitById(Integer pitId) throws Exception {
        return smallPits.stream()
                .filter(pit -> pit.getId().equals(pitId))
                .findFirst()
                .orElseThrow(() -> new Exception("Small Pit not found"));
    }
}
