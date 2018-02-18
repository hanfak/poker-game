package com.hanfak.domain.cards;

import com.hanfak.domain.game.Hand;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO Finish this see notes below
public class Deck {
    private final List<Rank> ranks = Arrays.asList(Rank.values());
    private final List<Suit> suits = Arrays.asList(Suit.values());
    private final List<Card> cardsDealt = new ArrayList<>();

    @SuppressWarnings("SameParameterValue")
    public Hand dealHand(int endExclusive) {
        List<Card> randomCardsCreated = IntStream.range(0, endExclusive)
                .mapToObj(i -> {
                    Card randomCard = generateRandomCard();
                    // TODO duplication, create equals for card... not sure??
                    while (this.cardsDealt.contains(randomCard)) {
                        randomCard = generateRandomCard();
                    }
                    return randomCard;
                }).collect(Collectors.toList());

        cardsDealt.addAll(randomCardsCreated);

        return Hand.hand(randomCardsCreated, null); // TODO reuse method but null is not empty
    }

    private Card generateRandomCard() {
        // TODO Extract to wrapper class in infrastructure for random and inject in to deck, and interface in domain
        // To allow different implementations of random to be used
        return Card.card(ranks.get(RandomUtils.nextInt(0, 13)), suits.get(RandomUtils.nextInt(0, 3)));
    }
}


// TODO edge case when cards run out??? exception thrown?? limit game to x players???