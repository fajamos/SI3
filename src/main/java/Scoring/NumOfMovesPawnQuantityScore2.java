package Scoring;


import GameModel.Players.Player;
import GameModel.State;

public class NumOfMovesPawnQuantityScore2 implements GameScoring{

    private static final double turnsPar = 1;
    private static final double pawnsPar = 46;

    @Override
    public double getScoreFor(Player player, Player enemy, State state) {
        if(state.getWinner()==player) return Double.POSITIVE_INFINITY;
        if(state.getWinner()==enemy) return Double.NEGATIVE_INFINITY;
//        System.out.println((player.totalPawns()<=3 || enemy.totalPawns()<=3 ? 0 : turnsPar)*
//                (2*state.possibleMoveTurns(player).size()-state.possibleMoveTurns(enemy).size()) + " " + pawnsPar*(player.totalPawns(enemy)-enemy.totalPawns(player)));
        return (player.totalPawns()<=3 || enemy.totalPawns()<=3 ? 0 : turnsPar)*
                (2*state.possibleMoveTurns(player).size()-state.possibleMoveTurns(enemy).size())
                + pawnsPar*(player.totalPawns(enemy)-enemy.totalPawns(player));
    }
}

