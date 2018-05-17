package testinfrastructure;

import com.hanfak.domain.cards.Card;

import java.util.Arrays;
import java.util.List;

import static testinfrastructure.CardsExamples.*;

// TODO use interface???
public class TwoCardHandsExamples {
    //THREE_OF_DIAMONDS, KING_OF_DIAMONDS, NINE_OF_DIAMONDS
    public static final List<Card> PLAYER_WITH_TWO_CARDS_FOR_FLUSH_ONE = Arrays.asList(FIVE_OF_DIAMONDS, JACK_OF_DIAMONDS);
    public static final List<Card> PLAYER_WITH_TWO_CARDS_FOR_HIGH_CARD_ONE = Arrays.asList(ACE_OF_SPADES, TWO_OF_HEARTS);

}
