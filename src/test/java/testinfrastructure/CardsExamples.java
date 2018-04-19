package testinfrastructure;

import com.hanfak.domain.cards.Card;

import static com.hanfak.domain.cards.Card.card;
import static com.hanfak.domain.cards.Rank.*;
import static com.hanfak.domain.cards.Suit.CLUB;
import static com.hanfak.domain.cards.Suit.DIAMOND;
import static com.hanfak.domain.cards.Suit.HEART;
import static com.hanfak.domain.cards.Suit.SPADE;

// TODO use interface???
public class CardsExamples {
    public static final Card ACE_OF_SPADES = card(ACE, SPADE);
    public static final Card ACE_OF_HEARTS = card(ACE, HEART);
    public static final Card ACE_OF_DIAMONDS = card(ACE, DIAMOND);

    public static final Card KING_OF_SPADES = card(KING, SPADE);
    public static final Card KING_OF_HEARTS = card(KING, HEART);
    public static final Card KING_OF_DIAMONDS = card(KING, DIAMOND);

    public static final Card QUEEN_OF_SPADES = card(QUEEN, SPADE);
    public static final Card QUEEN_OF_HEARTS = card(QUEEN, HEART);

    public static final Card JACK_OF_DIAMONDS = card(JACK, DIAMOND);
    public static final Card JACK_OF_SPADES = card(JACK, SPADE);

    public static final Card TEN_OF_DIAMONDS = card(TEN, DIAMOND);
    public static final Card TEN_OF_SPADES = card(TEN, SPADE);

    public static final Card NINE_OF_DIAMONDS = card(NINE, DIAMOND);
    public static final Card NINE_OF_CLUBS = card(NINE, CLUB);
    public static final Card NINE_OF_SPADES = card(NINE, SPADE);

    public static final Card EIGHT_OF_SPADES = card(EIGHT, SPADE);
    public static final Card EIGHT_OF_DIAMONDS = card(EIGHT, DIAMOND);
    public static final Card EIGHT_OF_CLUBS = card(EIGHT, CLUB);
    public static final Card EIGHT_OF_HEARTS = card(EIGHT, HEART);

    public static final Card SEVEN_OF_DIAMONDS = card(SEVEN, DIAMOND);
    public static final Card SEVEN_OF_SPADES = card(SEVEN, SPADE);

    public static final Card SIX_OF_HEARTS = card(SIX, HEART);
    public static final Card SIX_OF_SPADES = card(SIX, SPADE);


    public static final Card FIVE_OF_SPADES = card(FIVE, SPADE);
    public static final Card FIVE_OF_HEARTS = card(FIVE, HEART);
    public static final Card FIVE_OF_DIAMONDS = card(FIVE, DIAMOND);
    public static final Card FIVE_OF_CLUBS = card(FIVE, CLUB);

    public static final Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
    public static final Card FOUR_OF_SPADES = card(FOUR, SPADE);
    public static final Card FOUR_OF_HEARTS = card(FOUR, HEART);
    public static final Card FOUR_OF_CLUBS = card(FOUR, CLUB);

    public static final Card THREE_OF_SPADES = card(THREE, SPADE);
    public static final Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);

    public static final Card TWO_OF_HEARTS = card(TWO, HEART);
    public static final Card TWO_OF_SPADES = card(TWO, SPADE);
}
