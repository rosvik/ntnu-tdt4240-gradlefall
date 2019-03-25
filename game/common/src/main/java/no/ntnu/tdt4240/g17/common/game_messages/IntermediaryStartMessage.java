package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_helpers.Player;

import java.util.ArrayList;
import java.util.Dictionary;

public class IntermediaryStartMessage {
    public ArrayList<Player> players;
    public Dictionary newScores;
    public Integer roundNumber;
    public String nextArena;
    public String gameMode;
}
