package GameModel;

import GameModel.Players.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class State {
    private final GameTable gameTable;
    private final Player player1;
    private final Player player2;
    private Player turnOfPlayer;
    private Player winner = null;

    public State(Player p1, Player p2){
        player1 = p1;
        player2 = p2;
        turnOfPlayer = p1;
        gameTable = new GameTable();
    }

    public State(State other){
        ArrayList<Pawn> player1Pawns = new ArrayList<Pawn>(other.player1.getPawns().size());
        for(Pawn p : other.player1.getPawns()){
            player1Pawns.add(new Pawn(p));
        }
        ArrayList<Pawn> player2Pawns = new ArrayList<Pawn>(other.player2.getPawns().size());
        for(Pawn p : other.player2.getPawns()){
            player2Pawns.add(new Pawn(p));
        }
        player1 = other.player1.getClone(other.player1,player1Pawns);
        for(Pawn p : player1.getPawns()){
            p.setPlayer(player1);
        }
        player2 = other.player2.getClone(other.player2,player2Pawns);
        for(Pawn p : player2.getPawns()){
            p.setPlayer(player2);
        }
        turnOfPlayer = other.getTurnOfPlayer()==other.getPlayer1() ? player1 : player2;
        gameTable = new GameTable(player1Pawns,player2Pawns);
    }

    public void placePawn(Player player, Coord coord){
        Pawn pawn = new Pawn(player);
        pawn.setPosition(coord);
        player.getPawns().add(pawn);
        gameTable.movePawn(pawn,coord);
    }

    public void changeTurn(){
        turnOfPlayer = turnOfPlayer==player1 ? player2 : player1;
    }

    public void removePawn(Pawn pawn) {
        gameTable.removePawn(pawn);
        pawn.getPlayer().getPawns().remove(pawn);
    }

    public void movePawn(Pawn pawn, Coord coord){
        gameTable.movePawn(pawn,coord);
        pawn.setLastPosition(pawn.getPosition());
        pawn.setPosition(coord);
    }

    public List<Turn> possibleMoveTurns(Player player){
        List<Turn> result = new ArrayList<Turn>();
        for(Pawn pawn : player.getPawns()){
            result.addAll(possibleMoveTurns(pawn));
        }
        if(player.getPawns().size()+player.getPawnsLeftToSet()<3 || result.size()==0) winner = player==player1 ? player2 : player1;
        return result;
    }

    public List<Turn> possibleMoveTurns(Pawn pawn){
        List<Turn> result = new ArrayList<Turn>();
        if(turnOfPlayer.getPawns().size()==3){
            for(Coord coord : gameTable.freeCoords()){
                if(!coord.equals(pawn.getLastPosition())) result.add(new Turn(Turn.TurnType.MOVE,pawn.getPlayer(),pawn.getPosition(),coord));
            }
        } else {
            for(Coord coord : gameTable.freeNeightbourCoords(pawn.getPosition())){
                if(!coord.equals(pawn.getLastPosition())) result.add(new Turn(Turn.TurnType.MOVE,pawn.getPlayer(),pawn.getPosition(),coord));
            }
        }
        return result;
    }

    public List<Turn> possibleMoveTurns(){
        return possibleMoveTurns(turnOfPlayer);
    }


    public List<Turn> possiblePlaceTurns(Player player){
        List<Turn> result = new ArrayList<Turn>();
        for(Coord coord : gameTable.freeCoords()){
            result.add(new Turn(Turn.TurnType.PLACE,player,coord,null));
        }
        return result;
    }

    public List<Turn> possiblePlaceTurns(){
        return possiblePlaceTurns(turnOfPlayer);
    }

    public List<Turn> possibleRemoveTurns(Player player){
        List<Turn> result = new ArrayList<Turn>();
        for(Pawn pawn : enemy().getPawns()){
            result.add(new Turn(Turn.TurnType.REMOVE,player,pawn.getPosition(),null));
        }
        return result;
    }

    public List<Turn> possibleRemoveTurns(){
        return possibleRemoveTurns(turnOfPlayer);
    }

    public List<Turn> possibleTurns(Player player){
        if(player.getPawnsToRemove()>0){
            return possibleRemoveTurns(player);
        }
        if(player.getPawnsLeftToSet()>0){
            return possiblePlaceTurns(player);
        }
        return possibleMoveTurns(player);
    }

    public List<Turn> possibleTurns(){
        return possibleTurns(turnOfPlayer);
    }

    public void playTurn(){
        Turn turn = turnOfPlayer.getTurnTimed(this);
        playTurn(turn);
    }

    public void playTurn(Turn turn){
        switch (turn.getType()) {
            case PLACE:
                placePawn(turnOfPlayer,turn.getCoord());
                turnOfPlayer.removePawnToPlace();
                turnOfPlayer.addPawnsToRemove(gameTable.checkMill(turn.getCoord(),turnOfPlayer));
                break;
            case MOVE:
                movePawn(getGameTable().get(turn.getCoord()),turn.getTo());
                turnOfPlayer.addPawnsToRemove(gameTable.checkMill(turn.getTo(),turnOfPlayer));
                break;
            case REMOVE:
                removePawn(getGameTable().get(turn.getCoord()));
                turnOfPlayer.addPawnsToRemove(-1);
                break;
        }
        if(turnOfPlayer.getPawnsToRemove()==0) changeTurn();
    }

    public int getCurrentPlayerNumber(){
        return turnOfPlayer==player1 ? 1 : 2;
    }

    public Player getWinner(){
        if(winner!=null) return winner;
        if(player1.totalPawns()<3) {
            winner = player2;
            return winner;
        }
        if(player2.totalPawns()<3) {
            winner = player1;
            return winner;
        }
        if(possibleTurns().isEmpty()){
            winner = enemy();
            return winner;
        }
        return null;
    }


    private Player enemy(){
        return turnOfPlayer == player1 ? player2 : player1;
    }


}
