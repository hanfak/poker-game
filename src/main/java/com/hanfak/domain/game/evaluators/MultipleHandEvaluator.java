package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;
import com.hanfak.domain.game.playershand.WinningHand;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

// TODO need to unit test
public class MultipleHandEvaluator {

    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        List<PlayerResult> undeterminedPlayerResults = initializePlayerResults(players);

        // TODO can be combined to say player wiht best hand
        if (playerOneHasBetterBestHandThanPlayerTwo(undeterminedPlayerResults)) {
            return setPlayerOneAsWinner(undeterminedPlayerResults);
        }

        if (playerTwoHasBetterBestHandThanPlayerOne(undeterminedPlayerResults)) {
            return setPlayerTwoAsWinner(undeterminedPlayerResults);
        }

        // TODO Something complex about logic, try and simplify it
        if (allHandsHaveSamePair(undeterminedPlayerResults) || allHandsHaveABestHandOfAHighCard(players)) {
            List<List<Card>> collect = undeterminedPlayerResults.stream().map(x -> x.hand.cardsOfWinningHand.cardsInBestHand).collect(Collectors.toList());
            System.out.println("collect = " + collect);
            return determineResultOfPlayersWithSameBestHand(undeterminedPlayerResults);
        } else {
            return determineResultOfPlayersWithDifferentCardsInSameBestHands(undeterminedPlayerResults);
        }
    }

    private boolean allHandsHaveABestHandOfAHighCard(List<Player> players) {
        return players.stream().filter(player -> player.hand.cardsOfWinningHand.winningHand.equals(WinningHand.HIGH_CARD)).map(player -> player.hand.cardsOfWinningHand.winningHand).distinct().count() == 1;
    }

    private boolean allHandsHaveSamePair(List<PlayerResult> undeterminedPlayerResults) {
        List<List<Card>> collect = undeterminedPlayerResults.stream().map(playerResult -> playerResult.hand.cardsOfWinningHand.cardsInBestHand).collect(Collectors.toList());
        List<Rank> rank = collect.stream().flatMap(Collection::stream).map(card -> card.rank).distinct().collect(Collectors.toList());
        return rank.size() == 1;
    }

    private List<PlayerResult> determineResultOfPlayersWithDifferentCardsInSameBestHands(List<PlayerResult> undeterminedPlayerResults) {
        List<PlayerResult> playerResultsOrderedByHighestPair = undeterminedPlayerResults.stream().sorted(Comparator.comparing(x -> x.hand.cardsOfWinningHand.cardsInBestHand.get(0).rank.getLevelCode())).
                collect(Collectors.toList());
        System.out.println("players result in order of pairs " + playerResultsOrderedByHighestPair);
        List<PlayerResult> playerResultsWithWinLossRecorded = Stream.of(setPlayerResultOfWinner(playerResultsOrderedByHighestPair), setPlayerResultsOfLosers(playerResultsOrderedByHighestPair))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("Players with results " + playerResultsWithWinLossRecorded);
        return playerResultsWithWinLossRecorded;
    }


    private List<PlayerResult> determineResultOfPlayersWithSameBestHand(List<PlayerResult> undeterminedPlayerResults) {
        List<Integer> cardsWhichMatch = determineCardsThatAreTheSameOrDifferent(undeterminedPlayerResults);
        if (!cardsWhichMatch.isEmpty()) {
            // TODO extract sameBestHandEvaluator dependeny
            return determineWinOrLossForEachPlayer(undeterminedPlayerResults, cardsWhichMatch);
        } else {
            //tODO extract DrawEvaluator dependeny
            return setDrawForPlayers(undeterminedPlayerResults); //
        }
    }

    private List<PlayerResult> setPlayerTwoAsWinner(List<PlayerResult> undeterminedPlayerResults) {
        PlayerResult playerResultForWinner = PlayerResult.playerResult(undeterminedPlayerResults.get(1).playerName, Result.WIN, undeterminedPlayerResults.get(1).hand);
        PlayerResult playerResultForLoser = PlayerResult.playerResult(undeterminedPlayerResults.get(0).playerName, Result.LOSS, undeterminedPlayerResults.get(0).hand);
        return Stream.of(playerResultForWinner, playerResultForLoser)
                .collect(Collectors.toList());
    }

    private List<PlayerResult> setPlayerOneAsWinner(List<PlayerResult> undeterminedPlayerResults) {
        PlayerResult playerResultForWinner = PlayerResult.playerResult(undeterminedPlayerResults.get(0).playerName, Result.WIN, undeterminedPlayerResults.get(0).hand);
        PlayerResult playerResultForLoser = PlayerResult.playerResult(undeterminedPlayerResults.get(1).playerName, Result.LOSS, undeterminedPlayerResults.get(1).hand);
        return Stream.of(playerResultForWinner, playerResultForLoser)
                .collect(Collectors.toList());
    }

    private boolean playerTwoHasBetterBestHandThanPlayerOne(List<PlayerResult> undeterminedPlayerResults) {
        return undeterminedPlayerResults.get(0).hand.cardsOfWinningHand.winningHand.ordinal() < undeterminedPlayerResults.get(1).hand.cardsOfWinningHand.winningHand.ordinal();
    }

    private boolean playerOneHasBetterBestHandThanPlayerTwo(List<PlayerResult> undeterminedPlayerResults) {
        return undeterminedPlayerResults.get(0).hand.cardsOfWinningHand.winningHand.ordinal() > undeterminedPlayerResults.get(1).hand.cardsOfWinningHand.winningHand.ordinal();
    }

    private List<PlayerResult> initializePlayerResults(List<Player> players) {
        return players.stream().
                map(player -> playerResult(player.playerName, null, player.hand)).
                collect(Collectors.toList());
    }

    private List<Integer> determineCardsThatAreTheSameOrDifferent(List<PlayerResult> undeterminedPlayerResults) {
        return IntStream.range(0, 5).
                map(changeCardToComparisonNumber(undeterminedPlayerResults))
                .boxed()
                .filter(checkedIndex -> checkedIndex != 0)
                .collect(Collectors.toList());
    }

    private List<PlayerResult> setDrawForPlayers(List<PlayerResult> undeterminedPlayerResults) {
        List<PlayerResult> playerResultsDetermined = undeterminedPlayerResults.stream().map(playerResult -> playerResult(playerResult.playerName, Result.DRAW, playerResult.hand)).collect(Collectors.toList());
        System.out.println("draw" + playerResultsDetermined);

        return playerResultsDetermined;
    }

    //    TODO Should I return a list of [[win],[loss]], [[draw,draw]]???
    private List<PlayerResult> determineWinOrLossForEachPlayer(List<PlayerResult> undeterminedPlayerResults, List<Integer> cardsWhichMatch) {

        List<PlayerResult> collect = undeterminedPlayerResults.stream()
                .sorted(compareCardRanksOf(cardsWhichMatch))
                .collect(Collectors.toList());

        List<PlayerResult> playersResults = Stream.of(setPlayerResultOfWinner(collect), setPlayerResultsOfLosers(collect))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println("player results : " + playersResults);

        return playersResults;
    }

    private Comparator<PlayerResult> compareCardRanksOf(List<Integer> cardsWhichMatch) {
        return (x, y) -> {
            Integer levelCodeOfX = x.hand.cards.get(cardsWhichMatch.get(0) - 1).rank.getLevelCode();
            Integer levelCodeOfY = y.hand.cards.get(cardsWhichMatch.get(0) - 1).rank.getLevelCode();
            return levelCodeOfX.compareTo(levelCodeOfY);
        };
    }

    // TODO rename x and y to proper names
    private List<PlayerResult> setPlayerResultOfWinner(List<PlayerResult> collect) {
        return collect.subList(0, 1).stream()
                .map(x -> playerResult(x.playerName, Result.WIN, x.hand))
                .collect(Collectors.toList());
    }

    private List<PlayerResult> setPlayerResultsOfLosers(List<PlayerResult> collect) {
        return collect.subList(1, collect.size()).stream()
                .map(x -> playerResult(x.playerName, Result.LOSS, x.hand))
                .collect(Collectors.toList());
    }

    private IntUnaryOperator changeCardToComparisonNumber(List<PlayerResult> undeterminedPlayerResults) {
        return index -> {
            if (areAllCardsInEachHandInThisPositionEqual(undeterminedPlayerResults, index)) {
                return 0;
            } else {
                return index + 1;
            }
        };
    }

    private boolean areAllCardsInEachHandInThisPositionEqual(List<PlayerResult> undeterminedPlayerResults, int index) {
        List<Rank> cardsInPosition = undeterminedPlayerResults.stream().map(playerResult -> playerResult.hand.cards.get(index).rank).distinct().collect(Collectors.toList());
        return 1 == cardsInPosition.size();
    }
}
