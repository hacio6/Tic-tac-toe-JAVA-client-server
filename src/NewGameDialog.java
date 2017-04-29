/*import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
*/
/**
 * Created by Hacio-PC on 2016-04-02.
 *//**
public class NewGameDialog extends JDialog implements ActionListener {
    private static String Name_1;
    private static String Name_2;

    public static String getName_1() {
        return Name_1;
    }

    public static String getName_2() {
        return Name_2;
    }

    JLabel gracz1 = new JLabel("Gracz 1: ");
    JLabel gracz2 = new JLabel("Gracz 2: ");
    JTextField textName1 = new JTextField();
    JTextField textName2 = new JTextField();
    JButton OKB = new JButton("OK");
    JButton cancelB = new JButton("Anuluj");

    public NewGameDialog() {
        setSize(300, 200);
        setVisible(true);
        setResizable(false);
        setLayout(null);
        setTitle("Kółko i krzyżyk");

        gracz1.setBounds(10,30,50,30);
        gracz2.setBounds(10,70,50,30);
        textName1.setBounds(70,30,200,30);
        textName2.setBounds(70,70,200,30);
        OKB.setBounds(32,110,100,30);
        cancelB.setBounds(165,110,100,30);

        OKB.addActionListener(this);
        cancelB.addActionListener(this);

        add(gracz1);
        add(gracz2);
        add(textName1);
        add(textName2);
        add(OKB);
        add(cancelB);

       //setVisible(true);

    }

    @Override
    public void actionPerformed (ActionEvent e)
    {
        Object source = e.getSource();
        //MainForm game = new MainForm();
        if(source == cancelB) dispose();
        if(source == OKB)
        {
            if(textName1.getText().equals("") || textName2.getText().equals("")) dispose();
            else
            {
               // Game.setName1(textName1.getText());
              //  Game.setName2(textName2.getText());
                NewGameDialog.this.dispose();
                //game.enableButtons();
            }
        }
    }
}
*/