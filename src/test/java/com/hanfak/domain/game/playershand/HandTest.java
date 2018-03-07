package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.hanfak.domain.cards.Card.card;
import static com.hanfak.domain.cards.Rank.*;
import static com.hanfak.domain.cards.Suit.DIAMOND;
import static com.hanfak.domain.cards.Suit.SPADE;

public class HandTest implements WithAssertions{

    @Test
    public void whenCreatingANewHandFromASetOfCardsIt() throws Exception {
        Hand hand = Hand.hand(PLAYER_CARDS);

        assertThat(hand.cards.get(0)).isEqualTo(ACE_OF_SPADES);
        assertThat(hand.cards.get(1)).isEqualTo(FIVE_OF_SPADES);
        assertThat(hand.cards.get(2)).isEqualTo(FOUR_OF_DIAMONDS);
        assertThat(hand.cards.get(3)).isEqualTo(THREE_OF_SPADES);
        assertThat(hand.cards.get(4)).isEqualTo(TWO_OF_SPADES);
    }

    private static final Card ACE_OF_SPADES = card(ACE, SPADE);
    private static final Card FIVE_OF_SPADES = card(FIVE, SPADE);
    private static final Card TWO_OF_SPADES = card(TWO, SPADE);
    private static final Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
    private static final Card THREE_OF_SPADES = card(THREE, SPADE);

    private static final List<Card> PLAYER_CARDS = Arrays.asList(FIVE_OF_SPADES, ACE_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);

}