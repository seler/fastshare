package windows;

import javax.swing.table.DefaultTableModel;


public class TabModel extends DefaultTableModel{
    
    TabModel(Object[][] data, Object[] columnNames) {
        super(data,columnNames);
    }
    
    
    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
    
    public void clearData(){
        for(int i = 0; i<getRowCount();i++){
            removeRow(i);
        }
    }
    
    
}
