package br.com.robertomassoni.mancala.core.domain;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Game {
    private UUID id;
    private Map<Player, Board> playersBoard;
    private Player playerTurn;
}
