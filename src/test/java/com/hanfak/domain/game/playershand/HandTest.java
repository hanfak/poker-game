package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static testinfrastructure.CardsExamples.*;

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


    private static final List<Card> PLAYER_CARDS = Arrays.asList(FIVE_OF_SPADES, ACE_OF_SPADES, TWO_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES);

}