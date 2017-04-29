import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel loginL;
    private JLabel passwordL;
    private JTextField loginTF;
    private JPasswordField passwordPF;
    private static String name, password;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterDialog() {
        setContentPane(contentPane);
        setTitle("Kółko i krzyżyk rejestracja");
        setModal(true);
        setSize(270, 170);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                name = loginTF.getText();
                password = String.valueOf(passwordPF.getPassword());


                try {
                    int addUser = RegisterDialog.addUser();
                    if (addUser > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Użytkownik dodany !!!");
                        onOK();
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }

                loginTF.setText("");
                passwordPF.setText("");



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
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        RegisterDialog dialog = new RegisterDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
    public static int addUser() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/logowanie", "root", "");

        String query = "INSERT INTO dane VALUES ('" + name + "','" + password + "')";

        Statement stm = connection.createStatement();

        int executeUpdate = stm.executeUpdate(query);

        return executeUpdate;
    }
}

