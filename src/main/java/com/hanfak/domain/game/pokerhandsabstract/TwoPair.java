package com.hanfak.domain.game.pokerhandsabstract;

import com.hanfak.domain.cards.Rank;

import java.util.List;

public class TwoPair extends PokerHand {

    private final Rank higherPair;
    private final Rank lowerPair;
    private final List<Rank> kickers;


    public TwoPair(Rank higherPair, Rank lowerPair, List<Rank> kickers) {
        super(2);
        this.higherPair = higherPair;
        this.lowerPair = lowerPair;
        this.kickers = kickers;
    }

    @Override
    public int compareTo(PokerHand pokerHand) {
        if(pokerHand instanceof TwoPair) {
            return handleSameClass((TwoPair) pokerHand);
        }
        if(ranking < pokerHand.ranking) return -1;
        return 1;
    }

    private int handleSameClass(TwoPair otherTwoPair) {
        final int higherPairComparison = higherPair.compareTo(otherTwoPair.higherPair);
        if (higherPairComparison == 0) {
            int lowestPairComparison = lowerPair.compareTo(otherTwoPair.lowerPair);
            if (lowestPairComparison == 0) {
                return compareKickers(otherTwoPair);
            }
            return lowestPairComparison;
        }
        return higherPairComparison;
    }

    private int compareKickers(TwoPair otherTwoPair) {
        int result = 0;
        Rank highestRank = null;
        for (Rank kicker : kickers) {
            if(highestRank == null || highestRank.compareTo(kicker) < 0) {
                result = 1;
                highestRank = kicker;
            }
        }
        for(Rank kicker : otherTwoPair.kickers) {
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
        return "TwoPair{" +
                "higherPair=" + higherPair +
                ", lowerPair=" + lowerPair +
                '}';
    }
}
