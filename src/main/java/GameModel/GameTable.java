package GameModel;

import GameModel.Players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameTable {

    public static final int SIZE = 3;
    public static final int OUTER = 0;
    public static final int MIDDLE = 1;
    public static final int INNER = 2;

    /**
     * 000          001         002
     *      100     101     102
     *          200 201 202
     * 010  110 210     212 112 012
     *          220 221 222
     *      120     121     122
     * 020          021         022
     */

    private Pawn[][] outerRing;
    private Pawn[][] middleRing;
    private Pawn[][] innerRing;

    public GameTable(){
        outerRing = new Pawn[SIZE][SIZE];
        middleRing = new Pawn[SIZE][SIZE];
        innerRing = new Pawn[SIZE][SIZE];
    }

    public GameTable(ArrayList<Pawn> player1Pawns, ArrayList<Pawn> player2Pawns) {
        outerRing = new Pawn[SIZE][SIZE];
        middleRing = new Pawn[SIZE][SIZE];
        innerRing = new Pawn[SIZE][SIZE];
        for(Pawn p : player1Pawns){
            movePawn(p,p.getPosition());
        }
        for(Pawn p : player2Pawns){
            movePawn(p,p.getPosition());
        }
    }

    public Pawn get(Coord coord){
        if(coord.getX()==1 && coord.getY() == 1) throw new IllegalArgumentException("Invalid Coord");
        switch (coord.getRing()){
            case OUTER: return outerRing[coord.getY()][coord.getX()];
            case MIDDLE: return middleRing[coord.getY()][coord.getX()];
            case INNER: return innerRing[coord.getY()][coord.getX()];
            default: throw new IllegalArgumentException("Invalid Coord");
        }
    }

    public List<Coord> freeCoords(){
        ArrayList<Coord> coords = new ArrayList<Coord>();
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                if(!(x==1 && y == 1)) {
                    if (outerRing[y][x] == null) coords.add(new Coord(OUTER, x, y));
                    if (middleRing[y][x] == null) coords.add(new Coord(MIDDLE, x, y));
                    if (innerRing[y][x] == null) coords.add(new Coord(INNER, x, y));
                }
            }
        }
        return coords;
    }

    public List<Coord> freeNeightbourCoords(Coord coord){
        int x = coord.getX();
        int y = coord.getY();
        int ring = coord.getRing();
        Coord workCoord;
        ArrayList<Coord> coords = new ArrayList<Coord>();
        if(x==0 && y==0){
            workCoord = new Coord(ring,x,y+1);
            if(get(workCoord)==null) coords.add(workCoord);
            workCoord = new Coord(ring,x+1,y);
            if(get(workCoord)==null) coords.add(workCoord);
        }
        else if(x==2 && y==0){
            workCoord = new Coord(ring,x,y+1);
            if(get(workCoord)==null) coords.add(workCoord);
            workCoord = new Coord(ring,x-1,y);
            if(get(workCoord)==null) coords.add(workCoord);
        }
        else if(x==0 && y==2){
            workCoord = new Coord(ring,x,y-1);
            if(get(workCoord)==null) coords.add(workCoord);
            workCoord = new Coord(ring,x+1,y);
            if(get(workCoord)==null) coords.add(workCoord);
        }
        else if(x==2 && y==2){
            workCoord = new Coord(ring,x,y-1);
            if(get(workCoord)==null) coords.add(workCoord);
            workCoord = new Coord(ring,x-1,y);
            if(get(workCoord)==null) coords.add(workCoord);
        }
        else{
            if(x==1){
                workCoord = new Coord(ring,x-1,y);
                if(get(workCoord)==null) coords.add(workCoord);
                workCoord = new Coord(ring,x+1,y);
                if(get(workCoord)==null) coords.add(workCoord);
            }
            else if(y==1){
                workCoord = new Coord(ring,x,y+1);
                if(get(workCoord)==null) coords.add(workCoord);
                workCoord = new Coord(ring,x,y-1);
                if(get(workCoord)==null) coords.add(workCoord);
            }
            if(ring==0){
                workCoord = new Coord(ring+1,x,y);
                if(get(workCoord)==null) coords.add(workCoord);
            }
            else if(ring==1){
                workCoord = new Coord(ring-1,x,y);
                if(get(workCoord)==null) coords.add(workCoord);
                workCoord = new Coord(ring+1,x,y);
                if(get(workCoord)==null) coords.add(workCoord);
            }
            else if(ring==2){
                workCoord = new Coord(ring-1,x,y);
                if(get(workCoord)==null) coords.add(workCoord);
            }
        }
        return coords;
    }


    public void movePawn(Pawn pawn, Coord position) {
        if(pawn!=null) {
            if (get(position) != null) throw new IllegalArgumentException("Position " + position + " is not free");
            if (position.equals(pawn.getLastPosition()))
                throw new IllegalArgumentException("Position " + position + " was taken before");
        }
        if(pawn!= null && pawn.getPosition()!=null) {
            removePawn(pawn);
        }
        switch (position.getRing()) {
            case OUTER:
                outerRing[position.getY()][position.getX()] = pawn;
                break;
            case MIDDLE:
                middleRing[position.getY()][position.getX()] = pawn;
                break;
            case INNER:
                innerRing[position.getY()][position.getX()] = pawn;
                break;
        }
    }

    public void removePawn(Pawn pawn){
        movePawn(null,pawn.getPosition());
    }


    public int checkMill(Coord coord, Player player) {
        if(get(coord).getPlayer()!=player) return 0;

        int mills=0;
        int x = coord.getX();
        int y = coord.getY();
        int ring = coord.getRing();

        Coord workCoord1;
        Coord workCoord2;
        ArrayList<Coord> coords = new ArrayList<Coord>();
        if(x==0 && y==0){
            workCoord1 = new Coord(ring,x,1);
            workCoord2 = new Coord(ring,x,2);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            workCoord1 = new Coord(ring,1,y);
            workCoord2 = new Coord(ring,2,y);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
        }
        else if(x==2 && y==0){
            workCoord1 = new Coord(ring,x,1);
            workCoord2 = new Coord(ring,x,2);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            workCoord1 = new Coord(ring,1,y);
            workCoord2 = new Coord(ring,0,y);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
        }
        else if(x==0 && y==2){
            workCoord1 = new Coord(ring,x,1);
            workCoord2 = new Coord(ring,x,0);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            workCoord1 = new Coord(ring,1,y);
            workCoord2 = new Coord(ring,2,y);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
        }
        else if(x==2 && y==2){
            workCoord1 = new Coord(ring,x,1);
            workCoord2= new Coord(ring,x,0);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            workCoord1 = new Coord(ring,1,y);
            workCoord2 = new Coord(ring,0,y);
            if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                    get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
        }
        else{
            if(x==1){
                workCoord1 = new Coord(ring,0,y);
                workCoord2 = new Coord(ring,2,y);
                if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                        get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            }
            else if(y==1){
                workCoord1 = new Coord(ring,x,0);
                workCoord2 = new Coord(ring,x,2);
                if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                        get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            }
            if(ring==0){
                workCoord1 = new Coord(1,x,y);
                workCoord2 = new Coord(2,x,y);
                if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                        get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            }
            else if(ring==1){
                workCoord1 = new Coord(0,x,y);
                workCoord2 = new Coord(2,x,y);
                if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                        get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            }
            else if(ring==2){
                workCoord1 = new Coord(0,x,y);
                workCoord2 = new Coord(1,x,y);
                if(get(workCoord1)!= null && get(workCoord1).getPlayer()==player &&
                        get(workCoord2)!=null && get(workCoord2).getPlayer()==player) mills++;
            }
        }
        return mills;
    }
}
