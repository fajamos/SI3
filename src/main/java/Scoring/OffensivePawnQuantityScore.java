package Scoring;

import GameModel.Players.Player;
import GameModel.State;

public class OffensivePawnQuantityScore implements GameScoring {
    public double getScoreFor(Player player, Player enemy, State state) {
        if(state.getWinner()==player) return Double.POSITIVE_INFINITY;
        if(state.getWinner()==enemy) return Double.NEGATIVE_INFINITY;
        return -1* ((1.0/(player.totalPawns()-enemy.getPawnsToRemove())) - (2.0/(enemy.totalPawns()-player.getPawnsToRemove())));
    }
}
