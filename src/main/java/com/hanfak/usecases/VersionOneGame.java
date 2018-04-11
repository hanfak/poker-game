package com.hanfak.usecases;

import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.deck.DealtCards;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.evaluators.HandEvaluator;
import com.hanfak.domain.game.evaluators.MultipleHandEvaluator;
import com.hanfak.domain.game.playershand.PokerHand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;

public class VersionOneGame {
    private final CardDealer cardDealer;
    private final HandEvaluator handEvaluator;
    private final MultipleHandEvaluator multipleHandEvaluator;

    //https://dzone.com/articles/yet-4-more-techniques-for-writing-better-java validate params for null
    public VersionOneGame(CardDealer cardDealer, HandEvaluator handEvaluator, MultipleHandEvaluator multipleHandEvaluator) {
        this.cardDealer = cardDealer;
        this.handEvaluator = handEvaluator;
        this.multipleHandEvaluator = multipleHandEvaluator;
    }

    public List<PlayerResult> playGame(Player... players) {
        List<Player> playersWithAHandOfCards = dealHandToAllPlayers(players);
        List<PlayerResult> playerResults = evaluateGame(playersWithAHandOfCards);
        System.out.println(playerResults);

        return playerResults;
    }

    private List<Player> dealHandToAllPlayers(Player[] players) {
        return Arrays.stream(players).map(this::dealHand).collect(Collectors.toList());
    }

    private Player dealHand(Player player) {
        DealtCards dealtCards = cardDealer.dealHand(5);
        PokerHand pokerHand = handEvaluator.setPokerHand(dealtCards);
        return player(player.playerName, dealtCards.getCards(), pokerHand);
    }

    private List<PlayerResult> evaluateGame(List<Player> players) {
        return multipleHandEvaluator.compareAllPlayersHands(players);
    }
}
