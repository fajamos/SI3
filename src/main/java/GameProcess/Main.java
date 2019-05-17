package GameProcess;

import GameModel.GameTable;
import GameModel.Pawn;
import GameModel.Players.AIPlayer;
import GameModel.Players.ConsolePlayer;
import GameModel.Players.Player;
import GameModel.State;
import Scoring.*;

import java.util.ArrayList;

public class Main {
    private static final int STARTING_NUM_OF_PAWNS = 9;
    private static final int GAMES_NUMBER = 1;

    //static Player player1 = new ConsolePlayer(STARTING_NUM_OF_PAWNS,new ArrayList<Pawn>(),0);
    static Player player1 = new AIPlayer(STARTING_NUM_OF_PAWNS, new ArrayList<>(), new PawnQuantityScore(), 4, 0, true);
    static Player player2 = new AIPlayer(STARTING_NUM_OF_PAWNS, new ArrayList<>(),new AdvencedScore(4), 4, 0, true);
    static ConsoleUI ui = new ConsoleUI(player1,player2);
    public static void main(String[] Args){
        int player1Wins = 0;
        int player2Wins = 0;
        long player1Time = 0;
        long player2Time = 0;
        for (int i = 0; i < GAMES_NUMBER; i++) {
            ConsoleUI ui = new ConsoleUI(player1.getClone(player1,new ArrayList<>()),player2.getClone(player2,new ArrayList<>()),false);
            ui.run(false);
            GameScoring scoringToDelete;
            if(ui.getCurrentState().getWinner() == ui.getCurrentState().getPlayer1()){
                player1Wins++;
            } else {
                player2Wins++;
            }
            player1Time+=ui.getCurrentState().getPlayer1().getOnTime();
            player2Time+=ui.getCurrentState().getPlayer2().getOnTime();
            System.out.println("\nGAME: " + i);
        }

        System.out.println(player1Wins + ":" + player2Wins + "\tAvg Times: Player1: " + player1Time/GAMES_NUMBER + " Player2: " + player2Time/GAMES_NUMBER);
    }
}
