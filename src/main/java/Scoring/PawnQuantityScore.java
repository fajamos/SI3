package Scoring;

import GameModel.GameTable;
import GameModel.Players.Player;
import GameModel.State;

public class PawnQuantityScore implements GameScoring {
    public double getScoreFor(Player player, Player enemy, State state) {
        if(state.getWinner()==player) return Double.POSITIVE_INFINITY;
        if(state.getWinner()==enemy) return Double.NEGATIVE_INFINITY;
        return player.totalPawns(enemy)-enemy.totalPawns(player);
    }
}
