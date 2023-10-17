package br.com.robertomassoni.mancala.rest.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardResponse {
    private List<PitResponse> smallPits;
    private PitResponse bigPit;
    private String player;
}
