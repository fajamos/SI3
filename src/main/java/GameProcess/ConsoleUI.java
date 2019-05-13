package GameProcess;

import GameModel.*;
import GameModel.Players.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsoleUI {
    private State currentState;

    public ConsoleUI(Player p1, Player p2){
        currentState = new State(p1,p2);
    }

    public void printStateToConsole(){
        System.out.print("\t");
        for (int i = 0; i < 7; i++) {
            System.out.print(i + "\t");
        }
        int i = 0;
        System.out.print("\n" + i++ + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,0,0))) + "\t\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,1,0))) + "\t\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,2,0))) + "\n");

        System.out.print(i++ + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,0,0))) + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,1,0))) + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,2,0))) + "\n");

        System.out.print(i++ + "\t\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,0,0))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,1,0))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,2,0))) + "\n");


        System.out.print(i++ + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,0,1))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,0,1))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,0,1))) + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,2,1))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,2,1))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,2,1))) + "\n");

        System.out.print(i++ + "\t\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,0,2))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,1,2))) + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER,2,2))) + "\n");

        System.out.print(i++ + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,0,2))) + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,1,2))) + "\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE,2,2))) + "\n");

        System.out.print(i++ + "\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,0,2))) + "\t\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,1,2))) + "\t\t\t");
        System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER,2,2))) + "\n");

        System.out.println("\n\n turn of player " + (currentState.getTurnOfPlayer()==currentState.getPlayer1() ? "1" : "2") + ":");
    }

    public void run(){
        int i = 0;
        while (currentState.getWinner()==null){
            printStateToConsole();
            currentState.playTurn();
            i++;
        }
        printStateToConsole();
        System.out.println("winner: Player " + (currentState.getWinner()==currentState.getPlayer1() ? "1" : "2"));
        System.out.println("Time of player 1: " + currentState.getPlayer1().getOnTime());
        System.out.println("Time of player 2: " + currentState.getPlayer2().getOnTime());
        System.out.println("turns: " + i);
    }

    private String tag(Pawn pawn){
        if(pawn==null) return "-";
        if(pawn.getPlayer()==currentState.getPlayer1()) return "1";
        if(pawn.getPlayer()==currentState.getPlayer2()) return "2";
        return "ERROR";
    }
}
