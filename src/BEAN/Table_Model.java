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
     * �õ����� 
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
	 * �����п���ݵ�Ԫ����������Ӧ
	 */
	public void resizeTable(boolean bool,JScrollPane scrollPane,JTable table_01) { 
        Dimension containerwidth = null; 
        if (!bool) { 
            //��ʼ��ʱ����������СΪ��ѡ��С��ʵ�ʴ�СΪ0 
            containerwidth = scrollPane.getPreferredSize(); 
        } else { 
            //������ʾ�������������С�ı䣬ʹ��ʵ�ʴ�С��������ѡ��С 
            containerwidth = scrollPane.getSize(); 
        } 
        //������������ 
        int allwidth = table_01.getIntercellSpacing().width; 
        for (int j = 0; j < table_01.getColumnCount(); j++) { 
            //�����������Ŀ�� 
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
            //�����ͷ�Ŀ�� 
            int headerwidth = table_01. 
              getTableHeader(). 
              getDefaultRenderer().getTableCellRendererComponent( 
            		  table_01, table_01.getColumnModel(). 
              getColumn(j).getIdentifier(), false, false, 
              -1, j).getPreferredSize().width; 
            //�п�����ӦΪ��ͷ��� 
            max += headerwidth; 
            //�����п� 
            table_01.getColumnModel(). 
              getColumn(j).setPreferredWidth(max); 
            //�����������ȸ�ֵ���ǵ�Ҫ���ϵ�Ԫ��֮����������1������ 
            allwidth += max + table_01.getIntercellSpacing().width; 
        } 
        allwidth += table_01.getIntercellSpacing().width; 
        //������ʵ�ʿ�ȴ�С�������Ŀ�ȣ�����Ҫ�����ֶ���Ӧ�������ñ������Ӧ 
        if (allwidth > containerwidth.width) { 
        	table_01.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        } else { 
        	table_01. 
              setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); 
        } 
	}

}
