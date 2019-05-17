package GameProcess;

import GameModel.Players.AIPlayer;
import GameModel.State;
import GameModel.Turn;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Getter
public class AlphaBetaNode extends MinMaxNode {
    private Double alpha;
    private Double beta;

    public AlphaBetaNode(AlphaBetaNode parent, Turn turn, int depth, Double alpha, Double beta) {
        super(parent, turn, depth);
        this.alpha = alpha;
        this.beta = beta;
    }

    public AlphaBetaNode(State state, AIPlayer player, int depth) {
        super(state, player, depth);
        alpha = Double.NEGATIVE_INFINITY;
        beta = Double.POSITIVE_INFINITY;
    }

    @Override
    public double getScore() {
        if(score!= null) return score;
        List<Turn> turns = state.possibleTurns();
        if(depthLeft==0 || turns.size()==0){
            setScore(currentPlayer().getScoring().getScoreFor(playerNumber==1 ? state.getPlayer1() : state.getPlayer2(),playerNumber==1 ? state.getPlayer2() : state.getPlayer1(),state));
        } else {
            if(alpha == Double.NEGATIVE_INFINITY && beta == Double.POSITIVE_INFINITY) Collections.shuffle(turns);
            int childDepth = turns.get(0).getType()== Turn.TurnType.REMOVE ? depthLeft : depthLeft-1;
            for(Turn t : turns){
                AlphaBetaNode child = new AlphaBetaNode(this,t,childDepth,alpha,beta);
                children.add(child);
                child.setScore(child.getScore());

                if (currentPlayerNumber == playerNumber) {
                    alpha = Math.max(alpha, child.getScore());//max
                    if(beta<alpha){
                        children.remove(child);
                        setScore(alpha);
                        score=null;
                        return alpha;
                    }
                } else {
                    beta = Math.min(beta, child.getScore()); //min
                    if(beta<alpha) {
                        children.remove(child);
                        setScore(beta);
                        score=null;
                        return beta;
                    }
                }
            }
            if (currentPlayerNumber == playerNumber) {
                setScore(Collections.max(children, Comparator.comparing(MinMaxNode::getScore)).getScore());
                //System.out.println("max" + depthLeft);
            } else {
                setScore(Collections.min(children, Comparator.comparing(MinMaxNode::getScore)).getScore());
                //System.out.println("Min" + depthLeft);
            }
        }
        children.removeIf(child -> child.score == null);
        return score;
    }
}
