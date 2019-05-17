package GameModel.Players;

import GameModel.Pawn;
import GameModel.State;
import GameModel.Turn;
import GameProcess.AlphaBetaNode;
import GameProcess.MinMaxNode;
import Scoring.GameScoring;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AIPlayer extends Player {

    protected GameScoring scoring;
    protected int turnDepth;
    protected final boolean alphaBeta;

    public AIPlayer(int pawnsLeftToSet, List<Pawn> pawns, GameScoring scoring, int turnDepth, int pawnsToRemove, boolean alphaBeta) {
        super(pawnsLeftToSet, pawns,pawnsToRemove);
        this.scoring = scoring;
        this.turnDepth = turnDepth;
        this.alphaBeta = alphaBeta;
    }

    public Player getClone(Player player, List<Pawn> pawns) {
        AIPlayer p = (AIPlayer) player;

        return new AIPlayer(p.pawnsLeftToSet,pawns,p.scoring,p.turnDepth,player.pawnsToRemove,p.alphaBeta);
    }

    public Turn getTurn(State state) {
        return alphaBeta ? new AlphaBetaNode(state,this,turnDepth).getTurn() : new MinMaxNode(state,this,turnDepth).getTurn();
    }
}
