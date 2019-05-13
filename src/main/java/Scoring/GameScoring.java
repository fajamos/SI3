package Scoring;

import GameModel.Players.Player;
import GameModel.State;

public interface GameScoring {
    double getScoreFor(Player player, Player enemy, State state);
}
