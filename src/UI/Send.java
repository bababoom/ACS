package UI;



import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.Document;

import dao.Dao;
import BEAN.Screen_parameter;
import BEAN.UART_con;

public class Send extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vector<Vector<Object>> list ;
	private Vector<String> col ;
	
	private Vector<Vector<Object>> r_list ;
	private Vector<String> r_col ;
	
	private Vector<String[]> send_list = new Vector<String[]>();
	
	private CON_Set con_set ;
	private Send_Dialog send ;
	private Screen_parameter screen = new Screen_parameter() ;
	
	
	@SuppressWarnings("unused")
	private Dao dao ;
	
	private JLabel label_20 ;
	private JLabel label_21 ;
	
 	private JButton button_20 ;
 	private JButton button_21 ;
 	private JButton button_22 ;
 	private JButton button_27 ;
 	private JButton button_28 ;
 	private JButton button_29 ;
 	private JButton button_30 ;
 	private JTextField text_20 ;
 	
 	private JScrollPane scrollPane_01 ;
 	private JScrollPane scrollPane_02 ;
 	private JTable table_20 ;
 	private JTable table_21 ;
	/**
	 * Create the panel.
	 */
	public Send(Dao dao) {
		this.setBounds(0, (int)(150*screen.getHeight_pro()), (int)(1400*screen.getWidth_pro()), (int)(550*screen.getHeight_pro()));
		this.setLayout(null); 
		this.setVisible(true);
		this.dao = dao ;
		
		init() ;
	}
	/*
	 * 
	 */
	public void update_list(String sql){
		if(list.size()!=0){
			list.removeAllElements();
		}
		ResultSet rs = null ;
		//System.out.println(dao);
		
			rs = dao.executeQuery(sql) ;
		boolean flag = true ;
		try {
			while(rs.next()){
				
				if(r_list.size()!=0){
					for(int i=0;i<r_list.size();i++){
						if(rs.getString("id").equals((String)r_list.get(i).get(0))){
							flag = false ;
							break ;
						}
					}
				}
				if(!flag){
					flag = true ;
					continue ;
				}
				String s ;
				if(!rs.getString("finger").equals("��")){
					s = "��" ;
				}else s="��" ;
				String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
						rs.getString("academy"),rs.getString("class"),s,null} ;
				Vector<Object> temp_1 = new Vector<Object>() ;
				for(int i=0;i<temp.length;i++){
					temp_1.addElement(temp[i]) ;
				}
				list.add(temp_1) ;
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��ʼ��ҳ��
	 */                                         
	public void init(){
		
		r_list  = new Vector<Vector<Object>>() ;
		r_col = new Vector<String>() ;
		r_col.add("ѧ  ��") ;
		r_col.add("��  ��") ;
		r_col.add("��  ��") ;
		r_col.add("��  ��") ;
		table_21 = new JTable(new DefaultTableModel(r_list,r_col){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
            {
			 	if(column == 3) return true ;
			 	else return false ;
            }
		}) ;
		table_21.getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
		  table_21.getTableHeader().setResizingAllowed(false);   //�����������
		table_21.setBounds(0,0,(int)(420*screen.getWidth_pro()), (int)(340*screen.getHeight_pro()));
		table_21.getColumnModel().getColumn(3).setCellRenderer(new MyRender("ɾ  ��")) ;
		table_21.getColumnModel().getColumn(3).setCellEditor(new MyEditor("ɾ  ��"));
		table_21.getModel().addTableModelListener(new Table_Listener());
		scrollPane_02 = new JScrollPane(table_21) ;
		table_21.editCellAt(0,3) ;
		scrollPane_02.setBounds((int)(930*screen.getWidth_pro()), (int)(80*screen.getHeight_pro()),(int)(420*screen.getWidth_pro()), (int)(340*screen.getHeight_pro()));
		scrollPane_02.setVisible(true);
        resizeTable(true,scrollPane_02,table_21); 
		this.add(scrollPane_02) ;
		
		button_27 = new JButton("רҵ�༶����") ;
		button_27.setBounds((int)(400*screen.getWidth_pro()), (int)(20*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_27.setVisible(true);
		button_27.addActionListener(new B_Listener());
		this.add(button_27) ;
		
		button_28 = new JButton("����") ;
		button_28.setBounds((int)(520*screen.getWidth_pro()), (int)(20*screen.getHeight_pro()),(int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_28.setVisible(true);
		button_28.addActionListener(new B_Listener());
		this.add(button_28) ;
		
		
		
		label_21 = new JLabel("���� :") ;
		label_21.setBounds((int)(800*screen.getWidth_pro()), (int)(20*screen.getHeight_pro()),(int)(60*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_21.setVisible(true);
		this.add(label_21) ;
		
		text_20 = new JTextField() ;
		text_20.setBounds((int)(870*screen.getWidth_pro()), (int)(20*screen.getHeight_pro()),(int)(150*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		text_20.setVisible(true);
		Document dt = text_20.getDocument();
		dt.addDocumentListener(new DOC_listener());
		this.add(text_20) ;
		
		label_20 = new JLabel() ;
		label_20.setBounds((int)(20*screen.getHeight_pro()), (int)(20*screen.getHeight_pro()), (int)(250*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_20.setVisible(true);
		this.add(label_20);
		
		button_20 = new JButton("�༭����") ;
		button_20.setBounds((int)(290*screen.getHeight_pro()), (int)(20*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_20.setVisible(true);
		button_20.addActionListener(new B_Listener());
		this.add(button_20);
		
		col = new Vector<String>() ;
		col.addElement("ѧ��");
		col.addElement("����");
		col.addElement("ѧԺ");
		col.addElement("�༶");
		col.addElement("��¼ָ��");
		col.add("����") ;
		list = new Vector<Vector<Object>>() ;
		update_list("select id,academy,name,class,finger from info") ;
		table_20 = new JTable(new DefaultTableModel(list,col){
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
	            {
				 	if(column ==5 )return true ;
				 	else return false ;
	            }
			 
		}) ;
		table_20.getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
		table_20.getTableHeader().setResizingAllowed(false);   //�����������
		table_20.getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
		table_20.getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��"));
		table_20.editCellAt(0,5) ;
	    //table_20.addMouseListener(new Table_MouseListener());
		table_20.setBounds(0,0,(int)(800*screen.getWidth_pro()), (int)(340*screen.getHeight_pro()));

		
		scrollPane_01 = new JScrollPane(table_20) ;
		scrollPane_01.setBounds((int)(130*screen.getWidth_pro()), (int)(80*screen.getHeight_pro()),(int)(700*screen.getWidth_pro()), (int)(340*screen.getHeight_pro()));
		scrollPane_01.setVisible(true);
		resizeTable(true,scrollPane_01,table_20);
		this.add(scrollPane_01) ;
		
		
		 
		button_21 = new JButton("��   ��") ;
		button_21.setBounds((int)(1000*screen.getHeight_pro()), (int)(440*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_21.setEnabled(false);
		button_21.setVisible(true);
		button_21.addActionListener(new B_Listener());
		this.add(button_21);
		
		button_22 = new JButton("���Ͷ���") ;
		button_22.addActionListener(new B_Listener());
		button_22.setBounds((int)(1000*screen.getHeight_pro()), (int)(500*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_22.setVisible(true);
		this.add(button_22);
		

		
		button_29 = new JButton("һ�����") ;
		button_29.setBounds((int)(840*screen.getHeight_pro()), (int)(80*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_29.setVisible(true);
		button_29.addActionListener(new B_Listener());
		this.add(button_29);
		
		button_30 = new JButton("�����ѡ") ;
		button_30.setBounds((int)(840*screen.getHeight_pro()), (int)(130*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_30.setVisible(true);
		button_30.addActionListener(new B_Listener());
		this.add(button_30);
	}
	
	public void SetSend_Dialog(Send_Dialog send){
		this.send = send ;
	}
	public Send_Dialog getSend(){
		return this.send ;
	}
	public CON_Set getCon_set() {
		return con_set;
	}
	public void setCon_set(CON_Set con_set) {
		this.con_set = con_set;
	}
	
	/*
	 * ����п�����Ӧ
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
	
	
	
	class B_Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(button_20)){
				if(getCon_set()!=null){
					getCon_set().setVisible(true);
				}
			}else if(e.getSource().equals(button_27)){
				
				update_list("select *from Info order by academy ASC,class DESC");
				getTable_20().setModel(new DefaultTableModel(getList(),getCol()){
					 /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column)
			            {	
						 	if(column == 5)return true ;
						 	return false;
			            }
				}) ;
				getTable_20().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
				getTable_20().getTableHeader().setResizingAllowed(false);   //�����������

				getTable_20().getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
				  getTable_20().getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��")) ;
				  getTable_20().editCellAt(0, 5) ;
				resizeTable(true,scrollPane_01,getTable_20());
			}else if(e.getSource().equals(button_28)){
				
				
				update_list("select *from Info order by academy DESC,class DESC");
				getTable_20().setModel(new DefaultTableModel(getList(),getCol()){
					 /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column)
			            {	
						 	if(column == 5)return true ;
						 	return false;
			            }
				}) ;
				getTable_20().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
				getTable_20().getTableHeader().setResizingAllowed(false);   //�����������

				getTable_20().getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
				  getTable_20().getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��")) ;
				  getTable_20().editCellAt(0, 5) ;
				resizeTable(true,scrollPane_01,getTable_20());
			}else if(e.getSource().equals(button_29)){
				if(getList().size()!=0){
					for(int i=0;i<getList().size();i++){
						  String id = (String) getList().get(i).get(0) ;
						  String name = (String) getList().get(i).get(1) ;
						  String clas = (String) getList().get(i).get(3) ;
						  Vector<Object> temp = new Vector<Object>() ;
						  temp.add(id) ;
						  temp.add(name)  ;
						  temp.add(clas) ;
						  getR_list().add(temp) ;
						  
					}
					int x = getList().size() ;
					for(int i=0;i<x;i++)((DefaultTableModel)getTable_20().getModel()).removeRow(0);
					 getTable_21().setModel(new DefaultTableModel(getR_list(),r_col){
						  /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column)
				            {
							  if(column==3)return true ;
							  else 	return false;
				            }
					  }) ;
					 getTable_21().getModel().addTableModelListener(new Table_Listener());
					 getTable_21().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
					  getTable_21().getTableHeader().setResizingAllowed(false);   //�����������
					
					 
					
				}
				 getTable_21().getColumnModel().getColumn(3).setCellRenderer(new MyRender("ɾ  ��")) ;
				  getTable_21().getColumnModel().getColumn(3).setCellEditor(new MyEditor("ɾ  ��"));
				  getTable_21().editCellAt(0,3) ;
				  resizeTable(true,scrollPane_02,getTable_21());
			}else if(e.getSource().equals(getButton_30())){
				if(getR_list().size()!=0){
					getR_list().removeAllElements();
					getTable_21().setModel(new DefaultTableModel(getR_list(),r_col){
						  /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column)
				            {
							  	if(column==3)return true;
							 	return false;
				            }
					  }) ;
					button_21.setEnabled(false);
					getTable_21().getModel().addTableModelListener(new Table_Listener());
					getTable_21().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
					  getTable_21().getTableHeader().setResizingAllowed(false);   //�����������
					update_list("select id,academy,name,class,finger from info") ;
					getTable_20().setModel(new DefaultTableModel(getList(),getCol()){
						 /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column)
				            {	
							 	if(column == 5)return true ;
							 	return false;
				            }
					}) ;
					getTable_20().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
					getTable_20().getTableHeader().setResizingAllowed(false);   //�����������

					getTable_20().getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
					  getTable_20().getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��")) ;
					  getTable_20().editCellAt(0, 5) ;
					resizeTable(true,scrollPane_01,getTable_20());
				}
			}else if(e.getSource().equals(button_21)){
			
				if(getCon_set().getCon().getSerialPort()==null){
					getCon_set().setVisible(true);
				}
				else getSend().setVisible(true);
			}else if(e.getSource().equals(button_22)){
				getSend().getSend_que().setVisible(true);
			}
			
		}
		
		
	}
	
	
	/**
	 * 
	 * @author Administrator
	 *��������ڼ��� ��������ı� ����������ַ������������ƥ��
	 */
	private class DOC_listener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
				if(e.getDocument()==text_20.getDocument()){
					
					boolean flag = false ;//���ڱ�־�Ƿ�һ��������ӵ�����״̬
					String str = text_20.getText().trim() ;//��ȡ�����������
					ResultSet rs ;
					if(getList().size()!=0)getList().removeAllElements();//�Ƚ������գ���ֹ�ظ�
				
					rs = dao.executeQuery("select *from Info where id like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							/*
							 * ��Ϊͬʱƥ��ü������ԣ�Ϊ�˷�ֹ�ظ���ӣ�ÿ������Ӳ���֮ǰ�Ȳ��أ������Ҳ��һ��
							 */
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					rs = dao.executeQuery("select *from Info where `name` like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;

								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					rs = dao.executeQuery("select *from Info where academy like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					rs = dao.executeQuery("select *from Info where class like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					getTable_20().setModel(new DefaultTableModel(getList(),getCol()){
						 /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column)
				            {	
							 	if(column == 5)return true ;
							 	return false;
				            }
					}) ;
					getTable_20().getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
					  getTable_20().getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��")) ;
					  getTable_20().editCellAt(0,5) ;
					  getTable_20().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
						getTable_20().getTableHeader().setResizingAllowed(false);   //�����������

					resizeTable(true,scrollPane_01,getTable_20());
				}
				/*
				else{
					update_list("select *from Info");
					table_01.setModel(new Table_Model(list));
					resizeTable(true);
				}
				*/
			
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		
				if(e.getDocument()==text_20.getDocument()){
					boolean flag = false ;
					String str = text_20.getText().trim() ;
					ResultSet rs ;
					if(getList().size()!=0)getList().removeAllElements();
				
					rs = dao.executeQuery("select *from Info where id like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					rs = dao.executeQuery("select *from Info where `name` like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					rs = dao.executeQuery("select *from Info where academy like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					rs = dao.executeQuery("select *from Info where class like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<getList().size();i++){
								if(rs.getString("id").equals(getList().get(i).get(0))){
									flag = true ;
									break ;
								}
							}
							if(getR_list().size()!=0||!flag){
								for(int i=0;i<getR_list().size();i++){
									if(rs.getString("id").equals((String)getR_list().get(i).get(0))){
										flag = true;
										break ;
									}
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),s} ;
								Vector<Object> temp_1 = new Vector<Object>() ;
								for(int i=0;i<temp.length;i++){
									temp_1.addElement(temp[i]) ;
								}
								getList().add(temp_1) ;
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					getTable_20().setModel(new DefaultTableModel(getList(),getCol()){
						 /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column)
				            {	
							 	if(column == 5)return true ;
							 	return false;
				            }
					}) ;
					getTable_20().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
					getTable_20().getTableHeader().setResizingAllowed(false);   //�����������

					getTable_20().getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
					  getTable_20().getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��")) ;
					  getTable_20().editCellAt(0,5) ;
					resizeTable(true,scrollPane_01,getTable_20());
				}
				/*
				else{
					update_list("select *from Info");
					table_01.setModel(new Table_Model(list));
					resizeTable(true);
				}
				*/
			
			
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
			
		}
		
	}
	

	class MyRender implements TableCellRenderer{
		private JButton button = null ;
		public MyRender(String name){
			button = new JButton(name) ;
		}
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			return button ;
		}
		
	}
	
	class MyButton extends JButton{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int row ;

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}
		public MyButton(){} ;
		public MyButton(String name){
			super(name);
		}
	}
	
	class MyEditor extends DefaultCellEditor{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private MyButton button = null ;
		
		public MyEditor(String name){
			super(new JTextField());
			button = new MyButton(name);
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					// TODO Auto-generated method stub
					
					MyButton button = (MyButton)e.getSource();
					
					if(button.getLabel().equals("��  ��")){
						
						int row = button.getRow() ;
						String id = (String) getTable_20().getValueAt(row, 0) ;
						  //�鿴��ID��Ӧ��vector��listɾ����Ȼ�������ݿ��ѯ��Ӧ�����ݷŵ�r_list����
						  /*
						   * �ڸ��±��ʱ��ע�⿴һ���ұߵı��Ƿ������ݣ�����еĻ��ǵý�����ӹ������ݹ���
						   */
						  String name = (String) getTable_20().getValueAt(row, 1) ;
						  String clas = (String) getTable_20().getValueAt(row, 3) ;
						  Vector<Object> temp = new Vector<Object>() ;
						  temp.add(id) ;
						  temp.add(name) ;
						  temp.add(clas) ;
						  getR_list().add(temp) ;
						  DefaultTableModel mm = (DefaultTableModel) getTable_20().getModel() ;
						  mm.removeRow(row);
						  getTable_21().setModel(new DefaultTableModel(getR_list(),r_col){
							  /**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							public boolean isCellEditable(int row, int column)
					            {
								  	if(column==3)return true;
								 	return false;
					            }
						  }) ;
						  getTable_21().getModel().addTableModelListener(new Table_Listener());
						  getTable_21().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
						  getTable_21().getTableHeader().setResizingAllowed(false);   //�����������
						  resizeTable(true,scrollPane_02,getTable_21());
						  getTable_21().getColumnModel().getColumn(3).setCellRenderer(new MyRender("ɾ  ��")) ;
						  getTable_21().getColumnModel().getColumn(3).setCellEditor(new MyEditor("ɾ  ��"));
						  getTable_21().editCellAt(0,3) ;
						  new ChangeEvent(button);
						  getThis().cancelCellEditing();
					}else if(button.getLabel().equals("ɾ  ��")){
						int row = button.getRow() ;
						 DefaultTableModel mm = (DefaultTableModel) getTable_21().getModel() ;
						  mm.removeRow(row);
						  update_list("select id,academy,name,class,finger from info") ;
							getTable_20().setModel(new DefaultTableModel(getList(),getCol()){
								 /**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								public boolean isCellEditable(int row, int column)
						            {	
									 	if(column == 5)return true ;
									 	return false;
						            }
							}) ;
							getTable_20().getTableHeader().setReorderingAllowed(false);   //���������ƶ�   
							getTable_20().getTableHeader().setResizingAllowed(false);   //�����������

							getTable_20().getColumnModel().getColumn(5).setCellRenderer(new MyRender("��  ��")) ;
							  getTable_20().getColumnModel().getColumn(5).setCellEditor(new MyEditor("��  ��")) ;
							  getTable_20().editCellAt(0,5) ;
							resizeTable(true,scrollPane_01,getTable_20());
						  new ChangeEvent(button);
						  getThis().cancelCellEditing();
					}
				}
				});
		}
		@Override
	    public Component getTableCellEditorComponent(JTable table, Object value,
	            boolean isSelected, int row, int column) {
	      setClickCountToStart(0);
	//�����������İ�ť���ڵ��к��зŽ�button����
	        button.setRow(row);
	        return button;
	    }
		public MyEditor getThis(){
			return this ;
		}
	}
	
	
	class Table_Listener implements TableModelListener{

		@Override
		public void tableChanged(TableModelEvent e) {
			// TODO Auto-generated method stub
			
				if(getTable_21().getRowCount()!=0){
					button_21.setEnabled(true);
				}else button_21.setEnabled(false);
			
		}
		
	}
	
	/**
	 * 
	 * @return һ������ ����װ��id �� ָ��ģ��(��ѡ��Ҫ���͵�ѧ��/��ְ����id�Ͷ�Ӧ��ָ������)
	 * ���͵�ʱ�� ���������ȡ����
	 *
	 */
	public Vector<String[]> getSend_list(){
		if(this.send_list.size()!=0) this.send_list.clear();
		ResultSet rs = null ;
		for(int i=0;i<this.getR_list().size();i++){
			String sql = "select finger,class,name from info where id ='"+((String)this.getR_list().get(i).get(0)).trim()+"'" ;
			rs = dao.executeQuery(sql) ;
			
			try {
				if(rs.next()){
					String[] temp = new String[]{((String)this.getR_list().get(i).get(0)).trim(),rs.getString("finger").trim(),
							rs.getString("name").trim(),rs.getString("class").trim()} ;
					this.send_list.addElement(temp);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.send_list ;
	}
	public Vector<Vector<Object>> getR_list() {
		return r_list;
	}
	public void setR_list(Vector<Vector<Object>> r_list) {
		this.r_list = r_list;
	}
	public JTable getTable_21() {
		return table_21;
	}
	public void setTable_21(JTable table_21) {
		this.table_21 = table_21;
	}
	public JTable getTable_20() {
		return table_20;
	}
	public void setTable_20(JTable table_20) {
		this.table_20 = table_20;
	}
	public Vector<Vector<Object>> getList() {
		return list;
	}
	public void setList(Vector<Vector<Object>> list) {
		this.list = list;
	}
	public Vector<String> getCol() {
		return col;
	}
	public void setCol(Vector<String> col) {
		this.col = col;
	}
	public JButton getButton_30() {
		return button_30;
	}
	public void setButton_30(JButton button_30) {
		this.button_30 = button_30;
	}
}
