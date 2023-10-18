package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.SowPit;
import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import br.com.robertomassoni.mancala.core.service.GameService;
import br.com.robertomassoni.mancala.service.engine.MancalaGameEngine;
import br.com.robertomassoni.mancala.service.factory.GameFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GamePersistence persistence;

    @Override
    public Game createGame() {
        return persistence.save(GameFactory.createGame());
    }

    @Override
    public Game sow(final SowPit sowPit) {
        final var game = persistence.findById(sowPit.getGameId());
        final var result = MancalaGameEngine.play(game, sowPit.getPlayer(), sowPit.getPitIndex());
        return persistence.save(result);
    }
}
