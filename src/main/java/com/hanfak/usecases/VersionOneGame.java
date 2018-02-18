package com.hanfak.usecases;

import com.hanfak.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hanfak.domain.Player.player;
import static com.hanfak.domain.PlayerResult.playerResult;
import static java.util.stream.IntStream.range;

public class VersionOneGame {
    private final Deck deck;
    private final HandEvaluator handEvaluator;
    private final MultipleHandEvaluator multipleHandEvaluator;

    public VersionOneGame(Deck deck, HandEvaluator handEvaluator, MultipleHandEvaluator multipleHandEvaluator) {
        this.deck = deck;
        this.handEvaluator = handEvaluator;
        this.multipleHandEvaluator = multipleHandEvaluator;
    }

    public List<PlayerResult> playGame(Player... players) {
        List<Player> playersWithAHandOfCards = dealHandToAllPlayers(players);
        List<Player> playersWithHandsScored = evaluatePlayersHandsOfCards(playersWithAHandOfCards);
        return evaluateGame(playersWithHandsScored);
    }

    private List<Player> dealHandToAllPlayers(Player[] players) {
        return Arrays.stream(players).map(this::dealHand).collect(Collectors.toList());
    }

    private Player dealHand(Player player) {
        Hand hand = deck.dealHand();
        return player(player.playerName, hand);
    }

    private List<Player> evaluatePlayersHandsOfCards(List<Player> players) {
        return players.stream().map(this::evaluateHand).collect(Collectors.toList());
    }

    private Player evaluateHand(Player player) {
        Hand hand = handEvaluator.scoreHand(player.hand);
        return player(player.playerName, hand);
    }

    //TODO Refactor
    private List<PlayerResult> evaluateGame(List<Player> players) {
        List<Player> playersInOrderOfBestHand = multipleHandEvaluator.compareAllPlayersHands(players);
        Player winner = playersInOrderOfBestHand.get(0);
        PlayerResult winnersPlayerResult = playerResult(winner.playerName, Result.WIN, winner.hand);
        List<PlayerResult> losersPlayerResults = range(1, players.size() - 1)
                .mapToObj(players::get)
                .map(loser -> playerResult(loser.playerName, Result.LOSS, loser.hand))
                .collect(Collectors.toList());

        return Stream.concat(Stream.of(winnersPlayerResult), losersPlayerResults.stream())
                .collect(Collectors.toList());
    }
}
