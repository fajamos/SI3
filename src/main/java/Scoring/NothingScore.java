package Scoring;

import GameModel.Players.Player;
import GameModel.State;

public class NothingScore implements GameScoring {
    @Override
    public double getScoreFor(Player player, Player enemy, State state) {
        return 0;
    }
}
