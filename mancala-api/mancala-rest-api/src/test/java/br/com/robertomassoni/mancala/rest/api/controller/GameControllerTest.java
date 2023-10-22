package br.com.robertomassoni.mancala.rest.api.controller;

import br.com.robertomassoni.mancala.core.service.GameService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import mock.GameMock;
import mock.SowPitRequestMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameControllerTest {

    private GameService service;
    private GameController controller;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        service = Mockito.mock(GameService.class);
        controller = new GameController(service);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void shouldCreateNewGameAndReturnHttpStatusCreated() {
        final var actualGame = GameMock.createNewGame();
        when(service.createGame()).thenReturn(actualGame);

        final var expectedResponse = controller.createGame();

        verify(service, only()).createGame();
        assertThat(expectedResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void shouldPlayAndReturnHttpStatusOk() {
        final var actualGame = GameMock.createNewGame();
        when(service.sow(any())).thenReturn(actualGame);

        final var expectedResponse = controller.sow(SowPitRequestMock.create());

        verify(service, only()).sow(any());
        assertThat(expectedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldBeInvalidSowWhenGameIdIsNull() {
        final var actualRequest = SowPitRequestMock.create().withGameId(null);

        final var expectedValidation = new ArrayList<>(validator.validate(actualRequest));

        assertThat(expectedValidation.get(0).getPropertyPath().toString()).isEqualTo("gameId");
    }

    @Test
    public void shouldBeInvalidSowWhenPlayerIsNull() {
        final var actualRequest = SowPitRequestMock.create().withPlayer(null);

        final var expectedValidation = new ArrayList<>(validator.validate(actualRequest));

        assertThat(expectedValidation.get(0).getPropertyPath().toString()).isEqualTo("player");
    }

    @Test
    public void shouldBeInvalidSowWhenPitIndexIsNull() {
        final var actualRequest = SowPitRequestMock.create().withPitIndex(null);

        final var expectedValidation = new ArrayList<>(validator.validate(actualRequest));

        assertThat(expectedValidation.get(0).getPropertyPath().toString()).isEqualTo("pitIndex");
    }
}