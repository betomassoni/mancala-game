package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.SowPit;
import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import br.com.robertomassoni.mancala.core.service.GameService;
import br.com.robertomassoni.mancala.service.engine.MancalaGameEngine;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GamePersistence persistence;

    @Override
    public Game createGame() {
        return persistence.save(MancalaGameEngine.createGame());
    }

    @Override
    public Game sow(final SowPit sowPit) {
        try {
            final Game game = persistence.findById(sowPit.getGameId());
            final var result = MancalaGameEngine.play(game, sowPit.getPlayer(), sowPit.getPitIndex());
            return persistence.save(result);
        } catch (Exception e) {
            log.error(String.format("Error sowing on pit number %s of game %s by %s", sowPit.getPitIndex(),
                    sowPit.getGameId(),
                    sowPit.getPlayer().name()), e);
            throw e;
        }
    }
}
