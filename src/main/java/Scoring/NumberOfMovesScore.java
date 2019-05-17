package Scoring;

import GameModel.GameTable;
import GameModel.Players.Player;
import GameModel.State;

public class NumberOfMovesScore implements GameScoring {
    @Override
    public double getScoreFor(Player player, Player enemy, State state) {
        if(state.getWinner()==player) return Double.POSITIVE_INFINITY;
        if(state.getWinner()==enemy) return Double.NEGATIVE_INFINITY;
        return player.getPawns().size()>3 && enemy.getPawns().size()>3 ? ((state.possibleMoveTurns(player).size() + 0.01)/(state.possibleMoveTurns(enemy).size()+0.01)) : player.totalPawns()-enemy.totalPawns();
    }
}
