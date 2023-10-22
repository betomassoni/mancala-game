package br.com.robertomassoni.mancala.rest.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameResponse {
    private String id;
    private List<BoardResponse> playersBoard;
    private String playerTurn;
    private String winner;
    private String status;
}
