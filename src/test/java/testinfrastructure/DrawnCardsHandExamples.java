package testinfrastructure;

import com.hanfak.domain.cards.Card;

import java.util.Arrays;
import java.util.List;

import static testinfrastructure.CardsExamples.ACE_OF_SPADES;
import static testinfrastructure.CardsExamples.FOUR_OF_DIAMONDS;
import static testinfrastructure.CardsExamples.KING_OF_DIAMONDS;
import static testinfrastructure.CardsExamples.TEN_OF_SPADES;

public class DrawnCardsHandExamples {

    public static final List<Card> PLAYER_WITH_ACE_OF_SPADES_AND_FOUR_OF_DIAMONDS = Arrays.asList(ACE_OF_SPADES, FOUR_OF_DIAMONDS);
    public static final List<Card> PLAYER_WITH_TEN_OF_SPADES_AND_KING_OF_DIAMONDS = Arrays.asList(KING_OF_DIAMONDS, TEN_OF_SPADES);


}


