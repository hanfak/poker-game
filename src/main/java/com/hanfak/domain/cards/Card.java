package com.hanfak.domain.cards;

public class Card /*implements Comparable<Card>*/ {
    public final Rank rank;
    public final Suit suit;

    private Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card card(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
// TODO Issue when comparing cards more than 3 in pokerhandcards,
        // Looks like comparing kickers, before or overiding
        /*
        * 70: [PlayerResult{playerName='three', result=1, hand=[[Six♦, Six♣, Five❤, Five♣], [King♣]], pokerHand=TwoPair}
, PlayerResult{playerName='two', result=2, hand=[[Jack♠, Jack♦], [Ace❤, Ten❤, Seven♣]], pokerHand=Pair}
, PlayerResult{playerName='four', result=3, hand=[[Seven❤, Seven♠], [King♦, Queen❤, Two♠]], pokerHand=Pair}
, PlayerResult{playerName='one', result=4, hand=[[Nine♦, Nine❤], [King♠, Eight♣, Three❤]], pokerHand=Pair}
, PlayerResult{playerName='five', result=5, hand=[[Jack❤], [Ten♦, Eight❤, Four♣, Three♠]], pokerHand=HighCard}*/
        return this.rank == card.rank;
    }

    @Override
    public int hashCode() {
        int result = rank.hashCode();
        result = 31 * result + suit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        // TODO move to Suit enum??
        String suitSymbol = "";

        switch (suit) {
            case SPADE:
                suitSymbol = "\u2660";
                break;
            case DIAMOND:
                suitSymbol = "\u2666";
                break;
            case CLUB:
                suitSymbol = "\u2663";
                break;
            case HEART:
                suitSymbol = "\u2764";
                break;
        }
        return rank.name().charAt(0) + rank.name().substring(1).toLowerCase() +
                suitSymbol;
    }

    // TODO test
//    @Override
//    public int compareTo(Card o) {
//        return rank.compareTo(o.rank);
//    }
}
