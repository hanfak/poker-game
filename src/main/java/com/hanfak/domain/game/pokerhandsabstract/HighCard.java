package com.hanfak.domain.game.pokerhandsabstract;

import com.hanfak.domain.cards.Rank;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HighCard extends PokerHand {

    private final Rank rank;
    private final List<Rank> kickers;

    public HighCard(Rank rank, List<Rank> kickers) {
        super(0);
        this.rank = rank;
        this.kickers = kickers;
    }

    @Override
    public int compareTo(PokerHand pokerHand) {
        if(pokerHand instanceof HighCard) {
            // handle same class
            final HighCard otherHighCard = (HighCard) pokerHand;
            final int comparison = rank.compareTo(otherHighCard.rank);
            if(comparison == 0) {
                return compareKickers(otherHighCard);
            }
            return comparison;
        }
        if (ranking < pokerHand.ranking)
            return -1;
        return 1;
    }
    // Apply to straight
    private int compareKickers(HighCard otherHighCard) {
        int result = 0;
        Rank highestRank = null;
        for (Rank kicker : kickers) {
            if(highestRank == null || highestRank.compareTo(kicker) < 0) {
                result = 1;
                highestRank = kicker;
            }
        }
        for(Rank kicker : otherHighCard.kickers) {
            if(highestRank == null || highestRank.compareTo(kicker) < 0) {
                result = -1;
                highestRank = kicker;
            }
            if(highestRank.compareTo(kicker) == 0) {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "HighCard{" +
                "rank=" + rank +
                ", kickers=" + kickers +
                "}\n";
    }

    public static void main(String[] args) {
        // Need to be in order first
        final List<PokerHand> pokerHands = Arrays.asList(
                new HighCard(Rank.TWO, Arrays.asList(Rank.THREE, Rank.FOUR, Rank.FIVE)),
                new Pair(Rank.TWO, Arrays.asList(Rank.THREE, Rank.FOUR, Rank.FIVE)),
                new HighCard(Rank.KING, Arrays.asList(Rank.THREE, Rank.FOUR, Rank.FIVE)),
                new HighCard(Rank.TWO, Arrays.asList(Rank.THREE, Rank.FOUR, Rank.FIVE)),
                new Pair(Rank.TWO, Arrays.asList(Rank.THREE, Rank.FOUR, Rank.SIX)),
                new HighCard(Rank.TWO, Arrays.asList(Rank.THREE, Rank.FOUR, Rank.FIVE)),
                new HighCard(Rank.TWO, Arrays.asList(Rank.FIVE, Rank.FOUR, Rank.THREE)),
                new HighCard(Rank.ACE, Arrays.asList( Rank.FOUR, Rank.FIVE, Rank.SIX)),
                new Pair(Rank.TWO, Arrays.asList(Rank.TWO, Rank.THREE, Rank.FOUR)),
                new HighCard(Rank.TWO, Arrays.asList( Rank.FOUR, Rank.FIVE, Rank.SIX))
        );

        System.out.println("pokerHands: " + pokerHands);
        Collections.sort(pokerHands);
        System.out.println("pokerHands sorted: " + pokerHands);
    }
}
