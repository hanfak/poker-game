package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.infrastructure.CollectionsCardShuffler;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.stream.IntStream;

public class CardDealerTest implements WithAssertions {

    // Could test using mocks for dependecies but shuffle makes it harder and lots of setup

    @Test
    public void dealsAShuffledDistinctHand() throws Exception {
        Deck deck = new Deck();
        CollectionsCardShuffler collectionsCardShuffler = new CollectionsCardShuffler();
        CardDealer cardDealer = new CardDealer(deck, collectionsCardShuffler);

        DealtCards hand = cardDealer.dealHand(4);

        assertThat(hand.cards.size()).isEqualTo(4);
        assertThat(hand.cards.stream().distinct().count()).isEqualTo(4);
    }

    @Test
    public void twoHandsDealtMustBeUnique() throws Exception {
        Deck deck = new Deck();
        CollectionsCardShuffler collectionsCardShuffler = new CollectionsCardShuffler();
        CardDealer cardDealer = new CardDealer(deck, collectionsCardShuffler);

        DealtCards handOne = cardDealer.dealHand(4);
        DealtCards handTwo = cardDealer.dealHand(4);

        assertThat(handOne.cards).doesNotContain(handTwo.cards.toArray(new Card[4]));
    }

    @Test
    public void throwsErrorIfNotEnoughCardsToDealAHand() throws Exception {
        Deck deck = new Deck();
        CollectionsCardShuffler collectionsCardShuffler = new CollectionsCardShuffler();
        CardDealer cardDealer = new CardDealer(deck, collectionsCardShuffler);

        IntStream.range(0, 12).forEach(x -> cardDealer.dealHand(4));
        assertThatThrownBy(() -> cardDealer.dealHand(4))
                .hasMessage("Not enough cards left over")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void blah() throws Exception {

    }

}