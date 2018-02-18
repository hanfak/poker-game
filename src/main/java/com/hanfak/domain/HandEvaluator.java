package com.hanfak.domain;

public class HandEvaluator {
    public Hand scoreHand(Hand hand) {
        System.out.println(hand.cards);
        System.out.println(hand.cards.get(0));
        // TODO Game logic, for first test can check for uniqueness of all cards and no 5 adjacent cards
        return Hand.hand(hand.cards, WinningHand.HIGHCARD);
    }
}

/*
* Order hand.cards
* Look for each type of hand in Winning hand, start from best to worst
* if match return that winningHand
*
* Could inject list of evaluators for each hand, flush evaluator, straight evaluator, all implementing evaluator interface
*
*
* */