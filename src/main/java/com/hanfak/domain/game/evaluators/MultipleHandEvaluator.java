package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.Player;

import java.util.List;
import java.util.stream.Collectors;

public class MultipleHandEvaluator {

    public List<Player> compareAllPlayersHands(List<Player> players) {
        return players.stream().sorted(this::compareHands).collect(Collectors.toList());
    }

    private int compareHands(Player playerOne, Player playerTwo) {
        // TODO If statement will be used for other siutations
//        if (playerOne.hand.winningHand.equals(playerTwo.hand.winningHand)) {
            // TODO law demeter violation
            // Edge case two players have 4 of a kind, need to compare on high card, lots of other cases
            return Integer.compare(playerOne.hand.cards.get(0).rank.getLevelCode(), playerTwo.hand.cards.get(0).rank.getLevelCode());
//        } else {
//            return Integer.compare(playerOne.hand.winningHand.ordinal(), playerTwo.hand.winningHand.ordinal());
//        }
    }
}
