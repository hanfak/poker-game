package learning;

import com.hanfak.domain.Card;
import com.hanfak.domain.Rank;
import com.hanfak.domain.Suit;

import java.util.Arrays;
import java.util.List;

import static com.hanfak.domain.Card.card;

public class CardsLearning {
    public static void main(String[] args) {
        Card card1 = card(Rank.TWO, Suit.CLUB);
        Card card2 = card(Rank.ACE, Suit.CLUB);
        Card card3 = card(Rank.FIVE, Suit.CLUB);

        List<Card> cards = Arrays.asList(card1, card2, card3);
        System.out.println(card3.rank.getLevelCode()); // for ordering and comparing cards

        Arrays.stream(Rank.values()).forEach(System.out::println);

    }
}
