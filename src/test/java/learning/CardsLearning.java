package learning;

import com.hanfak.domain.cards.Card;
import com.hanfak.domain.cards.Rank;
import com.hanfak.domain.cards.Suit;

import java.util.Arrays;
import java.util.List;

import static com.hanfak.domain.cards.Card.card;

public class CardsLearning {
    public static void main(String[] args) {
        Card card1 = card(Rank.TWO, Suit.CLUB);
        Card card2 = card(Rank.ACE, Suit.CLUB);
        Card card3 = card(Rank.FIVE, Suit.CLUB);

        List<Card> cards = Arrays.asList(card1, card2, card3);

        Arrays.stream(Rank.values()).forEach(System.out::println);

    }
}
