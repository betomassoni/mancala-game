package br.com.robertomassoni.mancala.core.service;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.enums.Player;

public interface GameService {
    Game createNew();

    Game play(Game game, Player player, Integer pitId, Integer opponentSeeds);
}
