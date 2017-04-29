import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField loginTF;
    private JPasswordField passwordPF;
    private JLabel loginL;
    private JLabel passwordL;
    private JButton registerB;
    private JButton buttonServer;
    private JButton buttonClient;
    public String name;
    public boolean isServer = false;
    public int port = 5000;
    public String servername = "localhost";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LoginDialog() {
        setContentPane(contentPane);
        setTitle("Kólko i krzyzyk logowanie");
        setSize(270, 170);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection connection;
                PreparedStatement ps;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logowanie", "root", "");
                    ps = connection.prepareStatement("SELECT `login`, `haslo` FROM `dane` WHERE `login` = ? AND `haslo` = ?");
                    ps.setString(1, loginTF.getText());
                    ps.setString(2, String.valueOf(passwordPF.getPassword()));
                    ResultSet result = ps.executeQuery();
                    boolean flag = true;
                    if (result.next()) {

                        System.out.println("Zalogowano!");
                        JOptionPane opPane = new JOptionPane();
                        opPane.showMessageDialog(null, "Zalogowano gracza " + loginTF.getText() + "!", "Logowanie", JOptionPane.INFORMATION_MESSAGE);
                        name = loginTF.getText();
                        onServer();
                    } else {
                        System.out.println("Błąd logowania!");
                        JOptionPane opPane = new JOptionPane();
                        opPane.showMessageDialog(null, "Błędne dane!", "Logowanie", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException ex) {
                    throw new IllegalStateException("Cannot connect the database!", ex);
                    //Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
                }



            }
        });
        buttonClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection connection;
                PreparedStatement ps;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logowanie", "root", "");
                    ps = connection.prepareStatement("SELECT `login`, `haslo` FROM `dane` WHERE `login` = ? AND `haslo` = ?");
                    ps.setString(1, loginTF.getText());
                    ps.setString(2, String.valueOf(passwordPF.getPassword()));
                    ResultSet result = ps.executeQuery();
                    boolean flag = true;
                    if (result.next()) {

                        System.out.println("Zalogowano!");
                        JOptionPane opPane = new JOptionPane();
                        opPane.showMessageDialog(null, "Zalogowano gracza " + loginTF.getText() + "!", "Logowanie", JOptionPane.INFORMATION_MESSAGE);
                        name = loginTF.getText();
                        onClient();
                    } else {
                        System.out.println("Błąd logowania!");
                        JOptionPane opPane = new JOptionPane();
                        opPane.showMessageDialog(null, "Błędne dane!", "Logowanie", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException ex) {
                    throw new IllegalStateException("Cannot connect the database!", ex);
                    //Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        });


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        registerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Dziala!");
                RegisterDialog registerDialog = new RegisterDialog();
                registerDialog.setVisible(true);

            }
        });
    }


    private void onServer()
    {
        name = loginTF.getText();
        isServer = true;
        this.setVisible(false);
    }

    private void onClient() {

        name = loginTF.getText();
        isServer = false;

        this.setVisible(false);

    }
    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }


}
