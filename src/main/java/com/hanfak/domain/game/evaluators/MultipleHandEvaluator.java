package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// TODO need to unit test
public class MultipleHandEvaluator {
    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        if (playerOnePokerHandIsBetterRankedThenPlayerTwoPokerHand(players)) {
            return assignPlayerOneWinsWithBestPokerHand(players);
        }
        if (playerTwoPokerHandIsBetterRankedThenPlayerOnePokerHand(players)) {
            return assignPlayerTwoWinsWithBestPokerHand(players);
        }
        if (pokerHandCardsAreTheSame(players)) {
            if (kickerCardsTheSame(players)) {
                return assignBothPlayersDraw(players);
            }
            return determinePlayerResultFromHighestKickerCard(players);
        } else {
            return determinePlayerResultFromPokerHandCards(players);
        }
    }

    private boolean playerTwoPokerHandIsBetterRankedThenPlayerOnePokerHand(List<Player> players) {
        return players.get(0).pokerHand.ranking() < players.get(1).pokerHand.ranking();
    }

    private boolean playerOnePokerHandIsBetterRankedThenPlayerTwoPokerHand(List<Player> players) {
        return players.get(0).pokerHand.ranking() > players.get(1).pokerHand.ranking();
    }

    private boolean kickerCardsTheSame(List<Player> players) {
        return players.get(0).pokerHand.getKickerCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList()).
                equals(players.get(1).pokerHand.getKickerCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList()));
    }

    private boolean pokerHandCardsAreTheSame(List<Player> players) {
        return players.get(0).pokerHand.getPokerHandsCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList())
                .equals(players.get(1).pokerHand.getPokerHandsCards().stream().map(card -> card.rank.ordinal()).collect(Collectors.toList()));
    }

    // TODO refactor dry up
    private List<PlayerResult> determinePlayerResultFromPokerHandCards(List<Player> players) {
        List<Card> playerOnePokerHandCards = players.get(0).pokerHand.getPokerHandsCards();
        List<Card> playerTwoPokerHandCards = players.get(1).pokerHand.getPokerHandsCards();
        return calculateResultBasedOnHighestDifferentCard(players, playerOnePokerHandCards, playerTwoPokerHandCards, playerOnePokerHandCards.size());
    }

    private List<PlayerResult> determinePlayerResultFromHighestKickerCard(List<Player> players) {
        List<Card> playerOneKickerCards = players.get(0).pokerHand.getKickerCards();
        List<Card> playerTwoKickerCards = players.get(1).pokerHand.getKickerCards();
        return calculateResultBasedOnHighestDifferentCard(players, playerOneKickerCards, playerTwoKickerCards, playerOneKickerCards.size());
    }

    // tODO refactor remove for loop
    private List<PlayerResult> calculateResultBasedOnHighestDifferentCard(List<Player> players, List<Card> playerOneCards, List<Card> playerTwoCards, int cardsSize) {
        List<PlayerResult> playerResults = null;
        for (int position = 0; position < cardsSize; position++) {
            if (rankOfPlayerCard(playerOneCards, position) > rankOfPlayerCard(playerTwoCards, position)) {
                PlayerResult playerOneResult = PlayerResult.playerResult(players.get(0).playerName, Result.WIN, players.get(0).pokerHand);
                PlayerResult playerTwoResult = PlayerResult.playerResult(players.get(1).playerName, Result.LOSS, players.get(1).pokerHand);
                playerResults = Arrays.asList(playerOneResult, playerTwoResult);
                break;
            }
            if (rankOfPlayerCard(playerOneCards, position) < rankOfPlayerCard(playerTwoCards, position)) {
                PlayerResult playerOneResult = PlayerResult.playerResult(players.get(0).playerName, Result.LOSS, players.get(0).pokerHand);
                PlayerResult playerTwoResult = PlayerResult.playerResult(players.get(1).playerName, Result.WIN, players.get(1).pokerHand);
                playerResults = Arrays.asList(playerOneResult, playerTwoResult);
                break;
            }
        }
        return playerResults;
    }

    private int rankOfPlayerCard(List<Card> playerOneCards, int i) {
        return playerOneCards.get(i).rank.ordinal();
    }

    private List<PlayerResult> assignPlayerTwoWinsWithBestPokerHand(List<Player> players) {
        return setPlayersResults(players, Result.LOSS, Result.WIN);
    }

    private List<PlayerResult> assignPlayerOneWinsWithBestPokerHand(List<Player> players) {
        return setPlayersResults(players, Result.WIN, Result.LOSS);
    }

    private List<PlayerResult> assignBothPlayersDraw(List<Player> players) {
        return setPlayersResults(players, Result.DRAW, Result.DRAW);
    }

    private List<PlayerResult> setPlayersResults(List<Player> players, Result playerOneResults, Result playerTwoResults) {
        PlayerResult playerOneResult = PlayerResult.playerResult(players.get(0).playerName, playerOneResults, players.get(0).pokerHand);
        PlayerResult playerTwoResult = PlayerResult.playerResult(players.get(1).playerName, playerTwoResults, players.get(1).pokerHand);
        return Arrays.asList(playerOneResult, playerTwoResult);
    }
}
