package com.hanfak.domain.game.playershand;

import com.hanfak.domain.cards.Card;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static testinfrastructure.CardsExamples.*;

public class KickerCardsTest implements WithAssertions {

    @Test
    public void kickerCardsAreOrderedByRank() throws Exception {
        KickerCards kickerCards = new KickerCards(this.unOrderedKickerCards2);

        assertThat(kickerCards.getCards()).containsExactly(TEN_OF_SPADES, SIX_OF_HEARTS, FIVE_OF_DIAMONDS);
    }

    @Test
    public void aceCardIsAlwaysFirst() throws Exception {
        KickerCards kickerCards = new KickerCards(this.unOrderedKickerCards);

        assertThat(kickerCards.getCards()).containsExactly(ACE_OF_SPADES, TEN_OF_SPADES, SIX_OF_HEARTS, FIVE_OF_DIAMONDS);
    }

    @Test
    public void equalkickerCardsReturn0() throws Exception {
        KickerCards kickerCards = new KickerCards(this.kickerCards);

        assertThat(kickerCards.compareTo(new KickerCards(kickerCards1))).isEqualTo(0);
    }

    @Test
    public void thisKickerCardsIsHigherThanAnotherKickerCardsWhenFirstCardIsInBothKickerCardsAreDifferent() throws Exception {
        KickerCards higherKickerCards = new KickerCards(this.kickerCards);
        KickerCards lowerKickerCards = new KickerCards(this.kickerCards2);
        List<KickerCards> list = Arrays.asList(lowerKickerCards, higherKickerCards);
        Collections.sort(list);
        assertThat(list).containsExactly(higherKickerCards, lowerKickerCards);
    }

    @Test
    public void thisKickerCardsIsLowerThanAnotherKickerCardsWhenSecondCardIsInBothKickerCardsAreDifferent() throws Exception {
        KickerCards lowerKickerCards = new KickerCards(this.kickerCards3);
        KickerCards higherKickerCards = new KickerCards(this.kickerCards);

        List<KickerCards> list = Arrays.asList(lowerKickerCards, higherKickerCards);
        Collections.sort(list);
        assertThat(list).containsExactly(higherKickerCards, lowerKickerCards);
    }

    @Test
    public void thisKickerCardsIsLowerThanAnotherKickerCardsWhenLastCardIsInBothKickerCardsAreDifferent() throws Exception {
        KickerCards lowerKickerCards = new KickerCards(this.kickerCards1);
        KickerCards higherKickerCards = new KickerCards(this.kickerCards);

        List<KickerCards> list = Arrays.asList(lowerKickerCards, higherKickerCards);
        Collections.sort(list);
        assertThat(list).containsExactly(higherKickerCards, lowerKickerCards);
    }

    private final List<Card> kickerCards = Arrays.asList(ACE_OF_SPADES,TEN_OF_SPADES, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);
    private final List<Card> unOrderedKickerCards = Arrays.asList(FIVE_OF_DIAMONDS, TEN_OF_SPADES, ACE_OF_SPADES, SIX_OF_HEARTS);
    private final List<Card> unOrderedKickerCards2 = Arrays.asList(FIVE_OF_DIAMONDS, TEN_OF_SPADES, SIX_OF_HEARTS);
    private final List<Card> kickerCards1 = Arrays.asList(ACE_OF_SPADES,TEN_OF_SPADES, SIX_OF_HEARTS,TWO_OF_HEARTS);
    private final List<Card> kickerCards2 = Arrays.asList(KING_OF_HEARTS,TEN_OF_SPADES, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);
    private final List<Card> kickerCards3 = Arrays.asList(ACE_OF_SPADES,NINE_OF_CLUBS, SIX_OF_HEARTS,FIVE_OF_DIAMONDS);

}