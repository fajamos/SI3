package GameProcess;

import GameModel.Players.AIPlayer;
import GameModel.Players.Player;
import Scoring.AdvencedScore;
import Scoring.GameScoring;

import java.util.ArrayList;

public class MainTournament {
    private static final int STARTING_NUM_OF_PAWNS = 9;
    private static final int GAMES_NUMBER = 600;

    //static Player player1 = new ConsolePlayer(STARTING_NUM_OF_PAWNS,new ArrayList<Pawn>(),0);
    static Player player1 = new AIPlayer(STARTING_NUM_OF_PAWNS, new ArrayList<>(), new AdvencedScore(4), 4, 0, true);
    static Player player2 = new AIPlayer(STARTING_NUM_OF_PAWNS, new ArrayList<>(),new AdvencedScore(4), 4, 0, true);
    static ConsoleUI ui = new ConsoleUI(player1,player2);
    public static void main(String[] Args){
        ArrayList<GameScoring> ranking = new ArrayList<>(10);
        ranking.add(new AdvencedScore());
        ranking.add(new AdvencedScore());
        ranking.add(new AdvencedScore(Math.random()*5,Math.random()*5,Math.random()*5));
        ranking.add(new AdvencedScore(Math.random()*5,Math.random()*5,Math.random()*5));
        ranking.add(new AdvencedScore(Math.random()*5,Math.random()*5,Math.random()*5));
        ranking.add(new AdvencedScore(Math.random()*5,Math.random()*5,Math.random()*5));
        ranking.add(new AdvencedScore(Math.random()*5,Math.random()*5,Math.random()*5));
        ranking.add(new AdvencedScore(Math.random()*5,Math.random()*5,Math.random()*5));
        ranking.add(new AdvencedScore(Math.random()*10,Math.random()*10,Math.random()*10));
        ranking.add(new AdvencedScore(Math.random()*10,Math.random()*10,Math.random()*10));
        int player1Wins = 0;
        int player2Wins = 0;
        long player1Time = 0;
        long player2Time = 0;
        for (int i = 0; i < GAMES_NUMBER; i++) {
            ConsoleUI ui = new ConsoleUI(player1.getClone(player1,new ArrayList<>()),player2.getClone(player2,new ArrayList<>()),true);
            ui.run();
            GameScoring scoringToDelete;
            if(ui.getCurrentState().getWinner() == ui.getCurrentState().getPlayer1()){
                player1Wins++;
                AdvencedScore winnerScore = (AdvencedScore) ((AIPlayer) player1).getScoring();
                scoringToDelete = ((AIPlayer)player2).getScoring();
                ranking.remove(scoringToDelete);
                AdvencedScore newScore = (winnerScore).cross((AdvencedScore)ranking.get((int) (Math.random()*ranking.size())));
                newScore.mutate(0.3,0.2);
                ranking.add(newScore);
                ((AIPlayer) player2).setScoring(ranking.get((int) (Math.random()*ranking.size())));
                ((AIPlayer) player1).setScoring(ranking.get((int) (Math.random()*ranking.size())));
                System.out.printf("\nPawn:%f Move:%f Mill:%f \n",winnerScore.getPawnPar(),winnerScore.getMovePar(),winnerScore.getMillPar());
            } else {
                player2Wins++;
                AdvencedScore winnerScore = (AdvencedScore) ((AIPlayer) player2).getScoring();
                scoringToDelete = ((AIPlayer)player1).getScoring();
                ranking.remove(scoringToDelete);
                AdvencedScore newScore = (winnerScore).cross((AdvencedScore)ranking.get((int) (Math.random()*ranking.size())));
                newScore.mutate(0.3,0.2);
                ranking.add(newScore);
                ((AIPlayer) player2).setScoring(ranking.get((int) (Math.random()*ranking.size())));
                ((AIPlayer) player1).setScoring(ranking.get((int) (Math.random()*ranking.size())));
                System.out.printf("\nPawn:%f Move:%f Mill:%f \n",winnerScore.getPawnPar(),winnerScore.getMovePar(),winnerScore.getMillPar());
            }
            player1Time+=ui.getCurrentState().getPlayer1().getOnTime();
            player2Time+=ui.getCurrentState().getPlayer2().getOnTime();
            System.out.println(i);
        }
        System.out.println(player1Wins + ":" + player2Wins + "\tAvg Times: Player1: " + player1Time/GAMES_NUMBER + " Player2: " + player2Time/GAMES_NUMBER);
    }
}
