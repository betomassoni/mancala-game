package br.com.robertomassoni.mancala.rest.api.controller;

import br.com.robertomassoni.mancala.core.service.GameService;
import br.com.robertomassoni.mancala.rest.api.mapper.GameMapper;
import br.com.robertomassoni.mancala.rest.api.mapper.SowPitMapper;
import br.com.robertomassoni.mancala.rest.api.request.SowPitRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/mancala")
public class GameController {

    private GameService service;

    @PostMapping
    public ResponseEntity<?> createGame() {
        final var result = service.createGame();
        return ResponseEntity.status(CREATED).body(GameMapper.INSTANCE.mapFrom(result));
    }

    @PutMapping
    public ResponseEntity<?> sow(@RequestBody final SowPitRequest request) {
        final var sow = SowPitMapper.INSTANCE.mapFrom(request);
        final var result = service.sow(sow);
        return ResponseEntity.status(OK).body(GameMapper.INSTANCE.mapFrom(result));
    }
}
