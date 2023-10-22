package br.com.robertomassoni.mancala.core.domain;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private UUID id;
    private List<Board> playersBoard;
    private Player playerTurn;
    private Player winner;
    private Status status;

    public Board getBoard(final Player player) {
        return this.playersBoard.stream()
                .filter(board -> board.getPlayer().equals(player))
                .findFirst().orElse(null);
    }
}
