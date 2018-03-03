package com.hanfak.usecases;

import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.evaluators.HandEvaluator;
import com.hanfak.domain.game.evaluators.MultipleHandEvaluator;
import com.hanfak.domain.game.playershand.CardsOfWinningHand;
import com.hanfak.domain.game.playershand.Hand;
import com.hanfak.domain.game.playershand.WinningHand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;

public class VersionOneGame {
    private final CardDealer cardDealer;
    private final HandEvaluator handEvaluator;
    private final MultipleHandEvaluator multipleHandEvaluator;

    public VersionOneGame(CardDealer cardDealer, HandEvaluator handEvaluator, MultipleHandEvaluator multipleHandEvaluator) {
        this.cardDealer = cardDealer;
        this.handEvaluator = handEvaluator;
        this.multipleHandEvaluator = multipleHandEvaluator;
    }

    public List<PlayerResult> playGame(Player... players) {
        List<Player> playersWithAHandOfCards = dealHandToAllPlayers(players);
        List<Player> playersWithHandsScored = evaluatePlayersHandsOfCards(playersWithAHandOfCards);
        List<WinningHand> winninghands = playersWithHandsScored.stream().map(x -> x.hand.cardsOfWinningHand.winningHand).collect(Collectors.toList());
        System.out.println("winninghands = " + winninghands);
        List<PlayerResult> playerResults = evaluateGame(playersWithHandsScored);
        System.out.println(playerResults);

        return playerResults;
    }

    private List<Player> dealHandToAllPlayers(Player[] players) {
        return Arrays.stream(players).map(this::dealHand).collect(Collectors.toList());
    }

    private Player dealHand(Player player) {
        Hand hand = cardDealer.dealHand(5);
        return player(player.playerName, hand);
    }

    private List<Player> evaluatePlayersHandsOfCards(List<Player> players) {
        return players.stream().map(this::evaluateHand).collect(Collectors.toList());
    }

    private Player evaluateHand(Player player) {
        CardsOfWinningHand cardsOfWinningHand = handEvaluator.scoreHand(player.hand); //TODO return best hand
        Hand hand = Hand.hand(player.hand.cards, cardsOfWinningHand);
        return player(player.playerName, hand); // TODO create new hand
    }

    private List<PlayerResult> evaluateGame(List<Player> players) {
        return multipleHandEvaluator.compareAllPlayersHands(players);
    }
}
