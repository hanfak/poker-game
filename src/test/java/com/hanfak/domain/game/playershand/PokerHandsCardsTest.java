package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static testinfrastructure.CardsExamples.*;

public class PokerHandsCardsTest implements WithAssertions {
    @Test
    public void pokerHandCardsAreOrderedByRank() throws Exception {
    }

    @Test
    public void straightAceLowIsHasAceAsLastCard() throws Exception {
        PokerHandsCards pokerHandsCards = new PokerHandsCards(this.unorderedPokerHandCards1);

        assertThat(pokerHandsCards.getCards()).containsExactly(FIVE_OF_DIAMONDS, FOUR_OF_CLUBS, THREE_OF_DIAMONDS, TWO_OF_HEARTS, ACE_OF_SPADES);
    }
    
    @Test
    public void straightAceHighHasAceAsFirstCard() throws Exception {
        PokerHandsCards pokerHandsCards = new PokerHandsCards(this.unorderedPokerHandCards);

        assertThat(pokerHandsCards.getCards()).containsExactly(ACE_OF_SPADES,KING_OF_DIAMONDS, QUEEN_OF_SPADES,JACK_OF_DIAMONDS, TEN_OF_DIAMONDS);
    }
    
    @Test
    public void fullHouseHasThreeOfAKindGroupedFirstThenPair() throws Exception {
        PokerHandsCards pokerHandsCards = new PokerHandsCards(this.unorderedPokerHandCards2);

        assertThat(pokerHandsCards.getCards()).containsExactly(FIVE_OF_DIAMONDS,FIVE_OF_CLUBS,FIVE_OF_HEARTS,ACE_OF_DIAMONDS, ACE_OF_HEARTS);
    }
    
    @Test
    public void twoPairHasHighestPairFirst() throws Exception {
        PokerHandsCards pokerHandsCards = new PokerHandsCards(this.pokerHandCards5);

        assertThat(pokerHandsCards.getCards()).containsExactly(ACE_OF_DIAMONDS, ACE_OF_HEARTS, FIVE_OF_DIAMONDS,FIVE_OF_CLUBS);

    }

    @Test
    public void equalpokerHandsCardsReturn0() throws Exception {
        PokerHandsCards pokerHandsCards = new PokerHandsCards(this.pokerHandCards);

        assertThat(pokerHandsCards.compareTo(new PokerHandsCards(pokerHandCards1))).isEqualTo(0);
    }

    @Test
    public void thisPokerHandsCardsIsHigherThanAnotherPokerHandsCardsWhenFirstCardIsInBothPokerHandsCardsAreDifferent() throws Exception {
        PokerHandsCards higherPokerCards = new PokerHandsCards(this.pokerHandCards);
        PokerHandsCards lowerPokerCards = new PokerHandsCards(this.pokerHandCards2);
        List<PokerHandsCards> list = Arrays.asList(lowerPokerCards, higherPokerCards);
        Collections.sort(list);
        assertThat(list).containsExactly(higherPokerCards, lowerPokerCards);
    }

    @Test
    public void thisPokerHandsCardsIsLowerThanAnotherPokerHandsCardsWhenSecondCardIsInBothPokerHandsCardsAreDifferent() throws Exception {
        PokerHandsCards higherPokerCards = new PokerHandsCards(this.pokerHandCards);
        PokerHandsCards lowerPokerCards = new PokerHandsCards(this.pokerHandCards3);
        List<PokerHandsCards> list = Arrays.asList(lowerPokerCards, higherPokerCards);
        Collections.sort(list);
        assertThat(list).containsExactly(higherPokerCards, lowerPokerCards);
    }

    @Test
    public void thisPokerHandsCardsIsLowerThanAnotherPokerHandsCardsWhenLastCardIsInBothPokerHandsCardsAreDifferent() throws Exception {
        PokerHandsCards higherPokerCards = new PokerHandsCards(this.pokerHandCards);
        PokerHandsCards lowerPokerCards = new PokerHandsCards(this.pokerHandCards4);
        List<PokerHandsCards> list = Arrays.asList(lowerPokerCards, higherPokerCards);
        Collections.sort(list);
        assertThat(list).containsExactly(higherPokerCards, lowerPokerCards);
    }

    private final List<Card> pokerHandCards = Arrays.asList(ACE_OF_SPADES,TEN_OF_SPADES, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);
    private final List<Card> unorderedPokerHandCards = Arrays.asList(QUEEN_OF_SPADES,ACE_OF_SPADES,KING_OF_DIAMONDS, JACK_OF_DIAMONDS, TEN_OF_DIAMONDS);
    private final List<Card> unorderedPokerHandCards1 = Arrays.asList(TWO_OF_HEARTS,ACE_OF_SPADES,FOUR_OF_CLUBS, FIVE_OF_DIAMONDS, THREE_OF_DIAMONDS);
    private final List<Card> unorderedPokerHandCards2= Arrays.asList(ACE_OF_DIAMONDS, ACE_OF_HEARTS, FIVE_OF_DIAMONDS,FIVE_OF_CLUBS,FIVE_OF_HEARTS);
    private final List<Card> pokerHandCards1 = Arrays.asList(ACE_OF_SPADES,TEN_OF_SPADES, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);
    private final List<Card> pokerHandCards2 = Arrays.asList(KING_OF_HEARTS,TEN_OF_SPADES, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);
    private final List<Card> pokerHandCards3 = Arrays.asList(ACE_OF_SPADES,NINE_OF_CLUBS, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);
    private final List<Card> pokerHandCards4 = Arrays.asList(ACE_OF_SPADES,TEN_OF_SPADES, SIX_OF_HEARTS,FOUR_OF_CLUBS);
    private final List<Card> pokerHandCards5= Arrays.asList(FIVE_OF_DIAMONDS,FIVE_OF_CLUBS, ACE_OF_DIAMONDS, ACE_OF_HEARTS);

}