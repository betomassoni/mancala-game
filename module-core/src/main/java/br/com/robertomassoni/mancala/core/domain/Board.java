package br.com.robertomassoni.mancala.core.domain;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.util.List;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private List<Pit> smallPits;
    private Pit bigPit;
    private Player player;

    public Pit getSmallPitById(Integer pitId) throws Exception {
        return smallPits.stream()
                .filter(pit -> pit.getIndex().equals(pitId))
                .findFirst()
                .orElseThrow(() -> new Exception("Small Pit not found"));
    }
}
