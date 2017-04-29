import com.sun.imageio.plugins.png.*;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Hacio-PC on 2016-04-16.
 */
public class ScoreDialog extends JDialog {
    private JTable scoreT;
   // private JButton OKB;
    private JTextField filtrTF;
    private JLabel filtrL;
    private JPanel searchbarP;
    private TableRowSorter<TableModel> filtrNames;
    ScoreDialog()
    {


        searchbarP = new JPanel();
        searchbarP.setLayout(new FlowLayout());
        //searchbarP.setLayout(null);
        TableModel tableModel = new ScoreTableModel();
        filtrTF = new JTextField();
        filtrTF.setPreferredSize(new Dimension(100,20));
      //  OKB = new JButton("OK");
       // OKB.setPreferredSize(new Dimension(100,30));
        filtrL = new JLabel("Filtr:");
        filtrL.setPreferredSize(new Dimension(60,20));
        filtrL.setFont(new Font("Arial", Font.BOLD, 18));
        scoreT = new JTable(tableModel);
        filtrNames = new TableRowSorter<>(tableModel);
        scoreT.setRowSorter(filtrNames);
        setModal(true);
        setTitle("Kolko i krzyzyk wyniki");
        setName("Wyniki");
        setSize(450,300);
        setLayout(new GridLayout(2,1,0,100));
        searchbarP.add(filtrL);
        searchbarP.add(filtrTF);
      //  searchbarP.add(OKB);
        add(new JScrollPane(scoreT));
        add(searchbarP);
        //obsługa klikniecia w nagłówek kolumny
        scoreT.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = scoreT.columnAtPoint(e.getPoint());
                scoreT.getColumnClass(column);
            }
        });
        //Monitorowanie zmian w zawartości JTextField
        filtrTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String search = filtrTF.getText();
                if(search.trim().length() == 0)
                {
                    filtrNames.setRowFilter(null);
                }
                else
                {
                    filtrNames.setRowFilter(RowFilter.regexFilter("(?i)"+search));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String search = filtrTF.getText();
                if(search.trim().length() == 0)
                {
                    filtrNames.setRowFilter(null);
                }
                else
                {
                    filtrNames.setRowFilter(RowFilter.regexFilter("(?i)"+search));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {


            }
        });


    }
}
