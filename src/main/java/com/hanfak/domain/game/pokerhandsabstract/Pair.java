package com.hanfak.domain.game.pokerhandsabstract;

import com.hanfak.domain.cards.Rank;

import java.util.List;

public class Pair extends PokerHand {

    private final Rank rank;
    private final List<Rank> kickers;


    public Pair(Rank rank, List<Rank> kickers) {
        super(1);
        this.rank = rank;
        this.kickers = kickers;
    }

    @Override
    public int compareTo(PokerHand pokerHand) {
        if(pokerHand instanceof Pair) {
            final Pair otherPair = (Pair)pokerHand;
            final int comparison = rank.compareTo(otherPair.rank);
            if(comparison == 0) {
                return compareKickers(otherPair);
            }
            return comparison;
        }
        if(ranking < pokerHand.ranking) return -1;
        return 1;
    }

    private int compareKickers(Pair otherPair) {
        int result = 0;
        Rank highestRank = null;
        for (Rank kicker : kickers) {
            if(highestRank == null || highestRank.compareTo(kicker) < 0) {
                result = 1;
                highestRank = kicker;
            }
        }
        for(Rank kicker : otherPair.kickers) {
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
        return "Pair{" +
                "rank=" + rank +
                ", kickers=" + kickers +
                "}\n";
    }
}
