package GameProcess;

import GameModel.Players.AIPlayer;
import GameModel.Players.Player;
import GameModel.State;
import GameModel.Turn;
import lombok.Getter;

import java.util.*;

@Getter
public class MinMaxNode {
    protected State state;
    protected Turn turn;
    protected MinMaxNode parent;
    protected Double score;
    protected AIPlayer player;
    protected ArrayList<MinMaxNode> children;
    protected int depthLeft;
    protected int playerNumber;
    protected final int currentPlayerNumber;

    public MinMaxNode(MinMaxNode parent, Turn turn, int depth){
        this.parent = parent;
        children = new ArrayList<>();
        state = new State(parent.state);
        this.turn = turn;
        player = (AIPlayer) (parent.player==parent.state.getPlayer1() ? state.getPlayer1() : state.getPlayer2());
        playerNumber = parent.playerNumber;
        currentPlayerNumber = state.getCurrentPlayerNumber();
        state.playTurn(turn);
        depthLeft = depth;
    }

    public MinMaxNode(State state, AIPlayer player, int depth){
        parent = null;
        this.state = new State(state);
        this.player = player;
        playerNumber = player==state.getPlayer1() ? 1 : 2;
        currentPlayerNumber = playerNumber;
        children = new ArrayList<>();
        depthLeft = depth;
    }

    public Turn getTurn(){
        getScore();
//        for(MinMaxNode child: children){
//            System.out.println(child.getScore());
//        }
        //Collections.shuffle(children);
        return Collections.max(children, Comparator.comparing(MinMaxNode::getScore)).turn;
    }



    protected void setScore(double score){
        this.score = score;
        state = null;
        for(MinMaxNode child : children){
            child.children = null;
        }
        player = null;
        parent = null;
    }

    public double getScore(){
        if(score!= null) return score;
        if(depthLeft==0 || state.getWinner()!=null){
            setScore(currentPlayer().getScoring().getScoreFor(currentPlayer(),currentEnemy(),state));
        } else {
            List<Turn> turns = state.possibleTurns();
            int childDepth = turns.get(0).getType()== Turn.TurnType.REMOVE ? depthLeft : depthLeft-1;
            for(Turn t : turns){
                MinMaxNode child = new MinMaxNode(this,t,childDepth);
                children.add(child);
                child.setScore(child.getScore());
            }
            //setScore(sum(children)); //DEBUG
            if (currentPlayerNumber != playerNumber) {
                setScore(Collections.max(children, Comparator.comparing(MinMaxNode::getScore)).getScore());
            } else {
                setScore(Collections.min(children, Comparator.comparing(MinMaxNode::getScore)).getScore());
            }

        }
        return score;
    }

    private double sum (Collection<MinMaxNode> c){
        double result = 0;
        for(MinMaxNode n : c){
            result+=n.getScore();
        }
        return (result*depthLeft)/c.size();
    }

    private Player enemy(){
        return player==state.getPlayer1() ? state.getPlayer2() : state.getPlayer1();
    }
    protected AIPlayer currentPlayer(){
        return (AIPlayer) (playerNumber==1 ? state.getPlayer1() : state.getPlayer2());
    }
    protected Player currentEnemy(){
        return playerNumber==1 ? state.getPlayer2() : state.getPlayer1();
    }

}
