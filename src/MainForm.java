

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Hacio-PC on 2016-04-02.
 */
public class MainForm extends JFrame implements ActionListener {
    private JPanel ButtonsP = new JPanel();
    private JButton[][] TableB;
    private JPanel InformationsP = new JPanel();
    //private JLabel NameXL = new JLabel(Game.getName1());
    //private JLabel NameOL = new JLabel(Game.getName2());
    private JLabel NameXL = new JLabel(Name_1);
    private JLabel NameOL = new JLabel(Name_2);
    private JLabel ScoreXL = new JLabel("0");
    private JLabel ScoreOL = new JLabel("0");
    private JLabel Player1L = new JLabel("Gracz 1: ");
    private JLabel Player2L = new JLabel("Gracz 2: ");
    private JButton NewGameB = new JButton("Nowa Gra");
    private JButton ScoresB = new JButton("Wyniki");
    private static String Name_1,Name_2;

    public static String getName_1() {
        return Name_1;
    }

    public static String getName_2() {
        return Name_2;
    }

    /**
    private static String Name_1;
    private static String Name_2;

    public static String getName_1() {
        return Name_1;
    }

    public static String getName_2() {
        return Name_2;
    }

*/
    Server server = null;
    Client client = null;


    public static void main(String[] args) {
        MainForm mainform = new MainForm();
    }

