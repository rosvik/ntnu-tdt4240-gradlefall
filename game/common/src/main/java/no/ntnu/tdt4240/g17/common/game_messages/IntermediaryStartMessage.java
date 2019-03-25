package no.ntnu.tdt4240.g17.common.game_messages;

import no.ntnu.tdt4240.g17.common.game_helpers.Player;

import java.util.List;
import java.util.Map;

public class IntermediaryStartMessage {
    public List<Player> players;
    public Map<String,Integer> newScores;
    public Integer roundNumber;
    public String nextArena;
    public String gameMode;
}
