package br.com.robertomassoni.mancala.rest.api.response;

import br.com.robertomassoni.mancala.core.domain.Pit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResponse {
    private List<Pit> smallPits;
    private Pit bigPit;
    private String player;
}
