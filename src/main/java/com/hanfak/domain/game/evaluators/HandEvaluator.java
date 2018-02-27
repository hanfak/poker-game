package com.hanfak.domain.game.evaluators;

import com.hanfak.domain.game.playershand.Hand;
import com.hanfak.domain.game.playershand.WinningHand;

public class HandEvaluator {
    public Hand scoreHand(Hand hand) {
        System.out.println(hand.cards);
        // TODO Game logic, for first test can check for uniqueness of all cards and no 5 adjacent cards
        return Hand.hand(hand.cards, WinningHand.HIGH_CARD);
    }
}

/*
* Order hand.cards
* Look for each type of hand in Winning hand, start from best to worst
* if match return that winningHand
*
* Could inject list of evaluators for each hand, flush evaluator, straight evaluator,
* all implementing evaluator interface, to allow for different impl but this logic should never change
*
*
* */