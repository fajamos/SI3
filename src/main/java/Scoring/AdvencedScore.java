package Scoring;

import GameModel.Players.Player;
import GameModel.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvencedScore implements GameScoring {

    private double pawnPar;
    private double movePar;
    private double millPar;

    public AdvencedScore(double pawnPar, double movePar, double millPar) {
        this.pawnPar = pawnPar;
        this.movePar = movePar;
        this.millPar = millPar;
    }

    public AdvencedScore() {
        pawnPar = 2.2;
        movePar = 1.4;
        millPar = 0.7;
    }
    public AdvencedScore(int depth) {
        switch(depth){
            case 1:
                pawnPar = 4.5;
                movePar = 1.56;
                millPar = 2.8;
                break;
            case 2:
                pawnPar = 7.4;
                movePar = 1.9;
                millPar = 5.8;
                break;
            case 3:
                pawnPar = 2.58;
                movePar = 1.08;
                millPar = 2.9;
                break;
            case 4:
                pawnPar = 3.8;
                movePar = 2.8;
                millPar = 4.1;
                break;
            case 5:
                pawnPar = 2.58;
                movePar = 1.08;
                millPar = 2.9;
                break;
            case 6:
                pawnPar = 3.8;
                movePar = 2.8;
                millPar = 4.1;
                break;

        }

    }

    @Override
    public double getScoreFor(Player player, Player enemy, State state) {
        if(state.getWinner()==player) return Double.POSITIVE_INFINITY;
        if(state.getWinner()==enemy) return Double.NEGATIVE_INFINITY;
        double pawnScore = player.totalPawns() - enemy.totalPawns();
        double movesScore = player.getPawns().size()>3 && enemy.getPawns().size()>3 ? 9*((state.possibleMoveTurns(player).size() + 0.01)/(state.possibleMoveTurns(enemy).size()+0.01)) : 0;
        double almostMills = state.getGameTable().checkAlmostMill(player);
        return pawnScore* + movesScore*5 + almostMills;

    }

    public void mutate(double prob,double maxMutate){
        if(Math.random()<=prob){
            pawnPar += pawnPar* ((Math.random()*maxMutate) - (maxMutate/2));
        }
        if(Math.random()<=prob){
            millPar += millPar* ((Math.random()*maxMutate) - (maxMutate/2));
        }
        if(Math.random()<=prob){
            movePar += movePar* ((Math.random()*maxMutate) - (maxMutate/2));
        }
    }

    public AdvencedScore cross(AdvencedScore other){
        AdvencedScore child = new AdvencedScore();
        int randPawn = (int) (Math.random()*3);
        int randMove = (int) (Math.random()*3);
        int randMill = (int) (Math.random()*3);
        if(randPawn==0) child.pawnPar=pawnPar;
        if(randPawn==1) child.pawnPar=other.pawnPar;
        if(randPawn==2) child.pawnPar= (Math.random()*(pawnPar-other.pawnPar)) + other.pawnPar;
        if(randMove==0) child.movePar=movePar;
        if(randMove==1) child.movePar=other.movePar;
        if(randMove==2) child.movePar= (Math.random()*(movePar-other.movePar)) + other.movePar;
        if(randMill==0) child.millPar=millPar;
        if(randMill==1) child.millPar=other.millPar;
        if(randMill==2) child.millPar= (Math.random()*(millPar-other.millPar)) + other.millPar;
        return child;
    }
}
