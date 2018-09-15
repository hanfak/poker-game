package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.infrastructure.CollectionsCardShuffler;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.stream.IntStream;

public class CardDealerTest implements WithAssertions {

    @Test
    public void dealsAShuffledDistinctHand() {
        CollectionsCardShuffler collectionsCardShuffler = new CollectionsCardShuffler();
        CardDealer cardDealer = new CardDealer(collectionsCardShuffler);

        DealtCards hand = cardDealer.dealHand(4);

        assertThat(hand.getCards().size()).isEqualTo(4);
        assertThat(hand.getCards().stream().distinct().count()).isEqualTo(4);
    }

    @Test
    public void twoHandsDealtMustBeUnique() {
        CollectionsCardShuffler collectionsCardShuffler = new CollectionsCardShuffler();
        CardDealer cardDealer = new CardDealer(collectionsCardShuffler);

        DealtCards handOne = cardDealer.dealHand(4);
        DealtCards handTwo = cardDealer.dealHand(4);

        assertThat(handOne.getCards()).doesNotContain(handTwo.getCards().toArray(new Card[4]));
    }

    @Test
    public void throwsErrorIfNotEnoughCardsToDealAHand() {
        CollectionsCardShuffler collectionsCardShuffler = new CollectionsCardShuffler();
        CardDealer cardDealer = new CardDealer(collectionsCardShuffler);

        IntStream.range(0, 12).forEach(x -> cardDealer.dealHand(4));
        assertThatThrownBy(() -> cardDealer.dealHand(4))
                .hasMessage("Not enough cards left over")
                .isInstanceOf(IllegalArgumentException.class);
    }
}