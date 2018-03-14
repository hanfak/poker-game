package com.hanfak.domain.cards;

import com.hanfak.domain.deck.Deck;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static testinfrastructure.CardsExamples.ACE_OF_SPADES;
import static testinfrastructure.CardsExamples.THREE_OF_SPADES;

public class DeckTest implements WithAssertions {


    @Test
    public void createsADeckOf52CardsThatAreUnique() throws Exception {
        Deck deck = new Deck();

        List<Card> cardsInDeck = deck.cardsInDeck;
        assertThat(cardsInDeck.size()).isEqualTo(52);
        assertThat(cardsInDeck.stream().distinct().count()).isEqualTo(52);
    }

    @Test
    public void removesASetOfCardsFromDeck() throws Exception {
        List<Card> cardsToRemove = Arrays.asList(THREE_OF_SPADES, ACE_OF_SPADES);
        Deck deck = new Deck();

        deck.removeCards(cardsToRemove);

        assertThat(deck.cardsInDeck.size()).isEqualTo(50);
        assertThat(deck.cardsInDeck).doesNotContain(THREE_OF_SPADES, ACE_OF_SPADES);
    }

    @Test
    public void CardIsNotInDeckWhenRemoving() throws Exception {
        List<Card> cardsToRemove = Arrays.asList(THREE_OF_SPADES, ACE_OF_SPADES);
        Deck deck = new Deck();

        deck.removeCards(cardsToRemove);
        deck.removeCards(singletonList(THREE_OF_SPADES));

        assertThat(deck.cardsInDeck.size()).isEqualTo(50);
    }

    @Test
    public void has13HeartsInDeck() throws Exception {
        Deck deck = new Deck();

        long count = deck.cardsInDeck.stream().filter(card -> Suit.HEART.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

    @Test
    public void has13ClubsInDeck() throws Exception {
        Deck deck = new Deck();

        long count = deck.cardsInDeck.stream().filter(card -> Suit.CLUB.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

    @Test
    public void has13DiamondsInDeck() throws Exception {
        Deck deck = new Deck();

        long count = deck.cardsInDeck.stream().filter(card -> Suit.DIAMOND.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

    @Test
    public void has13SpadesInDeck() throws Exception {
        Deck deck = new Deck();

        long count = deck.cardsInDeck.stream().filter(card -> Suit.SPADE.equals(card.suit)).count();

        assertThat(count).isEqualTo(13);
    }

}