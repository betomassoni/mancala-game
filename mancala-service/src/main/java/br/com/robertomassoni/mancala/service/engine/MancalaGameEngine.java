package br.com.robertomassoni.mancala.service.engine;

import br.com.robertomassoni.mancala.core.domain.Board;
import br.com.robertomassoni.mancala.core.domain.Game;
import br.com.robertomassoni.mancala.core.domain.Pit;
import br.com.robertomassoni.mancala.core.domain.enums.Player;
import br.com.robertomassoni.mancala.core.exception.InvalidMoveException;
import br.com.robertomassoni.mancala.core.exception.PlayerCannotPlayException;
import br.com.robertomassoni.mancala.core.exception.PlayerNotFoundException;
import lombok.SneakyThrows;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class MancalaGameEngine {

    private Game game;
    private Player player;
    private boolean shouldPlayAgain;

    private MancalaGameEngine(final Game game, final Player player) {
        this.game = game;
        this.player = player;
        this.shouldPlayAgain = false;
    }

    public static Game play(final Game game, Player player, final Integer pitIndex) {
        if (!game.getPlayerTurn().equals(player)) {
            throw new PlayerCannotPlayException("This player cannot play this round");
        }
        return new MancalaGameEngine(game, player).sow(player, pitIndex, 0);
    }

    @SneakyThrows
    private Game sow(final Player player, final Integer pitIndex, final Integer remainingSeeds) {
        final var currentPlayerBoard = findPlayerBoard(player);
        final var seedsToSow = new AtomicInteger(remainingSeeds > 0 ? remainingSeeds : currentPlayerBoard.getSmallPitById(pitIndex).getSeedCount());

        if (seedsToSow.get() == 0) {
            throw new InvalidMoveException("Invalid move because there is no seeds in this pit");
        }

        if (remainingSeeds == 0) {
            sowSmallPitsPlayer(currentPlayerBoard, seedsToSow, pitIndex);
        } else {
            sowSmallPitsOpponentPlayer(currentPlayerBoard, seedsToSow, pitIndex - 1);
        }

        if (isCurrentPlayerTurn(currentPlayerBoard) && isRemainingSeeds(seedsToSow)) {
            sowBigPit(currentPlayerBoard, seedsToSow);
        }

        // Recursive call
        if (isRemainingSeeds(seedsToSow)) {
            this.sow(getOpponentPlayer(player), 1, seedsToSow.get());
        }

        defineNextPlayer();
        checkIfGameIsOver();

        return game;
    }

    private void transferAllSeedsToBitPit(final Board board) {
        final var allSeedsInSmallPitCount = board.getSmallPits().stream().mapToInt(Pit::getSeedCount).sum();
        board.getBigPit().addSeed(allSeedsInSmallPitCount);
    }

    private static boolean isOutOfSeeds(Board board) {
        return board.getSmallPits().stream().allMatch(pit -> pit.getSeedCount() == 0);
    }

    private Player getOpponentPlayer(Player player) {
        return (player == Player.PLAYER_1) ? Player.PLAYER_2 : Player.PLAYER_1;
    }

    private Board findPlayerBoard(final Player player) {
        return game.getPlayersBoard().stream()
                .filter(p -> p.getPlayer().equals(player))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
    }

    private List<Pit> getPitsToSow(final Board currentPlayerBoard, final Integer pitIndex) {
        return currentPlayerBoard.getSmallPits().stream()
                .filter(pit -> pit.getIndex() > pitIndex)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private void sowSmallPitsPlayer(final Board currentPlayerBoard, final AtomicInteger seedsToSow, final Integer pitIndex) {
        currentPlayerBoard.getSmallPitById(pitIndex).removeAllSeeds();
        getPitsToSow(currentPlayerBoard, pitIndex).stream()
                .sorted(Comparator.comparingInt(Pit::getIndex))
                .forEach(pit -> {
                    if (seedsToSow.get() > 0) {
                        pit.addOneSeed();
                        seedsToSow.getAndDecrement();
                        if (canCaptureOpponentSeeds(pit)) {
                            scoreByCollectingOpponentSeeds(currentPlayerBoard, pit);
                        }
                    }
                });
    }

    private void sowSmallPitsOpponentPlayer(final Board currentPlayerBoard, final AtomicInteger seedsToSow, final Integer pitIndex) {
        final var pitsToSow = getPitsToSow(currentPlayerBoard, pitIndex);
        pitsToSow.stream().sorted(Comparator.comparingInt(Pit::getIndex)).forEach(pit -> {
            if (seedsToSow.get() >= 1) {
                pit.addOneSeed();
                seedsToSow.getAndDecrement();
            }
        });
    }

    private boolean canCaptureOpponentSeeds(final Pit currentPit) {
        return currentPit.getSeedCount() == 1 && getOppositePit(currentPit).getSeedCount() > 0;
    }

    private void scoreByCollectingOpponentSeeds(final Board currentPlayerBoard, final Pit currentPit) {
        final var oppositePit = getOppositePit(currentPit);
        currentPlayerBoard.getBigPit().addSeed(oppositePit.getSeedCount());
        currentPlayerBoard.getBigPit().addOneSeed();
        oppositePit.removeAllSeeds();
        currentPit.removeAllSeeds();
    }

    private Pit getOppositePit(final Pit pit) {
        final var opponentPlayerBoard = findPlayerBoard(getOpponentPlayer(this.player));
        final var pitsInReverseOrder = opponentPlayerBoard.getSmallPits().stream()
                .sorted((pit1, pit2) -> Integer.compare(pit2.getIndex(), pit1.getIndex()))
                .toList();
        return pitsInReverseOrder.get(pit.getIndex() - 1);
    }

    private boolean isCurrentPlayerTurn(final Board board) {
        return game.getPlayerTurn().equals(board.getPlayer());
    }

    private void sowBigPit(final Board currentPlayerBoard, AtomicInteger seedsToSow) {
        currentPlayerBoard.getBigPit().addOneSeed();
        if (isLastSeedToSow(seedsToSow)) {
            this.shouldPlayAgain = true;
        }
        seedsToSow.getAndDecrement();
    }

    private boolean isLastSeedToSow(AtomicInteger seedsToSow) {
        return seedsToSow.get() == 1;
    }

    private boolean isRemainingSeeds(AtomicInteger seedsToSow) {
        return seedsToSow.get() > 0;
    }

    private void defineNextPlayer() {
        if (this.shouldPlayAgain) {
            this.game.setPlayerTurn(this.player);
        } else {
            this.game.setPlayerTurn(getOpponentPlayer(this.player));
        }
    }

    private void checkIfGameIsOver() {
        var player1Board = findPlayerBoard(Player.PLAYER_1);
        var player2Board = findPlayerBoard(Player.PLAYER_2);
        finishGame(player1Board, player2Board);
        defineWinner(player1Board, player2Board);
    }

    private void finishGame(final Board player1Board, final Board player2Board) {
        if (isOutOfSeeds(player1Board)) {
            transferAllSeedsToBitPit(player2Board);
        } else if (isOutOfSeeds(player2Board)) {
            transferAllSeedsToBitPit(player1Board);
        }
    }

    private void defineWinner(Board player1Board, Board player2Board) {
        if (isOutOfSeeds(player1Board) || isOutOfSeeds(player2Board)) {
            if (player1Board.getBigPit().getSeedCount() > player2Board.getBigPit().getSeedCount()) {
                game.setWinner(Player.PLAYER_1);
            } else {
                game.setWinner(Player.PLAYER_2);
            }
        }
    }
}
