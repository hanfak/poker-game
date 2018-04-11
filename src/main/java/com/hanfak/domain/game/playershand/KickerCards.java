package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanfak.domain.cards.Card.card;
import static com.hanfak.domain.cards.Rank.*;
import static com.hanfak.domain.cards.Suit.DIAMOND;
import static com.hanfak.domain.cards.Suit.HEART;
import static com.hanfak.domain.cards.Suit.SPADE;

public class KickerCards implements Comparable<KickerCards> {
    private final List<Card> cards;

    public KickerCards(List<Card> cards) {
        this.cards = orderCards(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int compareTo(KickerCards otherKickerCards) {
        return COMPARATOR.compare(this, otherKickerCards);
    }

    private List<Card> orderCards(List<Card> cards) {
        return cards.stream().
                sorted(Comparator.comparingInt(card -> card.rank.getLevelCode())).
                collect(Collectors.toList());
    }

    private static final Comparator<KickerCards> COMPARATOR =
            (kickerCardsOne, kickerCardsTwo) -> IntStream.range(0, kickerCardsOne.getCards().size()).
                    map(i -> Integer.compare(kickerCardsOne.getCards().get(i).rank.getLevelCode(), kickerCardsTwo.getCards().get(i).rank.getLevelCode())).
                    filter(rankValue -> rankValue != 0).
                    findFirst().
                    orElse(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KickerCards that = (KickerCards) o;

        return cards != null ? cards.equals(that.cards) : that.cards == null;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }

    public static void main(String[] args) {
        Card ACE_OF_SPADES = card(ACE, SPADE);
        Card ACE_OF_SPADES1 = card(ACE, SPADE);
        Card ACE_OF_HEARTS = card(ACE, HEART);
        Card KING_OF_SPADES = card(KING, SPADE);
        Card JACK_OF_SPADES = card(JACK, SPADE);
        Card FIVE_OF_SPADES = card(FIVE, SPADE);
        Card FOUR_OF_SPADES = card(FOUR, SPADE);
        Card SIX_OF_SPADES = card(SIX, SPADE);
        Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
        Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);
        Card THREE_OF_SPADES = card(THREE, SPADE);
        Card TWO_OF_SPADES = card(TWO, SPADE);

        System.out.println(ACE_OF_SPADES.equals(ACE_OF_SPADES1));
        System.out.println(ACE_OF_SPADES.equals(ACE_OF_HEARTS));
        List<Card> kickersOne = Arrays.asList(ACE_OF_SPADES, FIVE_OF_SPADES, FOUR_OF_DIAMONDS, TWO_OF_SPADES);
        List<Card> kickersFour = Arrays.asList(ACE_OF_SPADES, FIVE_OF_SPADES, THREE_OF_DIAMONDS, TWO_OF_SPADES);
        List<Card> kickersFour1 = Arrays.asList(ACE_OF_SPADES, FIVE_OF_SPADES, THREE_OF_DIAMONDS, TWO_OF_SPADES);
        List<Card> kickersTwo = Arrays.asList(KING_OF_SPADES, SIX_OF_SPADES, FOUR_OF_DIAMONDS, TWO_OF_SPADES);
        List<Card> kickersThree = Arrays.asList(KING_OF_SPADES, SIX_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);
//        System.out.println(kickersFour.equals(kickersTwo));
        KickerCards kickerCardsOne = new KickerCards(kickersOne);
        KickerCards kickerCardsTwo = new KickerCards(kickersTwo);
        KickerCards kickerCardsThree = new KickerCards(kickersThree);
        KickerCards kickerCardsFour = new KickerCards(kickersFour);

//        List<KickerCards> kickerCards = Arrays.asList(kickerCardsTwo, kickerCardsOne, kickerCardsThree, kickerCardsFour);
        List<KickerCards> kickerCards = Arrays.asList(kickerCardsTwo, kickerCardsOne);
        System.out.println("before = " + kickerCards.stream().map(KickerCards::getCards).collect(Collectors.toList()));
        System.out.println(kickerCardsTwo.compareTo(kickerCardsOne));
        System.out.println(kickerCardsTwo.compareTo(kickerCardsOne));
        Collections.sort(kickerCards);

        System.out.println("after = " + kickerCards.stream().map(KickerCards::getCards).collect(Collectors.toList()));
    }

}
