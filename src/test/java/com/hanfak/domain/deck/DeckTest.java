package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Suit;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static testinfrastructure.CardsExamples.ACE_OF_SPADES;
import static testinfrastructure.CardsExamples.THREE_OF_SPADES;

public class DeckTest implements WithAssertions {
    @Before
    public void setup() {
        Deck.resetDeck();
    }

    @Test
    public void createsADeckOf52CardsThatAreUnique() throws Exception {
        List<Card> cardsInDeck = Deck.getCardsInDeck();

        assertThat(cardsInDeck.size()).isEqualTo(52);
        assertThat(cardsInDeck.stream().distinct().count()).isEqualTo(52);
    }

    @Test
    public void resetsTheDeckToHaveFullDeckOfCards() throws Exception {
        List<Card> cardsToRemove = Arrays.asList(THREE_OF_SPADES, ACE_OF_SPADES);
        Deck.removeCards(cardsToRemove);

        Deck.resetDeck();

        assertThat(Deck.getCardsInDeck().size()).isEqualTo(52);
        assertThat(Deck.getCardsInDeck().stream().distinct().count()).isEqualTo(52);

    }

    @Test
    public void removesASetOfCardsFromDeck() throws Exception {

        List<Card> cardsToRemove = Arrays.asList(THREE_OF_SPADES, ACE_OF_SPADES);

        Deck.removeCards(cardsToRemove);

        assertThat(Deck.getCardsInDeck().size()).isEqualTo(50);
        assertThat(Deck.getCardsInDeck()).doesNotContain(THREE_OF_SPADES, ACE_OF_SPADES);
    }

    @Test
    public void CardIsNotInDeckWhenRemoving() throws Exception {
        List<Card> cardsToRemove = Arrays.asList(THREE_OF_SPADES, ACE_OF_SPADES);

        Deck.removeCards(cardsToRemove);
        Deck.removeCards(singletonList(THREE_OF_SPADES));

        assertThat(Deck.getCardsInDeck().size()).isEqualTo(50);
    }

    @Test
    public void has13HeartsInDeck() throws Exception {

        long count = Deck.getCardsInDeck().stream().filter(card -> Suit.HEART.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

    @Test
    public void has13ClubsInDeck() throws Exception {

        long count = Deck.getCardsInDeck().stream().filter(card -> Suit.CLUB.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

    @Test
    public void has13DiamondsInDeck() throws Exception {

        long count = Deck.getCardsInDeck().stream().filter(card -> Suit.DIAMOND.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

    @Test
    public void has13SpadesInDeck() throws Exception {

        long count = Deck.getCardsInDeck().stream().filter(card -> Suit.SPADE.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

}