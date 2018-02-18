package learning;

import com.hanfak.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.Card.card;
import static com.hanfak.domain.Player.player;
import static com.hanfak.domain.Rank.*;
import static com.hanfak.domain.Suit.DIAMOND;
import static com.hanfak.domain.Suit.SPADE;

public class MulitpleHandEvaluatorLearning {
    private static final Card THREE_OF_SPADES = card(THREE, SPADE);
    private static final Card ACE_OF_SPADES = card(ACE, SPADE);
    private static final Card FOUR_OF_DIAMONDS = card(FOUR, DIAMOND);
    private static final Card FOUR_OF_SPADES = card(FOUR, SPADE);
    private static final Card FIVE_OF_SPADES = card(FIVE, SPADE);

    private static final Card EIGHT_OF_SPADES = card(EIGHT, SPADE);
    private static final Card THREE_OF_DIAMONDS = card(THREE, DIAMOND);
    private static final Card NINE_OF_DIAMONDS = card(NINE, DIAMOND);
    private static final Card JACK_OF_DIAMONDS = card(JACK, DIAMOND);
    private static final Card KING_OF_SPADES = card(KING, SPADE);
    private static final Card QUEEN_OF_SPADES = card(QUEEN, SPADE);
    private static final Card TEN_OF_SPADES = card(TEN, SPADE);


    public static void main(String[] args) {
        MultipleHandEvaluator multipleHandEvaluator = new MultipleHandEvaluator();
        HandEvaluator handEvaluator = new HandEvaluator();
        Hand playersOneHand = Hand.hand(Arrays.asList(TEN_OF_SPADES, FOUR_OF_DIAMONDS, THREE_OF_SPADES, FOUR_OF_SPADES, FIVE_OF_SPADES), null);
        Hand playersTwoHand = Hand.hand(Arrays.asList(EIGHT_OF_SPADES, THREE_OF_DIAMONDS, QUEEN_OF_SPADES, NINE_OF_DIAMONDS, JACK_OF_DIAMONDS), null);
        Player playerone = player("one", playersOneHand);
        Player playertwo = player("two", playersTwoHand);

        List<Player> players = Arrays.asList(playerone, playertwo);
        List<Player> playersWithHandEvaluated = players.stream()
                .map(x -> player(x.playerName, handEvaluator.scoreHand(x.hand)))
                .collect(Collectors.toList());

        List<Player> players1 = multipleHandEvaluator.compareAllPlayersHands(playersWithHandEvaluated);

        System.out.println("players1 = " + players1.stream().map(x -> x.playerName).collect(Collectors.joining(",")));

    }
}
