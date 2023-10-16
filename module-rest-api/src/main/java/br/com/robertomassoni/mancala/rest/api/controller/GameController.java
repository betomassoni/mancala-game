package br.com.robertomassoni.mancala.rest.api.controller;

import br.com.robertomassoni.mancala.core.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/mancala")
public class GameController {

    private GameService service;

    @PostMapping
    public ResponseEntity<?> create() {
        final var result = service.createNew();
        return ResponseEntity.status(CREATED).body(result);
    }
}
