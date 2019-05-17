package GameProcess;

import GameModel.*;
import GameModel.Players.AIPlayer;
import GameModel.Players.ConsolePlayer;
import GameModel.Players.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class ConsoleUI {
    private Player player1Clone;
    private Player player2Clone;
    private State currentState;
    private final boolean mute;
    private int turns = 0;

    public ConsoleUI(Player p1, Player p2){
        currentState = new State(p1,p2);
        if(p1 instanceof AIPlayer && p2 instanceof AIPlayer) {
            player1Clone = p1.getClone(p1, new ArrayList<>());
            player2Clone = p2.getClone(p1, new ArrayList<>());
        }
        mute = false;
    }
    public ConsoleUI(Player p1, Player p2,boolean mute){
        currentState = new State(p1,p2);
        if(p1 instanceof AIPlayer && p2 instanceof AIPlayer) {
            player1Clone = p1.getClone(p1, new ArrayList<>());
            player2Clone = p2.getClone(p1, new ArrayList<>());
        }
        this.mute = mute;
    }

    public void printStateToConsole(){
        if(!mute) {
            System.out.print("\t");
            for (int i = 0; i < 7; i++) {
                System.out.print(i + "\t");
            }
            int i = 0;
            System.out.print("\n" + i++ + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 0, 0))) + "\t\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 1, 0))) + "\t\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 2, 0))) + "\n");

            System.out.print(i++ + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 0, 0))) + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 1, 0))) + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 2, 0))) + "\n");

            System.out.print(i++ + "\t\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 0, 0))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 1, 0))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 2, 0))) + "\n");


            System.out.print(i++ + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 0, 1))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 0, 1))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 0, 1))) + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 2, 1))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 2, 1))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 2, 1))) + "\n");

            System.out.print(i++ + "\t\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 0, 2))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 1, 2))) + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.INNER, 2, 2))) + "\n");

            System.out.print(i++ + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 0, 2))) + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 1, 2))) + "\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.MIDDLE, 2, 2))) + "\n");

            System.out.print(i++ + "\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 0, 2))) + "\t\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 1, 2))) + "\t\t\t");
            System.out.print(tag(currentState.getGameTable().get(new Coord(GameTable.OUTER, 2, 2))) + "\n");

            System.out.println("\n\n turn of player " + (currentState.getTurnOfPlayer() == currentState.getPlayer1() ? "1" : "2") + ":");
        }
    }

    public void run(boolean preventLongGames){
        turns = 0;
        while (currentState.getWinner()==null){
            printStateToConsole();
            currentState.playTurn();
            turns++;
            if(preventLongGames && turns>200){
                System.out.println("This match will last endlessly");
                break;
            }
        }
        if(preventLongGames && turns>200){
            ConsoleUI clone = new ConsoleUI(player1Clone,player2Clone,mute);
            clone.run();
            currentState = clone.currentState;
            turns = clone.turns;
        }
        printStateToConsole();
        System.out.println("winner: Player " + (currentState.getWinner()==currentState.getPlayer1() ? "1" : "2"));
        System.out.println("Time of player 1: " + currentState.getPlayer1().getOnTime());
        System.out.println("Time of player 2: " + currentState.getPlayer2().getOnTime());
        System.out.println("turns: " + turns);
        System.out.println((currentState.getPlayer1().getOnTime() + currentState.getPlayer2().getOnTime())/turns);
    }

    private String tag(Pawn pawn){
        if(pawn==null) return "-";
        if(pawn.getPlayer()==currentState.getPlayer1()) return "1";
        if(pawn.getPlayer()==currentState.getPlayer2()) return "2";
        return "ERROR";
    }

    public void run(){
        run(false);
    }
}
