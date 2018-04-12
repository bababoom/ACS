package BEAN;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Table_Model extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] columnNames ;
	private Vector<String[]> v ;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Table_Model(Vector v,String[] name){
		this.v = v ;
		this.columnNames = name ;
	}
	
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return v.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return v.get(rowIndex)[columnIndex];
	}
	
	/** 
     * 得到列名 
     */  
    @Override  
    public String getColumnName(int column)  
    {  
        return columnNames[column];  
    }


	public String[] getColumnNames() {
		return columnNames;
	}


	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}


	public Vector<String[]> getV() {
		return v;
	}


	public void setV(Vector<String[]> v) {
		this.v = v;
	} 
	
	public void addColoumn(String[] str){
		this.getV().add(str) ;
		
	}
	
	/**
	 * 
	 * @param bool
	 * 表格的列宽根据单元格内容自适应
	 */
	public void resizeTable(boolean bool,JScrollPane scrollPane,JTable table_01) { 
        Dimension containerwidth = null; 
        if (!bool) { 
            //初始化时，父容器大小为首选大小，实际大小为0 
            containerwidth = scrollPane.getPreferredSize(); 
        } else { 
            //界面显示后，如果父容器大小改变，使用实际大小而不是首选大小 
            containerwidth = scrollPane.getSize(); 
        } 
        //计算表格总体宽度 
        int allwidth = table_01.getIntercellSpacing().width; 
        for (int j = 0; j < table_01.getColumnCount(); j++) { 
            //计算该列中最长的宽度 
            int max = 0; 
            for (int i = 0; i < table_01.getRowCount(); i++) { 
                int width = table_01. 
                  getCellRenderer(i, j).getTableCellRendererComponent 
                  (table_01, table_01.getValueAt(i, j), false, 
                  false, i, j).getPreferredSize().width; 
                if (width > max) { 
                    max = width; 
                } 
            } 
            //计算表头的宽度 
            int headerwidth = table_01. 
              getTableHeader(). 
              getDefaultRenderer().getTableCellRendererComponent( 
            		  table_01, table_01.getColumnModel(). 
              getColumn(j).getIdentifier(), false, false, 
              -1, j).getPreferredSize().width; 
            //列宽至少应为列头宽度 
            max += headerwidth; 
            //设置列宽 
            table_01.getColumnModel(). 
              getColumn(j).setPreferredWidth(max); 
            //给表格的整体宽度赋值，记得要加上单元格之间的线条宽度1个像素 
            allwidth += max + table_01.getIntercellSpacing().width; 
        } 
        allwidth += table_01.getIntercellSpacing().width; 
        //如果表格实际宽度大小父容器的宽度，则需要我们手动适应；否则让表格自适应 
        if (allwidth > containerwidth.width) { 
        	table_01.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        } else { 
        	table_01. 
              setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); 
        } 
	}

}
