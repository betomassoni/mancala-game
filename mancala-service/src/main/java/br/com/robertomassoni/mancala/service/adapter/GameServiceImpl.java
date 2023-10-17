package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.SowPit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.exception.PlayerNotFoundException;
import br.com.robertomassoni.mancala.core.repository.GamePersistence;
import br.com.robertomassoni.mancala.core.service.GameService;
import br.com.robertomassoni.mancala.service.factory.GameFactory;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

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
        final var result = sow(game, sowPit.getPlayer(), sowPit.getPitIndex(), 0);
        return persistence.save(result);
    }

    @SneakyThrows
    public Game sow(final Game game, final Player player, final Integer pitId, final Integer opponentSeeds) {
        var opponentPlayer = (player == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
        var opponentPlayerBoard = game.getPlayersBoard().stream()
                .filter(p -> p.getPlayer().equals(opponentPlayer)).findFirst().orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        var currentPlayerBoard = game.getPlayersBoard().stream()
                .filter(p -> p.getPlayer().equals(player)).findFirst().orElseThrow(() -> new PlayerNotFoundException("Player not found"));

        var chosenPit = currentPlayerBoard.getSmallPitById(pitId);

        AtomicInteger seedsToSow; // = new AtomicInteger(chosenPit.getSeedCount());
        if (opponentSeeds > 0) {
            seedsToSow = new AtomicInteger(opponentSeeds);
        } else {
            seedsToSow = new AtomicInteger(chosenPit.getSeedCount());
            chosenPit.removeAllSeeds();
        }

        var pitsToSow = currentPlayerBoard.getSmallPits().stream()
                .filter(pit -> pit.getIndex() > pitId && opponentSeeds == 0 || pit.getIndex() >= pitId && opponentSeeds > 0)
                .toList();

        // semeia os buracos
        pitsToSow.stream()
                .sorted(Comparator.comparingInt(Pit::getIndex))
                .forEach(pit -> {
                    if (seedsToSow.get() > 1
                            || seedsToSow.get() == 1 && pit.getSeedCount() > 0
                            || seedsToSow.get() == 1 && pit.getSeedCount() == 0 && !game.getPlayerTurn().equals(currentPlayerBoard.getPlayer())) {
                        seedsToSow.getAndDecrement();
                        pit.addOneSeed();
                    } else if (seedsToSow.get() == 1 && pit.getSeedCount() == 0) {
                        // se a ultima semente cair no buraco vazio, pontua
                        var pitOppositeOpponent = opponentPlayerBoard.getSmallPits().stream()
                                .filter(p -> p.getIndex().equals(p.getIndex()))
                                .findFirst().orElseThrow(() -> new RuntimeException());
                        currentPlayerBoard.getBigPit().addSeed(pitOppositeOpponent.getSeedCount());
                        currentPlayerBoard.getBigPit().addOneSeed();
                        pitOppositeOpponent.removeAllSeeds();
                        seedsToSow.getAndDecrement();
                    }
                });

        // semeia o buraco maior
        if (game.getPlayerTurn().equals(currentPlayerBoard.getPlayer())) {
            if (seedsToSow.get() > 0) {
                currentPlayerBoard.getBigPit().addOneSeed();
                seedsToSow.getAndDecrement();
            }
        }

        // Semeia do proximo jogador recursivamente
        if (seedsToSow.get() > 0) {
            sow(game, opponentPlayer, 1, seedsToSow.get());
        }

        return game;
    }
}
