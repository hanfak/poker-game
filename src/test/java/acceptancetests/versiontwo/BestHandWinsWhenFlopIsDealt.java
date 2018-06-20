package acceptancetests.versiontwo;


import com.googlecode.yatspec.junit.SpecRunner;
import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.deck.DealtCards;
import com.hanfak.domain.game.Player;
import com.hanfak.domain.game.PlayerResult;
import com.hanfak.wiring.PokerGame;
import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.hanfak.domain.game.Player.player;
import static testinfrastructure.CardsExamples.*;
import static testinfrastructure.DrawnCardsHandExamples.PLAYER_WITH_ACE_OF_SPADES_AND_FOUR_OF_DIAMONDS;
import static testinfrastructure.DrawnCardsHandExamples.PLAYER_WITH_TEN_OF_SPADES_AND_KING_OF_DIAMONDS;

@RunWith(SpecRunner.class)
public class BestHandWinsWhenFlopIsDealt implements WithAssertions {
    
    @Test
    public void playerWinsWithBetterHandAfterAllCardsHaveBeenPlayed() throws Exception {
        givenADeckDealsOutASetOfRandomCardsToTwoPlayers();

        whenOneCompleteGameIsPlayed();

        thenPlayerOneHasLost();
        andPlayerTwoHasWon();
    }

    private void whenOneCompleteGameIsPlayed() {
        play = pokerGame.play(cardDealer, playerOne, playerTwo);
    }

    private void givenADeckDealsOutASetOfRandomCardsToTwoPlayers() {
        org.mockito.Mockito.when(cardDealer.dealHand(5)).
                thenReturn(new DealtCards(PLAYER_WITH_TEN_OF_SPADES_AND_KING_OF_DIAMONDS)).
                thenReturn(new DealtCards(PLAYER_WITH_ACE_OF_SPADES_AND_FOUR_OF_DIAMONDS)).
                thenReturn(new DealtCards(Arrays.asList(ACE_OF_HEARTS, KING_OF_HEARTS, SIX_OF_HEARTS))).
                thenReturn(new DealtCards(Collections.singletonList(TWO_OF_SPADES))).
                thenReturn(new DealtCards(Collections.singletonList(THREE_OF_CLUBS)));
    }

    @SuppressWarnings("ConstantConditions")
    private void thenPlayerOneHasLost() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player One".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(2);
        assertThat(first.get().playerName).isEqualTo("Player One");

    }

    @SuppressWarnings("ConstantConditions")
    private void andPlayerTwoHasWon() {
        Optional<PlayerResult> first = play.stream().filter(playerResult -> "Player Two".equals(playerResult.playerName)).findFirst();
        assertThat(first.get().result).isEqualTo(1);
        assertThat(first.get().playerName).isEqualTo("Player Two");
    }

    private final CardDealer cardDealer = Mockito.mock(CardDealer.class);

    private static final String VERSION = "1.0";
    private static final Player playerOne = player("Player One");
    private static final Player playerTwo = player("Player Two");

    private List<PlayerResult> play;
    private PokerGame pokerGame;

    @Before
    public void setUp() throws Exception {
        pokerGame = new PokerGame(VERSION);
    }
}
