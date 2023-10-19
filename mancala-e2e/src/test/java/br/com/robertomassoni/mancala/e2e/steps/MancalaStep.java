package br.com.robertomassoni.mancala.e2e.steps;

import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.exception.handler.ExceptionMessage;
import br.com.robertomassoni.mancala.rest.api.request.SowPitRequest;
import br.com.robertomassoni.mancala.rest.api.response.GameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class MancalaStep {

    @Autowired
    public ObjectMapper objectMapper;

    private Response response;
    private GameResponse gameResponse;
    private Integer httpCode;

    @ParameterType(".*")
    public Player player(String player) {
        return Player.valueOf(player);
    }

    @SneakyThrows
    @When("Make a POST request to create a new game")
    public void quando_faco_um_post_para_criar_um_novo_jogo() {
        response = RestAssured.given()
                .post("http://localhost:8080/api/v1/mancala");

        httpCode = response.getStatusCode();
        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            gameResponse = objectMapper.readValue(response.getBody().asString(), GameResponse.class);
        }
    }

    @Then("the status code should be {int}")
    public void entao_o_status_code_deve_ser(int httpStatusCode) {
        assertThat(response.getStatusCode()).isEqualTo(httpStatusCode);
    }

    @Then("the game id should be returned")
    public void entao_o_status_code_deve_serbo() {
        assertThat(gameResponse.getId()).isNotEmpty();
    }

    @Then("all players should have {int} seeds each small pit")
    public void allSmallPitsShouldHaveSeeds(int seedsCount) {
        gameResponse.getPlayersBoard().forEach(board -> {
            board.getSmallPits().forEach(pit -> {
                assertThat(pit.getSeedCount()).isEqualTo(seedsCount);
            });
        });
    }

    @Then("all players should have {int} seeds in big pit")
    public void allPlayersShouldHaveSeedsInBigPit(int seedsCount) {
        gameResponse.getPlayersBoard().forEach(board -> 
                assertThat(board.getBigPit().getSeedCount()).isEqualTo(seedsCount));
    }

    @Then("{player} score {int} seed(s) in his big pit")
    public void iScoreSeedsInMyBigPit(Player player, int seedsCount) {
        var boardResponse = gameResponse.getPlayersBoard().stream()
                .filter(board -> board.getPlayer().equals(player.name()))
                .findFirst().orElse(null);

        assertThat(boardResponse.getBigPit().getSeedCount()).isEqualTo(seedsCount);
    }

    @And("{player} play again")
    public void playerPlayAgain(Player player) {
        assertThat(gameResponse.getPlayerTurn()).isEqualTo(player.name());
    }

    @SneakyThrows
    @Then("the {player} sow his small pit number {int}")
    public void playerSowSmallPitNumber(Player player, int pitIndex) {
        makeMove(gameResponse.getId(), player, pitIndex);
    }

    @Then("a client error should occur")
    public void aClientErrorShouldOccur() {
        assertThat(HttpStatus.valueOf(httpCode).is4xxClientError()).isTrue();
    }

    @SneakyThrows
    private void makeMove(String gameId, Player player, int pitIndex) {
        var move = new SowPitRequest()
                .withGameId(gameId)
                .withPlayer(player.name())
                .withPitIndex(pitIndex);
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsString(move))
                .put("http://localhost:8080/api/v1/mancala");
        httpCode = response.getStatusCode();
        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            gameResponse = objectMapper.readValue(response.getBody().asString(), GameResponse.class);
        }
    }
}
