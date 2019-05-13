package GameProcess;

import GameModel.Players.AIPlayer;
import GameModel.State;
import GameModel.Turn;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
public class AlphaBetaNode extends MinMaxNode {
    private Double alpha;
    private Double beta;

    public AlphaBetaNode(AlphaBetaNode parent, Turn turn, int depth, Double alpha, Double beta) {
        super(parent, turn, depth);
        this.alpha = new Double(alpha);
        this.beta = new Double(beta);
    }

    public AlphaBetaNode(State state, AIPlayer player, int depth) {
        super(state, player, depth);
        alpha = Double.NEGATIVE_INFINITY;
        beta = Double.POSITIVE_INFINITY;
    }

    @Override
    public double getScore() {
        if(score!= null) return score;
        if(depthLeft==0 || state.getWinner()!=null){
            setScore(currentPlayer().getScoring().getScoreFor(currentPlayer(),currentEnemy(),state));
        } else {
            List<Turn> turns = state.possibleTurns();
            int childDepth = turns.get(0).getType()== Turn.TurnType.REMOVE ? depthLeft : depthLeft-1;
            for(Turn t : turns){
                AlphaBetaNode child = new AlphaBetaNode(this,t,childDepth,alpha,beta);
                children.add(child);
                child.setScore(child.getScore());

                if (currentPlayerNumber != playerNumber) {
                    alpha = Math.max(alpha, child.getScore());//max
                    if(beta<=alpha){
                        setScore(alpha);
                        return alpha;
                    }
                } else {
                    beta = Math.min(beta, child.getScore()); //min
                    if(beta<=alpha) {
                        setScore(beta);
                        return beta;
                    }
                }
            }
            if (currentPlayerNumber != playerNumber) {
                setScore(Collections.max(children, Comparator.comparing(MinMaxNode::getScore)).getScore());
            } else {
                setScore(Collections.min(children, Comparator.comparing(MinMaxNode::getScore)).getScore());
            }

        }
        return score;
    }
}
