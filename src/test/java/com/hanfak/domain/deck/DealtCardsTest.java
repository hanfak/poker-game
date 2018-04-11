package com.hanfak.domain.deck;

import com.hanfak.domain.cards.Card;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Arrays;

import static com.hanfak.domain.cards.Card.card;
import static com.hanfak.domain.cards.Rank.*;
import static com.hanfak.domain.cards.Suit.CLUB;
import static com.hanfak.domain.cards.Suit.DIAMOND;
import static com.hanfak.domain.cards.Suit.HEART;
import static com.hanfak.domain.cards.Suit.SPADE;

public class DealtCardsTest implements WithAssertions {

    @Test
    public void AceTo10InOrder() throws Exception {
        DealtCards dealtCards = new DealtCards(Arrays.asList(KING_OF_HEARTS,ACE_OF_SPADES,TEN_OF_SPADES, QUEEN_OF_SPADES, JACK_OF_DIAMONDS));

        assertThat(dealtCards.getCards()).containsExactly(ACE_OF_SPADES, KING_OF_HEARTS, QUEEN_OF_SPADES, JACK_OF_DIAMONDS, TEN_OF_SPADES);
    }

    @Test
    public void AceTo10InOrderDespiteDuplicates() throws Exception {
        DealtCards dealtCards = new DealtCards(Arrays.asList(TEN_OF_SPADES,ACE_OF_SPADES,TEN_OF_SPADES, KING_OF_HEARTS, JACK_OF_DIAMONDS));

        assertThat(dealtCards.getCards()).containsExactly(ACE_OF_SPADES, KING_OF_HEARTS, JACK_OF_DIAMONDS, TEN_OF_SPADES, TEN_OF_SPADES);
    }

    @Test
    public void AceToFiveInOrderWhereAceIsLast() throws Exception {
        DealtCards dealtCards = new DealtCards(Arrays.asList(TWO_OF_HEARTS,ACE_OF_SPADES,THREE_OF_DIAMONDS, FIVE_OF_DIAMONDS, FOUR_OF_CLUBS));

        assertThat(dealtCards.getCards()).containsExactly(FIVE_OF_DIAMONDS, FOUR_OF_CLUBS, THREE_OF_DIAMONDS, TWO_OF_HEARTS, ACE_OF_SPADES);
    }

    @Test
    public void AceToFiveInOrderWhereAceIsLastWithDuplicates() throws Exception {
        DealtCards dealtCards = new DealtCards(Arrays.asList(TWO_OF_HEARTS,ACE_OF_SPADES,THREE_OF_DIAMONDS, FIVE_OF_DIAMONDS, THREE_OF_DIAMONDS));

        assertThat(dealtCards.getCards()).containsExactly(FIVE_OF_DIAMONDS, THREE_OF_DIAMONDS, THREE_OF_DIAMONDS, TWO_OF_HEARTS, ACE_OF_SPADES);
    }

    @Test
    public void NoAdjacentCardsAndAceIsAtStart() throws Exception {
        DealtCards dealtCards = new DealtCards(Arrays.asList(SIX_OF_HEARTS,ACE_OF_SPADES,TEN_OF_SPADES, FIVE_OF_DIAMONDS, THREE_OF_DIAMONDS));

        assertThat(dealtCards.getCards()).containsExactly(ACE_OF_SPADES, TEN_OF_SPADES, SIX_OF_HEARTS, FIVE_OF_DIAMONDS, THREE_OF_DIAMONDS);
    }

    private static final Card TWO_OF_HEARTS = card(TWO, HEART);
    private static final Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);
    private static final Card FOUR_OF_CLUBS = card(FOUR, CLUB);
    private static final Card FIVE_OF_DIAMONDS = card(FIVE, DIAMOND);
    private static final Card SIX_OF_HEARTS = card(SIX, HEART);
    private static final Card ACE_OF_SPADES = card(ACE, SPADE);
    private static final Card KING_OF_HEARTS = card(KING, HEART);
    private static final Card QUEEN_OF_SPADES = card(QUEEN, SPADE);
    private static final Card JACK_OF_DIAMONDS = card(JACK, DIAMOND);
    private static final Card TEN_OF_SPADES = card(TEN, SPADE);


}