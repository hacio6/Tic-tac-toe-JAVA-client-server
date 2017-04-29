import java.io.*;
import java.util.ArrayList;

/**
 * Created by Hacio-PC on 2016-04-02.
 */
public class Game implements Serializable{
    private static String name1 = "";
    private static String name2 = "";
    private static int Points1 = 0;
    private static int Points2 = 0;
    private static boolean PlayerOneTurn = true;
    private static ArrayList<Score> scores = new ArrayList<Score>();


    public static ArrayList<Score> getScores()
    {
        return scores;
    }
    public static void updateScoreTable(Score score)
    {
        scores.add(score);
    }
    public static void saveScore(String player, String type)
    {
        for (Score i : scores)
        {
            if(i.getName().equals(player))
            {
                if(type=="Wins")
                {
                    int wins = i.getWins();
                    i.setWins(++wins);
                }
                if(type=="Draws")
                {
                    int draws = i.getDraws();
                    i.setDraws(++draws);
                }
                if(type=="Losses")
                {
                    int losses = i.getLosses();
                    i.setLosses(++losses);
                }
            }
        }

    }

    public static void saveScoreFile(){
        try {
            FileOutputStream fos = new FileOutputStream("output.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(scores);
            oos.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }
    public static void readScoreFile()
    {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("wyniki.txt"));
            scores = (ArrayList<Score>)in.readObject();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void setPlayerOneTurn(boolean playerOneTurn) {
        PlayerOneTurn = playerOneTurn;
    }

    public static String getName1() {
        return name1;
    }

    public static void setName1(String name1) {
        Game.name1 = name1;
    }

    public static String getName2() {
        return name2;
    }

    public static void setName2(String name2) {
        Game.name2 = name2;
    }

    public static int getPoints2() {
        return Points2;
    }

    public static void setPoints2(int points2) {
        Points2 = points2;
    }

    public static int getPoints1() {
        return Points1;
    }

    public static void setPoints1(int points1) {
        Points1 = points1;
    }

    private static int[][] table = new int[3][3];

    public static void initializeTable()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                table[i][j] = -1;

    }

    public static boolean checkIfPlayerEnd(int sign)
    {
        for (int i = 0; i < 3; i++)
        {
            if(table[0][i] == sign && table[1][i] == sign && table[2][i] == sign) return true;
            if(table[i][0] == sign && table[i][1] == sign && table[i][2] == sign) return true;
        }
        if(table[0][0] == sign && table[1][1] == sign && table[2][2] == sign) return true;
        else if (table[0][2] == sign && table[1][1] == sign && table[2][0] == sign) return true;
        else return false;
    }

    public static boolean checkIfDraw()
    {
        int owned = 0;
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(table[i][j] != -1)
                    owned++;
            }
        }
        if (owned == 9) return true;
        else return false;
    }

    public static void changeValue(int i, int j)
    {
        if (PlayerOneTurn) table[i][j] = 0;
        else table[i][j] = 1;
    }

    public static void nextTurn()
    {
        if (PlayerOneTurn) PlayerOneTurn = false;
        else PlayerOneTurn = true;
    }

    public static String printChar()
    {
        if (PlayerOneTurn) return "X";
        else return "O";
    }




}
