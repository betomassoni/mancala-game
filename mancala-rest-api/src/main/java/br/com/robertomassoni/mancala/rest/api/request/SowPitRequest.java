package br.com.robertomassoni.mancala.rest.api.request;

import br.com.robertomassoni.mancala.core.annotation.ValueOfEnum;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SowPitRequest {
    @NotEmpty
    private String gameId;
    @NotEmpty
    @ValueOfEnum(enumClass = Player.class, message = "Must be a valid player (PLAYER_1 or PLAYER_2)")
    private String player;
    @NotNull
    private Integer pitIndex;
}
