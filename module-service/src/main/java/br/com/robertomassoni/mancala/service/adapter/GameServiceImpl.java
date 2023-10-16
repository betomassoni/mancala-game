package br.com.robertomassoni.mancala.service.adapter;

import br.com.robertomassoni.mancala.service.factory.GameFactory;
import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.service.GameService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameServiceImpl implements GameService {

    @Override
    public Game createNew() {
        return GameFactory.createGame();
    }

    @SneakyThrows
    @Override
    public Game play(Game game, Player player, Integer pitId, Integer opponentSeeds) {
        var opponentPlayer = (player == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
        var opponentPlayerBoard = game.getPlayersBoard().get(opponentPlayer);
        var currentPlayerBoard = game.getPlayersBoard().get(player);

        var chosenPit = currentPlayerBoard.getSmallPitById(pitId);

        AtomicInteger seedsToSow; // = new AtomicInteger(chosenPit.getSeedCount());
        if (opponentSeeds > 0) {
            seedsToSow = new AtomicInteger(opponentSeeds);
        } else {
            seedsToSow = new AtomicInteger(chosenPit.getSeedCount());
            chosenPit.removeAllSeeds();
        }

        var pitsToSow = currentPlayerBoard.getSmallPits().stream()
                .filter(pit -> pit.getId() > pitId && opponentSeeds == 0 || pit.getId() >= pitId && opponentSeeds > 0)
                .toList();

        // semeia os buracos
        pitsToSow.stream()
                .sorted(Comparator.comparingInt(Pit::getId))
                .forEach(pit -> {
                    if (seedsToSow.get() > 1
                            || seedsToSow.get() == 1 && pit.getSeedCount() > 0
                            || seedsToSow.get() == 1 && pit.getSeedCount() == 0 && !game.getPlayerTurn().equals(currentPlayerBoard.getPlayer())) {
                        seedsToSow.getAndDecrement();
                        pit.addOneSeed();
                    } else if (seedsToSow.get() == 1 && pit.getSeedCount() == 0) {
                        // se a ultima semente cair no buraco vazio, pontua
                        var pitOppositeOpponent = opponentPlayerBoard.getSmallPits().stream()
                                .filter(p -> p.getId().equals(p.getId()))
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
            play(game, opponentPlayer, 1, seedsToSow.get());
        }

        return game;
    }
}
