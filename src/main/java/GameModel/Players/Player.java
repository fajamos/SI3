package GameModel.Players;

import GameModel.Pawn;
import GameModel.State;
import GameModel.Turn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public abstract class Player {
    protected int pawnsLeftToSet = 0;
    protected int pawnsToRemove = 0;
    protected List<Pawn> pawns;
    protected long onTime = 0;
    public abstract Player getClone(Player player, List<Pawn> pawns);

    public Player(int pawnsLeftToSet, List<Pawn> pawns, int pawnsToRemove){
        this.pawnsLeftToSet = pawnsLeftToSet;
        this.pawns = pawns;
        this.pawnsToRemove = pawnsToRemove;

    }
    public Turn getTurnTimed(State state){
        long current = new Date().getTime();
        Turn turn = getTurn(state);
        onTime += new Date().getTime()-current;
        return turn;
    }

    public void addPawnsToRemove(int i){
        pawnsToRemove+=i;
    }

    public int totalPawns(){
        return pawns.size()+pawnsLeftToSet;
    }
    public int totalPawns(Player enemy){
        return pawns.size()+pawnsLeftToSet - enemy.getPawnsToRemove();
    }

    public void removePawnToPlace(){
        pawnsLeftToSet--;
    }
    public abstract Turn getTurn(State state);


}

