package com.hanfak.usecases.versiontwo;

import com.hanfak.domain.deck.CardDealer;
import com.hanfak.domain.deck.DealtCards;
import com.hanfak.domain.game.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanfak.domain.game.Player.player;

public class InitialHandUseCase {
    private CardDealer cardDealer;

    public InitialHandUseCase(CardDealer cardDealer) {
        this.cardDealer = cardDealer;
    }

    public List<Player> dealCards(Player[] players) {
        List<Player> playersWithAHandOfCards = dealHandToAllPlayers(players);
        return null;
    }

    private List<Player> dealHandToAllPlayers(Player[] players) {
        return Arrays.stream(players).map(this::dealHand).collect(Collectors.toList());
    }

    private Player dealHand(Player player) {
        DealtCards dealtCards = cardDealer.dealHand(2);
        return player(player.playerName, dealtCards.getCards(), null);
    }
}
