import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Hacio-PC on 2016-04-16.
 */
public class ScoreTableModel extends AbstractTableModel {
   private static String[] columnsNames =
           {
                   "Nazwa:", "Wygrane:", "Remisy:", "Przegrane:"
           };
    private static Object[][] data;

    public ScoreTableModel()
    {
        data = new Object[Game.getScores().size()][columnsNames.length];
        for (int i = 0; i < Game.getScores().size(); i++ )
            for (int j =0; j < columnsNames.length; j++)
            {
                data[i][j] = Game.getScores().get(i).getValueOfIndex(j);
            }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    @Override
    public Class getColumnClass(int index)
    {
        if(index==0)
            return String.class;
        else return Integer.class;
    }
    @Override
    public String getColumnName(int column)
    {
        return columnsNames[column];
    }
}
