package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.Document;

import BEAN.Screen_parameter;
import BEAN.UART_con;
import dao.Dao;

public class display extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dao dao ;
	private Screen_parameter screen = new Screen_parameter() ;//Ϊ�˻�ȡ��Ļ�Ĳ�����ʹ�����κε����ϵĴ�С��������һ��
	
	@SuppressWarnings("unused")
	private JDialog fff ;
	
	private JFrame frame ;
	
	private UART_con con ;
	
	private String str="";
	
	private Vector<String[]> list ;
	
	private JLabel label_01  ;
	private JLabel label_02 ;
	private JLabel label_03 ;
	private JLabel label_04 ;
	private JLabel label_05 ;
	private JLabel label_06 ;
	private JLabel label_07 ;
	
	private JScrollPane scrollPane ;
	
	private JTable table_01 ;
	
	private JButton button_01 ;
	private JButton button_02 ;
	private JButton button_03 ;
	private JButton button_04 ;
	private JButton button_05 ;
	private JButton button_06 ;
	private JButton button_07 ;
	private JButton button_08 ;
	
	private JTextField text_01 ;
	private JTextField text_02 ;
	
	
	/**
	 * Create the panel.
	 */
	public display(Dao dao) {
		this.setBounds(0, (int)(150*screen.getHeight_pro()), (int)(1400*screen.getWidth_pro()), (int)(550*screen.getHeight_pro()));
		this.setLayout(null); 
		this.setVisible(true);
		this.dao = dao ;
		initial() ;
	}
	
	
	
	public void initial(){
		
		
		
		list = new Vector<String[]>() ;
		this.update_list("select *from Info") ;
		table_01 = new JTable(new Table_Model(list)) ;
		table_01.setBounds((int)(0*screen.getWidth_pro()), (int)(0*screen.getHeight_pro()),(int)(800*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()));
		table_01.getSelectionModel().addListSelectionListener(new Table_Listener()) ;
		
		scrollPane = new JScrollPane(table_01) ;
		scrollPane.setBounds((int)(50*screen.getWidth_pro()), (int)(80*screen.getHeight_pro()),(int)(950*screen.getWidth_pro()), (int)(340*screen.getHeight_pro()));
		scrollPane.setVisible(true);
		scrollPane.addComponentListener(new ComponentAdapter() { 
            @Override 
            public void componentResized(ComponentEvent e) { 
                resizeTable(true); 
            } 
		}); 
		this.add(scrollPane) ;
	
		label_03 = new JLabel("ѧ��/ְ���� : ") ;
		label_03.setBounds((int)(50*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_03) ;
		label_03.setVisible(true);
		
		label_06 = new JLabel("") ;
		label_06.setBounds((int)(150*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_06) ;
		label_06.setVisible(true);
		
		label_04 = new JLabel("���� : ") ;
		label_04.setBounds((int)(280*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(60*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_04) ;
		label_04.setVisible(true);
		
		text_02 = new JTextField() ;
		text_02.setBounds((int)(350*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(text_02) ;
		text_02.getDocument().addDocumentListener(new DOC_listener());
		text_02.setVisible(true);
		
		label_05 = new JLabel("רҵ�༶ : ") ;
		label_05.setBounds((int)(480*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_05) ;
		label_05.setVisible(true);
		
		label_07 = new JLabel("") ;
		label_07.setBounds((int)(580*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_07) ;
		label_07.setVisible(true);
		
		button_05 = new JButton(" ��¼ָ�� ") ;
		button_05.setBounds((int)(690*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_05) ;
		button_05.addActionListener(new Button_Listener());
		button_05.setVisible(true);
		
		button_06 = new JButton("�޸�") ;
		button_06.setBounds((int)(870*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_06) ;
		button_06.addActionListener(new Button_Listener());
		button_06.setVisible(true);
		
		button_08 = new JButton("����") ;
		button_08.setBounds((int)(1030*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_08) ;
		button_08.addActionListener(new Button_Listener());
		button_08.setVisible(true);
		
		button_07 = new JButton("ɾ��") ;
		button_07.setBounds((int)(950*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_07) ;
		button_07.addActionListener(new Button_Listener());
		button_07.setVisible(true);
		
		button_01 = new JButton("רҵ�༶����") ;
		button_01.setBounds((int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_01.setVisible(true);
		button_01.addActionListener(new Button_Listener());
		this.add(button_01) ;
		
		button_02 = new JButton("����") ;
		button_02.setBounds((int)(170*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_02.setVisible(true);
		button_02.addActionListener(new Button_Listener());
		this.add(button_02) ;
		
		button_03 = new JButton("���ڽ���") ;
		button_03.setBounds((int)(330*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_03.setVisible(true);
		button_03.addActionListener(new Button_Listener());
		this.add(button_03) ;
		
		button_04 = new JButton("����") ;
		button_04.setBounds((int)(450*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_04.setVisible(true);
		button_04.addActionListener(new Button_Listener());
		this.add(button_04) ;
		
		label_02 = new JLabel("���� :") ;
		label_02.setBounds((int)(610*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(60*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_02.setVisible(true);
		this.add(label_02) ;
		
		text_01 = new JTextField() ;
		text_01.setBounds((int)(680*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(150*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		text_01.setVisible(true);
		Document dt = text_01.getDocument();
		dt.addDocumentListener(new DOC_listener());
		this.add(text_01) ;
		
		label_01 = new JLabel("") ;
		label_01.setForeground(Color.RED);
		label_01.setBounds((int)(50*screen.getWidth_pro()), (int)(10*screen.getHeight_pro()),(int)(200*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_01.setVisible(true);
		this.add(label_01) ;
		
	}
	
	/**
	 * ����list
	 * @param sql
	 */
	public void update_list(String sql){
		if(list.size()!=0){
			list.removeAllElements();
		}
		//System.out.println(dao);
		ResultSet rs = dao.executeQuery(sql) ;
		try {
			while(rs.next()){
				String s ;
				if(!rs.getString("finger").equals("��")){
					s = "��" ;
				}else s="��" ;
				String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
						rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
				list.addElement(temp);

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param bool
	 * �����п���ݵ�Ԫ����������Ӧ
	 */
	public void resizeTable(boolean bool) { 
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
	
	private class Table_Listener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			if(!e.getValueIsAdjusting()){
				if(table_01.getSelectedRow()!=-1){
					Object[] a;
					
			         a = list.elementAt(table_01.getSelectedRow()) ;
			         label_06.setText(a[0].toString());
			         label_07.setText(a[2].toString());
			         text_02.setText(a[1].toString());
			         str = "" ;
				}
			}
		}
		
	}
	
	private class Finger extends JDialog{
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JLabel label_11 ;
		
		private JButton button_11 ;
		private JButton button_12 ;
		private JButton button_13 ;
		private JButton button_14 ;
		
		public Finger(JFrame frame,boolean modal){
			super(frame,modal) ;
			//System.out.println(parent.getParent().getName());
			setTitle("��¼ָ��") ;
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setBounds((int)(600*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()),(int)(600*screen.getWidth_pro()), (int)(400*screen.getHeight_pro()));
			this.setLayout(null);
			this.setResizable(false) ;
			this.init() ;
			this.setVisible(true);
			
		
			//System.out.println("ok");
		}
		public void init(){
			
			
			label_11 = new JLabel("�밴�°�ť��ʼ��һ��ָ��¼��") ;
			label_11.setBounds((int)(175*screen.getWidth_pro()), (int)(90*screen.getHeight_pro()),(int)(250*screen.getWidth_pro()), (int)(60*screen.getHeight_pro()));
			label_11.setVisible(true);
			label_11.setForeground(Color.red);
			add(label_11) ;
			
			button_11 = new JButton("��һ��ָ��") ;
			button_11.setBounds((int)(70*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_11.setEnabled(true);
			button_11.addActionListener(new B_Listener());
			button_11.setVisible(true);
			add(button_11) ;
			
			button_12 = new JButton("�ڶ���ָ��") ;
			button_12.setBounds((int)(200*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_12.setEnabled(false);
			button_12.addActionListener(new B_Listener());
			button_12.setVisible(true);
			add(button_12) ;
			
			button_13 = new JButton("�ϲ�ģ��") ;
			button_13.setBounds((int)(330*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_13.setEnabled(false);
			button_13.addActionListener(new B_Listener());
			button_13.setVisible(true);
			add(button_13) ;
			
			button_14 = new JButton("ȡ��") ;
			button_14.setBounds((int)(440*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_14.setEnabled(true);
			button_14.setVisible(true);
			button_14.addActionListener(new B_Listener());
			add(button_14) ;
		}
		
		public JDialog getDialog(){
			return this ;
		}
		
		private class B_Listener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().equals(button_11)){
					new Thread(new Button_11_run()).start();
				}else if(e.getSource().equals(button_14)){
					getDialog().dispose();
				}else if(e.getSource().equals(button_12)){
					new Thread(new Button_12_run()).start();
				}else if(e.getSource().equals(button_13)){
					new Thread(new Button_13_run()).start();
				}
			}
			
		}
		
		private	class Button_13_run implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					con.write("EF 01 FF FF FF FF 01 00 03 05 00 09");
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 0A 00 14".replace(" ", ""))){
						label_11.setText("�ϳ�ʧ�ܣ�������������һ����ָ��������¼��ָ������������ ");
						con.setTest("");
						break ;
					}else if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
						con.setTest("");
						con.write("EF 01 FF FF FF FF 01 00 04 08 01 00 0E");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String temp = con.getTest().trim().replace(" ", "").substring(18,20) ;
						//System.out.println(temp);
						if(temp.equals("00")){
							str = con.getTest().trim() ;
							label_11.setText("ģ���ϴ��ɹ� ��");
							
							str = con.getInfo(str) ;
							con.setTest("");
							getDialog().dispose();
							break ;
						}else{
							label_11.setText("ģ���ϴ�ʧ�ܣ������� ��");
							con.setTest("");
							break ;
						}
					}else{
						label_11.setText("�������������ԣ�");
						con.setTest("");
						break ;
					}
				}
			}
			
		}
		
		private class Button_11_run implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count = 0 ;
				while(true){
					if(count ==6){
						label_11.setText("��һ��ָ��¼�볬ʱ�������� ");
						break ;
					}
					con.write("EF 01 FF FF FF FF 01  00 03 01  00 05");
					count++ ;
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count ++ ;
					if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
						con.setTest("");
						con.write("EF 01 FF FF FF FF 01 00 04 02 01 00 08");
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
							con.setTest("");
							label_11.setText("��һ��ָ��¼����ɣ�");
							button_12.setEnabled(true);
						}else{
							con.write("EF 01 FF FF FF FF 01 00 04 02 01 00 08");
							try {
								Thread.sleep(800);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
								con.setTest("");
								label_11.setText("��һ��ָ��¼����ɣ�");
								button_12.setEnabled(true);
							}else{
								con.setTest("");
								label_11.setText("����ʧ�ܣ�������¼�룡");
							}
						}
						break ;
					}else{
						con.setTest("");
					}
				}
			}
			
		}
		
		private class Button_12_run implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count = 0 ;
				while(true){
					if(count ==6){
						label_11.setText("�ڶ���ָ��¼�볬ʱ�������� ");
						break ;
					}
					con.write("EF 01 FF FF FF FF 01  00 03 01  00 05");
					count++ ;
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count ++ ;
					if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
						con.setTest("");
						con.write("EF 01 FF FF FF FF 01 00 04 02 02 00 09");
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
							con.setTest("");
							label_11.setText("�ڶ���ָ��¼����ɣ�");
							button_13.setEnabled(true);
						}else{
							con.write("EF 01 FF FF FF FF 01 00 04 02 02 00 09");
							try {
								Thread.sleep(800);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(con.getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
								con.setTest("");
								label_11.setText("�ڶ���ָ��¼����ɣ�");
								button_13.setEnabled(true);
							}else{
								con.setTest("");
								label_11.setText("����ʧ�ܣ�������¼�룡");
							}
						}
						break ;
					}else{
						con.setTest("");
					}
				}
			}
			
		}
		
		
	}
	
	
	private class DOC_listener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
				if(e.getDocument()==text_01.getDocument()){
					boolean flag = false ;
					String str = text_01.getText().trim() ;
					ResultSet rs ;
					if(list.size()!=0)list.removeAllElements();
				
					rs = dao.executeQuery("select *from Info where id like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
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
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
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
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
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
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_01.setModel(new Table_Model(list));
					resizeTable(true);
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
			
		
				if(e.getDocument()==text_01.getDocument()){
					boolean flag = false ;
					String str = text_01.getText().trim() ;
					ResultSet rs ;
					if(list.size()!=0)list.removeAllElements();
				
					rs = dao.executeQuery("select *from Info where id like '%"+str+"%'") ;
					try {
						while(rs.next()){
							String s ;
							if(!rs.getString("finger").equals("��")){
								s = "��" ;
							}else s="��" ;
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
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
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
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
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
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
							
							for(int i=0;i<list.size();i++){
								if(rs.getString("id").equals(list.get(i)[0])){
									flag = true ;
									break ;
								}
							}
							if(!flag){
								String[] temp = new String[]{rs.getString("id"),rs.getString("name"),
										rs.getString("academy"),rs.getString("class"),rs.getString("work"),s,rs.getString("date").substring(0, 19)} ;
								
								list.addElement(temp);
								flag = false ;
							}else flag = false ;
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_01.setModel(new Table_Model(list));
					resizeTable(true);
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
	
	private class Button_Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(button_01)){
				update_list("select *from Info order by academy DESC,class DESC");
				text_01.setText("");
				table_01.setModel(new Table_Model(list));
				resizeTable(true);
			}else  if(e.getSource().equals(button_02)){
				update_list("select *from Info order by academy ASC,class DESC");
				text_01.setText("");
				table_01.setModel(new Table_Model(list));
				resizeTable(true);
			}else if(e.getSource().equals(button_03)){
				update_list("select *from Info order by date DESC");
				text_01.setText("");
				table_01.setModel(new Table_Model(list));
				resizeTable(true);
			}else if(e.getSource().equals(button_04)){
				update_list("select *from Info order by date ASC");
				text_01.setText("");
				table_01.setModel(new Table_Model(list));
				resizeTable(true);
			}else if(e.getSource().equals(button_06)){
				if(!label_06.getText().equals("")){
					if(str.equals("")){
						String sql ="update Info set name='"+text_02.getText().trim()+"' where id='"+label_06.getText().trim()+"'" ;
						dao.execute(sql) ;
						update_list("select *from Info");
						
						table_01.setModel(new Table_Model(list));
						resizeTable(true);
						label_06.setText("");
						text_02.setText("");
						label_07.setText("");
						str = "" ;
						
						JOptionPane.showMessageDialog(null, "���ѳɹ��޸�������");
					}else{
						String sql ="update Info set name='"+text_02.getText().trim()+"',finger='"+str.trim()+"' where id='"+label_06.getText().trim()+"'" ;
						dao.execute(sql) ;
						update_list("select *from Info");
						
						table_01.setModel(new Table_Model(list));
						resizeTable(true);
						label_06.setText("");
						text_02.setText("");
						label_07.setText("");
						str = "" ;
						
						JOptionPane.showMessageDialog(null, "���ѳɹ��޸�������ָ�ƣ�");

					}
				}
			}else if(e.getSource().equals(button_07)){
				if(!label_06.getText().equals("")){
					
					int x = JOptionPane.showConfirmDialog(null, 
							"ȷ��Ҫɾ��"+text_02.getText()+"��������", "ȷ��ɾ��", JOptionPane.YES_NO_OPTION);
					
					if(x==0)
					{
						String sql = "delete from Info where id='"+label_06.getText()+"'" ;
						dao.execute(sql) ;
						update_list("select *from Info");
						table_01.setModel(new Table_Model(list));
						resizeTable(true);
					}
					

					/*
					delete = new Delete() ;
					getDelete().setLabel();
					getDelete().setEnabled(true);
					getDelete().setVisible(true);
					*/
					
				}
			}else if(e.getSource().equals(button_08)){
				label_06.setText(""); 
				text_02.setText("");
				label_07.setText("");
				str="" ;
			}else if(e.getSource().equals(button_05)&&(!label_06.getText().equals(""))){
				if(con==null){
					JOptionPane.showMessageDialog(null, "�뵽ָ��¼���ҳ�������ӣ�ȷ�������Ҳ���������ȷ������");
				}
				else {
					str = "" ;
					fff = new Finger(frame,true) ;
				}
			}
		}
		
	}
	
	
	
	
		
	
	
	
	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTable getTable_01() {
		return table_01;
	}

	public void setTable_01(JTable table_01) {
		this.table_01 = table_01;
	}
	
	
	@SuppressWarnings("rawtypes")
	public Table_Model Create_Model(Vector list){
		return new Table_Model(list) ;
	}
	public Vector<String[]> getList() {
		return list;
	}

	public void setList(Vector<String[]> list) {
		this.list = list;
	}
	public String getStr() {
		return str;
	}



	public void setStr(String str) {
		this.str = str;
	}




	public void setCon(UART_con con) {
		this.con = con ;
	}

	private class Table_Model extends AbstractTableModel{
		
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String[] columnNames =  
		        { "ѧ��", "����", "ѧԺ", "�༶","���" ,"��¼ָ��","����"}; 
		Vector<String[]> v ;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Table_Model(Vector v){
			this.v = v ;
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
		
        
	}
	



	public JFrame getFrame() {
		return frame;
	}



	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
