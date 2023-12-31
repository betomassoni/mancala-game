package br.com.robertomassoni.mancala.core.service;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.SowPit;

public interface GameService {
    Game createGame();

    Game sow(final SowPit sowPit);
}
