package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.domain.game.Result;
import com.hanfak.domain.game.playershand.BestHand;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.hanfak.domain.game.PlayerResult.playerResult;

// TODO need to unit test
// TODO too big a class
public class MultipleHandEvaluator {
    // TODO extraact to class, for determining if best hands are the same or different
    // TODO extract to class, comparing cards for results
    public List<PlayerResult> compareAllPlayersHands(List<Player> players) {
        if (playersHaveDifferentBestHands(players)) {
            return determineResultsOfPlayersWithUniqueBestHands(players);
        }

        if (allPlayersHandsHaveTheSameBestHand(players)) {
            List<List<Card>> collect = players.stream().map(x -> x.hand.cardsOfWinningHand.cardsInBestHand).collect(Collectors.toList());
            System.out.println("collect = " + collect);
            return determineResultOfPlayersWithSameCardsInSameBestHand(players);
        } else {
            return determineResultOfPlayersWithDifferentCardsInSameBestHands(players);
        }
    }

    private boolean playersHaveDifferentBestHands(List<Player> players) {
        List<BestHand> diffPair = players.stream().map(player -> player.hand.cardsOfWinningHand.bestHand).distinct().collect(Collectors.toList());
        System.out.println("diffPair = " + diffPair);
        return diffPair.size() > 1;
    }

    private List<PlayerResult> determineResultsOfPlayersWithUniqueBestHands(List<Player> players) {
        List<Player> orderedPlayersByBestHand = players.stream()
                .sorted(winningHandsOfPlayers()).collect(Collectors.toList());
        System.out.println("orderedPlayersByBestHand = " + orderedPlayersByBestHand);
        return Stream.of(setPlayerResultOfWinner(orderedPlayersByBestHand), setPlayerResultsOfLosers(orderedPlayersByBestHand))
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    private Comparator<Player> winningHandsOfPlayers() {
        return (player1, player2) -> {
            Integer player2WinningHand = player2.hand.cardsOfWinningHand.bestHand.ordinal();
            Integer player1WinningHand = player1.hand.cardsOfWinningHand.bestHand.ordinal();
            return player2WinningHand.compareTo(player1WinningHand);
        };
    }

    private List<PlayerResult> determineResultOfPlayersWithDifferentCardsInSameBestHands(List<Player> players) {
        List<Player> playersOrderedByHighestPair = players.stream().sorted(Comparator.comparing(x -> x.hand.cardsOfWinningHand.cardsInBestHand.get(0).rank.getLevelCode())).
                collect(Collectors.toList());
        System.out.println("players result in order of pairs " + playersOrderedByHighestPair);
        List<PlayerResult> playerResultsWithWinLossRecorded = Stream.of(setPlayerResultOfWinner(playersOrderedByHighestPair), setPlayerResultsOfLosers(playersOrderedByHighestPair))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("Players with results " + playerResultsWithWinLossRecorded);
        return playerResultsWithWinLossRecorded;
    }

    private List<PlayerResult> setPlayerResultOfWinner(List<Player> players) {
        return players.subList(0, 1).stream()
                .map(winningPlayer -> playerResult(winningPlayer.playerName, Result.WIN, winningPlayer.hand))
                .collect(Collectors.toList());
    }

    private List<PlayerResult> setPlayerResultsOfLosers(List<Player> players) {
        return players.subList(1, players.size()).stream()
                .map(losingPlayer -> playerResult(losingPlayer.playerName, Result.LOSS, losingPlayer.hand))
                .collect(Collectors.toList());
    }

    private boolean allPlayersHandsHaveTheSameBestHand(List<Player> players) {
        // Better logic

        List<List<Card>> sameBestHand = players.stream()
                .map(playerResult -> playerResult.hand.cardsOfWinningHand.cardsInBestHand)
                .collect(Collectors.toList());
        System.out.println("sameBestHand = " + sameBestHand);

        List<List<Rank>> handsWithDistinctRanks = sameBestHand.stream()
                .map(x -> x.stream()
                        .map(y -> y.rank)
                        .collect(Collectors.toList()))
                .distinct().collect(Collectors.toList());
        System.out.println("handsWithDistinctRanks = " + handsWithDistinctRanks);

        return 1 == handsWithDistinctRanks.size();
    }

    private List<PlayerResult> determineResultOfPlayersWithSameCardsInSameBestHand(List<Player> players) {
        List<Integer> cardsWhichMatch = determineCardsThatAreTheSameOrDifferent(players);
        if (!cardsWhichMatch.isEmpty()) {
            // TODO extract sameBestHandEvaluator dependeny
            return determineWinOrLossForEachPlayer(players, cardsWhichMatch);
        } else {
            //tODO extract DrawEvaluator dependeny
            return setDrawForPlayers(players); //
        }
    }

    private List<Integer> determineCardsThatAreTheSameOrDifferent(List<Player> players) {
        return IntStream.range(0, 5)
                .map(changeCardToComparisonNumber(players))
                .boxed()
                .filter(checkedIndex -> checkedIndex != 0)
                .collect(Collectors.toList());
    }

    private IntUnaryOperator changeCardToComparisonNumber(List<Player> players) {
        return positionOfCardInHand ->
                areAllCardsInEachHandInThisPositionEqual(players, positionOfCardInHand) ?
                        0 : positionOfCardInHand + 1;
    }

    private boolean areAllCardsInEachHandInThisPositionEqual(List<Player> players, int index) {
        List<Rank> cardsInPosition = players.stream().map(playerResult -> playerResult.hand.cards.get(index).rank).distinct().collect(Collectors.toList());
        return 1 == cardsInPosition.size();
    }

    //    TODO Should I return a list of [[win],[loss]], [[draw,draw]]???
    private List<PlayerResult> determineWinOrLossForEachPlayer(List<Player> players, List<Integer> cardsWhichMatch) {
        List<Player> sortedPlayersByComparingEachCardInHand = players.stream()
                .sorted(compareCardRanksOf(cardsWhichMatch))
                .collect(Collectors.toList());

        List<PlayerResult> playersResults = Stream.of(setPlayerResultOfWinner(sortedPlayersByComparingEachCardInHand), setPlayerResultsOfLosers(sortedPlayersByComparingEachCardInHand))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println("player results : " + playersResults);

        return playersResults;
    }

    private Comparator<Player> compareCardRanksOf(List<Integer> cardsWhichMatch) {
        return (x, y) -> {
            Integer levelCodeOfX = x.hand.cards.get(cardsWhichMatch.get(0) - 1).rank.getLevelCode();
            Integer levelCodeOfY = y.hand.cards.get(cardsWhichMatch.get(0) - 1).rank.getLevelCode();
            return levelCodeOfX.compareTo(levelCodeOfY);
        };
    }

    private List<PlayerResult> setDrawForPlayers(List<Player> players) {
        List<PlayerResult> playerResultsDetermined = players.stream().map(playerResult -> playerResult(playerResult.playerName, Result.DRAW, playerResult.hand)).collect(Collectors.toList());
        System.out.println("draw" + playerResultsDetermined);

        return playerResultsDetermined;
    }
}
