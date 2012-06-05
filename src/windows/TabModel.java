package windows;

import javax.swing.table.DefaultTableModel;

/**
 * Describes Table data model
 */
public class TabModel extends DefaultTableModel{
    
    /**
     * Constructor
     * @param data data objects array
     * @param columnNames column names array
     */
    TabModel(Object[][] data, Object[] columnNames) {
        super(data,columnNames);
    }
    
    /**
     * Hardcoded - cells are not editable
     * @param row cell's row
     * @param column cell's column
     * @return false
     */
    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
    
    /**
     * Clears whole table
     */
    public void clearData(){
        for(int i = 0; i<getRowCount();i++){
            removeRow(i);
        }
    }
    
    
}
