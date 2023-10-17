package br.com.robertomassoni.mancala.rest.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SowPitRequest {
    @NotEmpty
    private String gameId;
    @NotEmpty
    private String player;
    @NotEmpty
    private Integer pitIndex;
}
