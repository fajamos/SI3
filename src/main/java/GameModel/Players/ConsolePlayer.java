package GameModel.Players;

import GameModel.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ConsolePlayer extends Player {

    private static final String INPUT_COORD_INSTRUCTION = "Select position by:\n\t<x><y> from 0 to 6\n\t<ring><y><x> from 0 to 2. 0 - Outer ring\t2 - Inner ring";
    private static final String INPUT_WRONG_COORD = "Invalid position.\n" + INPUT_COORD_INSTRUCTION;
    private static final String INPUT_SELECT_PAWN = "Select pawn at position: ";
    private static final String INPUT_SELECT_PAWN_WRONG = "There is no yours pawn at this postion";
    private static final String INPUT_MOVE_PAWN = "Select position to move or type 'cancel' to select another: ";
    private static final String INPUT_MOVE_PAWN_POSSIBLE_MOVES = "Possible moves for this pawn: ";
    private static final String INPUT_MOVE_PAWN_NO_POSSIBLE_MOVES = "No possible moves for this pawn. Type cancel to select another pawn";
    private static final String INPUT_MOVE_PAWN_ILLEGAL = "Illegal position to move";
    private static final String INPUT_MOVE_TAKEN = "This position is taken";
    private static final String INPUT_PLACE = "Select position to place pawn: ";
    private static final String INPUT_PLACE_TAKEN = INPUT_MOVE_TAKEN;
    private static final String INPUT_REMOVE = "Select enemy pawn to remove at position: ";
    private static final String INPUT_REMOVE_WRONG = "Select enemy pawn to remove ";

    private BufferedReader reader;

    public ConsolePlayer(int PawnsLeftToSet, List<Pawn> pawns, int pawnsToRemove) {
        super(PawnsLeftToSet, pawns, pawnsToRemove);
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public Turn movePawn(State state) {
        System.out.println(INPUT_SELECT_PAWN);
        Coord pos = null;
        Coord to = null;
        while(pos == null){
            try {
                pos = readCoord(reader.readLine());
                if(pos==null){
                    System.out.println(INPUT_WRONG_COORD);
                } else{
                    Pawn tempPawm = state.getGameTable().get(pos);
                    if (tempPawm==null || tempPawm.getPlayer()!=this){
                        pos = null;
                        System.out.println(INPUT_SELECT_PAWN_WRONG);
                    }
                }
            } catch (IOException e) {
                System.out.println(INPUT_WRONG_COORD);
            }
        }
        System.out.println(INPUT_MOVE_PAWN);
        System.out.print(INPUT_MOVE_PAWN_POSSIBLE_MOVES);
        Pawn pawn = state.getGameTable().get(pos);
        List<Turn> possibleMoves = state.possibleMoveTurns(pawn);
        if(possibleMoves.isEmpty()){
            System.out.print(INPUT_MOVE_PAWN_NO_POSSIBLE_MOVES);
        }
        for(Turn t : possibleMoves){
            System.out.print(coordToString(t.getTo()) + ", ");
        }
        while(to == null){
            try {
                String command = reader.readLine();
                if(command.toLowerCase().equals("cancel")) return movePawn(state);
                to = readCoord(command);
                if(to==null){
                    System.out.println(INPUT_WRONG_COORD);
                } else if (!coordsInTurns(possibleMoves,pos,to)){
                    to = null;
                    System.out.println(INPUT_MOVE_PAWN_ILLEGAL);
                }
            } catch (IOException e) {
                System.out.println(INPUT_WRONG_COORD);
            }
        }
        return new Turn(Turn.TurnType.MOVE,this,pos,to);

    }

    public Turn placePawn(State state) {
        System.out.println(INPUT_PLACE);
        Coord coord = null;
        while(coord==null){
            try {
                coord = readCoord(reader.readLine());
                if(coord==null){
                    System.out.println(INPUT_WRONG_COORD);
                } else if(state.getGameTable().get(coord)!=null){
                    System.out.println(INPUT_PLACE_TAKEN);
                    coord = null;
                }
            } catch (IOException e) {
                System.out.println(INPUT_WRONG_COORD);
            }
        }
        return new Turn(Turn.TurnType.PLACE,this,coord,null);
    }

    @Override
    public Player getClone(Player player, List<Pawn> pawns) {
        return new ConsolePlayer(player.pawnsLeftToSet,pawns,player.pawnsToRemove);
    }

    public Turn removePawn(State state) {
        System.out.println(INPUT_REMOVE);
        Coord coord = null;
        while(coord==null){
            try {
                coord = readCoord(reader.readLine());
                if(coord==null){
                    System.out.println(INPUT_WRONG_COORD);
                } else {
                    Pawn pawn = state.getGameTable().get(coord);
                    if(pawn==null || pawn.getPlayer()==this){
                        System.out.println(INPUT_REMOVE_WRONG);
                        coord=null;
                    }
                }
            } catch (IOException e) {
                System.out.println(INPUT_WRONG_COORD);
            }
        }
        return new Turn(Turn.TurnType.REMOVE,this,coord,null);
    }

    @Override
    public Turn getTurn(State state) {
        if(pawnsToRemove > 0){
            return removePawn(state);
        }
        if(pawnsLeftToSet >0){
            return placePawn(state);
        } else{
            return movePawn(state);
        }
    }

    private Coord readCoord(String string){
        if(string.length()==2){
            return parseToCoord(string);
        }
        else if(string.length()==3){
            int ring = Integer.parseInt(string.substring(0,1));
            int y = Integer.parseInt(string.substring(1,2));
            int x = Integer.parseInt(string.substring(2,3));
            if(x <0 || x > 2 || y < 0 || y > 2 || ring < 0 || ring > 2 || (x==1 && y == 1)) return null;
            return new Coord(ring,x,y);
        }
        else return null;
    }
    private Coord parseToCoord(String string){
        int x = Integer.parseInt(string.substring(0,1));
        int y = Integer.parseInt(string.substring(1));
        if(x <0 || x > 6 || y < 0 || y > 6) return null;
        switch(x){
            case 0:
                switch(y){
                    case 0: return new Coord(GameTable.OUTER,0,0);
                    case 3: return new Coord(GameTable.OUTER,0,1);
                    case 6: return new Coord(GameTable.OUTER,0,2);
                    default: return null;
                }
            case 1:
                switch(y){
                    case 1: return new Coord(GameTable.MIDDLE,0,0);
                    case 3: return new Coord(GameTable.MIDDLE,0,1);
                    case 5: return new Coord(GameTable.MIDDLE,0,2);
                    default: return null;
                }
            case 2:
                switch(y){
                    case 2: return new Coord(GameTable.INNER,0,0);
                    case 3: return new Coord(GameTable.INNER,0,1);
                    case 4: return new Coord(GameTable.INNER,0,2);
                    default: return null;
                }
            case 3:
                switch(y){
                    case 0: return new Coord(GameTable.OUTER,1,0);
                    case 1: return new Coord(GameTable.MIDDLE,1,0);
                    case 2: return new Coord(GameTable.INNER,1,0);
                    case 4: return new Coord(GameTable.INNER,1,2);
                    case 5: return new Coord(GameTable.MIDDLE,1,2);
                    case 6: return new Coord(GameTable.OUTER,1,2);
                    default: return null;
                }
            case 4:
                switch(y){
                    case 2: return new Coord(GameTable.INNER,2,0);
                    case 3: return new Coord(GameTable.INNER,2,1);
                    case 4: return new Coord(GameTable.INNER,2,2);
                    default: return null;
                }
            case 5:
                switch(y){
                    case 1: return new Coord(GameTable.MIDDLE,2,0);
                    case 3: return new Coord(GameTable.MIDDLE,2,1);
                    case 5: return new Coord(GameTable.MIDDLE,2,2);
                    default: return null;
                }
            case 6:
                switch(y){
                    case 0: return new Coord(GameTable.OUTER,2,0);
                    case 3: return new Coord(GameTable.OUTER,2,1);
                    case 6: return new Coord(GameTable.OUTER,2,2);
                    default: return null;
                }
            default: return null;
        }
    }
    private String coordToString(Coord coord){
        return String.format("%d%d%d",coord.getRing(),coord.getY(),coord.getX());
    }
    private boolean coordsInTurns(List<Turn> turns, Coord from, Coord to){
        for(Turn t : turns){
            if(t.getType() != Turn.TurnType.MOVE) throw new IllegalArgumentException();
            if (from.equals(t.getCoord()) && to.equals(t.getTo())) return  true;
        }
        return false;
    }
}
