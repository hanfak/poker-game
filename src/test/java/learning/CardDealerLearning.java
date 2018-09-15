package learning;

import com.hanfak.domain.deck.CardDealer;
import com.hanfak.infrastructure.CollectionsCardShuffler;

import java.util.stream.IntStream;

public class CardDealerLearning {

    public static void main(String[] args) {
        CardDealer cardDealer = new CardDealer(new CollectionsCardShuffler());
        IntStream.range(1,13).forEach(x -> {
//                    System.out.println("deck size before " + cardDealer.deck.cardsInDeck.size());
//                    Hand hand = cardDealer.dealHand(5);

//                    System.out.println("hand = " + hand.cards);
//                    System.out.println("deck size after " + cardDealer.deck.cardsInDeck.size());
                }

        );

    }
}
