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
        // TODO instead of creating it here, pass players to rest and then create it a tthe end
        List<PlayerResult> undeterminedPlayerResults = initializePlayerResults(players);

        if(playersHaveDifferentBestHands(undeterminedPlayerResults)) {
            return determineResultsOfPlayersWithUniqueBestHands(undeterminedPlayerResults);
        }

        if (allHandsHaveTheSameBestHand(undeterminedPlayerResults) ) {
            List<List<Card>> collect = undeterminedPlayerResults.stream().map(x -> x.hand.cardsOfWinningHand.cardsInBestHand).collect(Collectors.toList());
            System.out.println("collect = " + collect);
            return determineResultOfPlayersWithSameCardsInSameBestHand(undeterminedPlayerResults);
        } else {
            return determineResultOfPlayersWithDifferentCardsInSameBestHands(undeterminedPlayerResults);
        }
    }

    private boolean playersHaveDifferentBestHands(List<PlayerResult> undeterminedPlayerResults) {
        List<WinningHand> diffPair = undeterminedPlayerResults.stream().map(playerResult -> playerResult.hand.cardsOfWinningHand.winningHand).distinct().collect(Collectors.toList());
        System.out.println("diffPair = " + diffPair);
        return diffPair.size() > 1;
    }

    private boolean allHandsHaveTheSameBestHand(List<PlayerResult> undeterminedPlayerResults) {
        List<List<Card>> samwPair = undeterminedPlayerResults.stream().map(playerResult -> playerResult.hand.cardsOfWinningHand.cardsInBestHand).collect(Collectors.toList());
        System.out.println("samwPair = " + samwPair);
        List<Rank> rank = samwPair.stream().flatMap(Collection::stream).map(card -> card.rank).distinct().collect(Collectors.toList());
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

    private List<PlayerResult> determineResultOfPlayersWithSameCardsInSameBestHand(List<PlayerResult> undeterminedPlayerResults) {
        List<Integer> cardsWhichMatch = determineCardsThatAreTheSameOrDifferent(undeterminedPlayerResults);
        if (!cardsWhichMatch.isEmpty()) {
            // TODO extract sameBestHandEvaluator dependeny
            return determineWinOrLossForEachPlayer(undeterminedPlayerResults, cardsWhichMatch);
        } else {
            //tODO extract DrawEvaluator dependeny
            return setDrawForPlayers(undeterminedPlayerResults); //
        }
    }

    private List<PlayerResult> determineResultsOfPlayersWithUniqueBestHands(List<PlayerResult> undeterminedPlayerResults) {
        List<PlayerResult> orderedPlayersByBestHand = undeterminedPlayerResults.stream().sorted(winningHandsOfPlayers()).collect(Collectors.toList());
        System.out.println("orderedPlayersByBestHand = " + orderedPlayersByBestHand);
        return Stream.of(setPlayerResultOfWinner(orderedPlayersByBestHand), setPlayerResultsOfLosers(orderedPlayersByBestHand))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Comparator<PlayerResult> winningHandsOfPlayers() {
        return (player1, player2) -> {
            Integer player2WinningHand = player2.hand.cardsOfWinningHand.winningHand.ordinal();
            Integer player1WinningHand = player1.hand.cardsOfWinningHand.winningHand.ordinal();
            return player2WinningHand.compareTo(player1WinningHand);
        };
    }

    private List<PlayerResult> initializePlayerResults(List<Player> players) {
        return players.stream().
                map(player -> playerResult(player.playerName, null, player.hand)).
                collect(Collectors.toList());
    }

    private List<Integer> determineCardsThatAreTheSameOrDifferent(List<PlayerResult> undeterminedPlayerResults) {
        return IntStream.range(0, 5)
                .map(changeCardToComparisonNumber(undeterminedPlayerResults))
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
        List<PlayerResult> sortedPlayersResultsByComparingEachCardInHand = undeterminedPlayerResults.stream()
                .sorted(compareCardRanksOf(cardsWhichMatch))
                .collect(Collectors.toList());

        List<PlayerResult> playersResults = Stream.of(setPlayerResultOfWinner(sortedPlayersResultsByComparingEachCardInHand), setPlayerResultsOfLosers(sortedPlayersResultsByComparingEachCardInHand))
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

    private List<PlayerResult> setPlayerResultOfWinner(List<PlayerResult> undeterminedPlayerResults) {
        return undeterminedPlayerResults.subList(0, 1).stream()
                .map(winningPlayerResult -> playerResult(winningPlayerResult.playerName, Result.WIN, winningPlayerResult.hand))
                .collect(Collectors.toList());
    }

    private List<PlayerResult> setPlayerResultsOfLosers(List<PlayerResult> undeterminedPlayerResults) {
        return undeterminedPlayerResults.subList(1, undeterminedPlayerResults.size()).stream()
                .map(losingPlayerResult -> playerResult(losingPlayerResult.playerName, Result.LOSS, losingPlayerResult.hand))
                .collect(Collectors.toList());
    }

    private IntUnaryOperator changeCardToComparisonNumber(List<PlayerResult> undeterminedPlayerResults) {
        return positionOfCardInHand ->
             areAllCardsInEachHandInThisPositionEqual(undeterminedPlayerResults, positionOfCardInHand) ?
                     0 : positionOfCardInHand + 1;
    }

    private boolean areAllCardsInEachHandInThisPositionEqual(List<PlayerResult> undeterminedPlayerResults, int index) {
        List<Rank> cardsInPosition = undeterminedPlayerResults.stream().map(playerResult -> playerResult.hand.cards.get(index).rank).distinct().collect(Collectors.toList());
        return 1 == cardsInPosition.size();
    }
}