    public MainForm() {
        setSize(325, 550);
        setTitle("Kólko i krzyzyk - GRZEGORZ HACZYK");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Game.setPlayerOneTurn(true);
        Game.setPoints1(0);
        Game.setPoints2(0);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
       // Game.readScoreFile();

        TableB = new JButton[3][3];
        ButtonsP.setBounds(10, 10, 300, 300);
        ButtonsP.setLayout(new GridLayout(3, 3, 10, 10));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TableB[i][j] = new JButton("");
                TableB[i][j].setFont(new Font("Arial", Font.BOLD, 30));
                TableB[i][j].setEnabled(false);
                ButtonsP.add(TableB[i][j]);
                TableB[i][j].addActionListener(this);
            }
        }
        add(ButtonsP);

        InformationsP.setBounds(10,330,300,100);
        InformationsP.setBorder(BorderFactory.createTitledBorder("Informacje"));
        InformationsP.setLayout (new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();
        con.insets = new Insets(0,30,10,5);
        con.gridx = 0;
        con.gridy = 0;
        Player1L.setFont(new Font("Arial", Font.BOLD, 20));
        Player2L.setFont(new Font("Arial", Font.BOLD, 20));
        NameXL.setForeground(new Color(0,120,0));
        NameXL.setFont(new Font("Arial", Font.BOLD, 20));
        NameOL.setForeground(new Color(0,120,0));
        NameOL.setFont(new Font("Arial", Font.BOLD, 20));
        ScoreXL.setForeground(new Color(160,0,0));
        ScoreXL.setFont(new Font("Arial", Font.BOLD, 20));
        ScoreOL.setForeground(new Color(160,0,0));
        ScoreOL.setFont(new Font("Arial", Font.BOLD, 20));
        InformationsP.add(Player1L, con);
        con.gridx = 1;
        InformationsP.add(NameXL, con);
        con.gridx = 2;
        InformationsP.add(ScoreXL, con);
        con.gridx = 0;
        con.gridy = 1;
        InformationsP.add(Player2L, con);
        con.gridx = 1;
        InformationsP.add(NameOL, con);
        con.gridx = 2;
        InformationsP.add(ScoreOL, con);
        add(InformationsP);

        NewGameB.setBounds(120, 450, 100, 30);
        NewGameB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog loginD = new LoginDialog();
                loginD.setVisible(true);
                // Name_1 = JOptionPane.showInputDialog(null, "Podaj imie1:", "Nowa gra", JOptionPane.INFORMATION_MESSAGE);
                // Name_2 = JOptionPane.showInputDialog(null, "Podaj imie2:", "Nowa gra", JOptionPane.INFORMATION_MESSAGE);
                LoginDialog loginD2 = new LoginDialog();
                loginD2.setVisible(true);



                            Game.setName1(loginD.getName());
                             Game.setName2(loginD2.getName());
                            NameXL.setText(Game.getName1());
                             NameOL.setText(Game.getName2());


                                    boolean found = false;

                                    for (Score x : Game.getScores()) {
                                        if (x.getName().equals(Game.getName1())) found = true;
                                    }
                                    if (!found && !Game.getName1().equals(""))
                                        Game.updateScoreTable(new Score(Game.getName1(), 0, 0, 0));

                                    found = false;

                                    for (Score x : Game.getScores()) {
                                        if (x.getName().equals(Game.getName2())) found = true;
                                    }
                                    if (!found && Game.getName1() != null)
                                        Game.updateScoreTable(new Score(Game.getName2(), 0, 0, 0));


                                    if (!NameXL.getText().equals("") && !NameOL.getText().equals("")) {
                                        resetPoints();
                                        restart(true);
                                    } else {
                                        resetPoints();
                                        restart(false);
                                    }
                                }






        });
        add(NewGameB);
        ScoresB.setBounds(230,490,100,30);
        ScoresB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScoreDialog scoreDialog = new ScoreDialog();
                scoreDialog.setVisible(true);
            }
        });
        add(ScoresB);
        Game.initializeTable();

    }
    //------------------------------------------------------------------------------------------------

    public void enableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TableB[i][j].setEnabled(true);
            }
        }
    }

    public void restart(boolean buttonsEnabled) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TableB[i][j].setEnabled(true);
                TableB[i][j].setText("");
                Game.initializeTable();
                Game.setPlayerOneTurn(true);
                setPoints();
            }
        }
    }
    public void resetPoints()
    {
        Game.setPoints1(0);
        Game.setPoints2(0);
    }
    public void setPoints()
    {
        ScoreXL.setText(Integer.toString(Game.getPoints1()));
        ScoreOL.setText(Integer.toString(Game.getPoints2()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (source == TableB[i][j]) {
                    Game.changeValue(i, j);
                    TableB[i][j].setText(Game.printChar());
                    TableB[i][j].setEnabled(false);
                    if (Game.checkIfPlayerEnd(0)) {
                        JOptionPane opPane = new JOptionPane();
                        opPane.showMessageDialog(null, "Wygral gracz " + Game.getName1() + "!", "Koniec rundy", JOptionPane.INFORMATION_MESSAGE);
                        Game.setPoints1(Game.getPoints1() + 1);

                        Game.saveScore(Game.getName1(), "Wins");
                        Game.saveScore(Game.getName2(), "Losses");
                       // Game.saveScoreFile();
                        restart(true);
                    } else if (Game.checkIfPlayerEnd(1)) {
                        JOptionPane opPane = new JOptionPane();
                        opPane.showMessageDialog(null, "Wygral gracz " + Game.getName2() + "!", "Koniec rundy", JOptionPane.INFORMATION_MESSAGE);
                        Game.setPoints2(Game.getPoints2() + 1);

                        Game.saveScore(Game.getName1(), "Losses");
                        Game.saveScore(Game.getName2(), "Wins");
                       // Game.saveScoreFile();
                        restart(true);
                    } else {
                        if (!Game.checkIfDraw())
                            Game.nextTurn();
                        else {
                            JOptionPane opPane = new JOptionPane();
                            opPane.showMessageDialog(null, "Remis!", "Koniec rundy", JOptionPane.INFORMATION_MESSAGE);

                            Game.saveScore(Game.getName1(), "Draws");
                            Game.saveScore(Game.getName2(), "Draws");
                          //  Game.saveScoreFile();
                            restart(true);

                        }
                    }

                }
            }
        }


    }

}