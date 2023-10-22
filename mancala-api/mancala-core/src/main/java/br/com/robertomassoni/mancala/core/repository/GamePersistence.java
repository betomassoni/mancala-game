package br.com.robertomassoni.mancala.core.repository;

import br.com.robertomassoni.mancala.core.domain.Game;

import java.util.UUID;

public interface GamePersistence {
    Game save(Game game);

    Game findById(UUID id);
}
