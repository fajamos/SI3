package Scoring;


import GameModel.Players.Player;
import GameModel.State;

public class NumOfMovesPawnQuantityScore implements GameScoring{

    private static final int turnsPar = 1;
    private static final int pawnsPar = 1;

    @Override
    public double getScoreFor(Player player, Player enemy, State state) {
        if(state.getWinner()==player) return Double.POSITIVE_INFINITY;
        if(state.getWinner()==enemy) return Double.NEGATIVE_INFINITY;
        return (player.getPawns().size()>3 || enemy.getPawns().size()>3 ? turnsPar : 0)*(
                    (double)(state.possibleMoveTurns(player).size())
                    /(state.possibleMoveTurns(enemy).size()))
                + pawnsPar*(-1* ((1.0/player.totalPawns()) - (1.0/enemy.totalPawns())));
    }
}

