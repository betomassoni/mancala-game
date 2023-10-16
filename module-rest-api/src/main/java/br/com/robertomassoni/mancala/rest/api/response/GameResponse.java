package br.com.robertomassoni.mancala.rest.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GameResponse {
    private String id;
    private String playerTurn;
    private String nextPlayer;
    private Map<String, BoardResponse> playersBoard;
}
