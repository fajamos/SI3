package GameProcess;

import GameModel.Pawn;
import GameModel.Players.AIPlayer;
import GameModel.Players.ConsolePlayer;
import GameModel.Players.Player;
import GameModel.State;
import Scoring.*;

import java.util.ArrayList;

public class Main {
    private static final int STARTING_NUM_OF_PAWNS = 9;

    static Player player1 = new ConsolePlayer(STARTING_NUM_OF_PAWNS,new ArrayList<Pawn>(),0);
    static Player player2 = new AIPlayer(STARTING_NUM_OF_PAWNS, new ArrayList<>(), new NumOfMovesPawnQuantityScore2(), 4, 0, false);
    //static Player player2 = new AIPlayer(STARTING_NUM_OF_PAWNS, new ArrayList<>(),new PawnQuantityScore(), 4, 0, false);
    static ConsoleUI ui = new ConsoleUI(player1,player2);
    public static void main(String[] Args){
        ui.run();
    }
}
