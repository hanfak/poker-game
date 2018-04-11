package com.hanfak.domain.cards;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

public class CardTest implements WithAssertions {
    @Test
    public void stringOfAHeartCardShowsAHeartSymbol() throws Exception {
        Card card = Card.card(Rank.EIGHT, Suit.HEART);
        assertThat(card.toString()).contains("\u2764");
    }

    @Test
    public void stringOfAClubCardShowsAClubSymbol() throws Exception {
        Card card = Card.card(Rank.EIGHT, Suit.CLUB);
        assertThat(card.toString()).contains("\u2663");
    }

    @Test
    public void stringOfASpadeCardShowsASpadeSymbol() throws Exception {
        Card card = Card.card(Rank.EIGHT, Suit.SPADE);
        assertThat(card.toString()).contains("\u2660");
    }

    @Test
    public void stringOfDiamondCardShowsADiamonSymbol() throws Exception {
        Card card = Card.card(Rank.EIGHT, Suit.DIAMOND);
        assertThat(card.toString()).contains("\u2666");
    }
}