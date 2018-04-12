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
	private Screen_parameter screen = new Screen_parameter() ;//为了获取屏幕的参数，使得在任何电脑上的大小比例保持一致
	
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
	
		label_03 = new JLabel("学号/职工号 : ") ;
		label_03.setBounds((int)(50*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_03) ;
		label_03.setVisible(true);
		
		label_06 = new JLabel("") ;
		label_06.setBounds((int)(150*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_06) ;
		label_06.setVisible(true);
		
		label_04 = new JLabel("姓名 : ") ;
		label_04.setBounds((int)(280*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(60*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_04) ;
		label_04.setVisible(true);
		
		text_02 = new JTextField() ;
		text_02.setBounds((int)(350*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(text_02) ;
		text_02.getDocument().addDocumentListener(new DOC_listener());
		text_02.setVisible(true);
		
		label_05 = new JLabel("专业班级 : ") ;
		label_05.setBounds((int)(480*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_05) ;
		label_05.setVisible(true);
		
		label_07 = new JLabel("") ;
		label_07.setBounds((int)(580*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_07) ;
		label_07.setVisible(true);
		
		button_05 = new JButton(" 重录指纹 ") ;
		button_05.setBounds((int)(690*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_05) ;
		button_05.addActionListener(new Button_Listener());
		button_05.setVisible(true);
		
		button_06 = new JButton("修改") ;
		button_06.setBounds((int)(870*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_06) ;
		button_06.addActionListener(new Button_Listener());
		button_06.setVisible(true);
		
		button_08 = new JButton("重置") ;
		button_08.setBounds((int)(1030*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_08) ;
		button_08.addActionListener(new Button_Listener());
		button_08.setVisible(true);
		
		button_07 = new JButton("删除") ;
		button_07.setBounds((int)(950*screen.getWidth_pro()), (int)(450*screen.getHeight_pro()),(int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(button_07) ;
		button_07.addActionListener(new Button_Listener());
		button_07.setVisible(true);
		
		button_01 = new JButton("专业班级降序") ;
		button_01.setBounds((int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_01.setVisible(true);
		button_01.addActionListener(new Button_Listener());
		this.add(button_01) ;
		
		button_02 = new JButton("升序") ;
		button_02.setBounds((int)(170*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_02.setVisible(true);
		button_02.addActionListener(new Button_Listener());
		this.add(button_02) ;
		
		button_03 = new JButton("日期降序") ;
		button_03.setBounds((int)(330*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_03.setVisible(true);
		button_03.addActionListener(new Button_Listener());
		this.add(button_03) ;
		
		button_04 = new JButton("升序") ;
		button_04.setBounds((int)(450*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_04.setVisible(true);
		button_04.addActionListener(new Button_Listener());
		this.add(button_04) ;
		
		label_02 = new JLabel("检索 :") ;
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
	 * 更新list
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
				if(!rs.getString("finger").equals("无")){
					s = "是" ;
				}else s="否" ;
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
	 * 表格的列宽根据单元格内容自适应
	 */
	public void resizeTable(boolean bool) { 
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
			setTitle("重录指纹") ;
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setBounds((int)(600*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()),(int)(600*screen.getWidth_pro()), (int)(400*screen.getHeight_pro()));
			this.setLayout(null);
			this.setResizable(false) ;
			this.init() ;
			this.setVisible(true);
			
		
			//System.out.println("ok");
		}
		public void init(){
			
			
			label_11 = new JLabel("请按下按钮开始第一次指纹录入") ;
			label_11.setBounds((int)(175*screen.getWidth_pro()), (int)(90*screen.getHeight_pro()),(int)(250*screen.getWidth_pro()), (int)(60*screen.getHeight_pro()));
			label_11.setVisible(true);
			label_11.setForeground(Color.red);
			add(label_11) ;
			
			button_11 = new JButton("第一次指纹") ;
			button_11.setBounds((int)(70*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_11.setEnabled(true);
			button_11.addActionListener(new B_Listener());
			button_11.setVisible(true);
			add(button_11) ;
			
			button_12 = new JButton("第二次指纹") ;
			button_12.setBounds((int)(200*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_12.setEnabled(false);
			button_12.addActionListener(new B_Listener());
			button_12.setVisible(true);
			add(button_12) ;
			
			button_13 = new JButton("合并模板") ;
			button_13.setBounds((int)(330*screen.getWidth_pro()), (int)(220*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_13.setEnabled(false);
			button_13.addActionListener(new B_Listener());
			button_13.setVisible(true);
			add(button_13) ;
			
			button_14 = new JButton("取消") ;
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
						label_11.setText("合成失败！两个特征不是一个手指！请重新录入指纹特征并重试 ");
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
							label_11.setText("模板上传成功 ！");
							
							str = con.getInfo(str) ;
							con.setTest("");
							getDialog().dispose();
							break ;
						}else{
							label_11.setText("模板上传失败，请重试 ！");
							con.setTest("");
							break ;
						}
					}else{
						label_11.setText("发生错误，请重试！");
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
						label_11.setText("第一次指纹录入超时！请重试 ");
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
							label_11.setText("第一次指纹录入完成！");
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
								label_11.setText("第一次指纹录入完成！");
								button_12.setEnabled(true);
							}else{
								con.setTest("");
								label_11.setText("处理失败，请重新录入！");
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
						label_11.setText("第二次指纹录入超时！请重试 ");
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
							label_11.setText("第二次指纹录入完成！");
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
								label_11.setText("第二次指纹录入完成！");
								button_13.setEnabled(true);
							}else{
								con.setTest("");
								label_11.setText("处理失败，请重新录入！");
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
							if(!rs.getString("finger").equals("无")){
								s = "是" ;
							}else s="否" ;
							
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
						
						JOptionPane.showMessageDialog(null, "您已成功修改姓名！");
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
						
						JOptionPane.showMessageDialog(null, "您已成功修改姓名和指纹！");

					}
				}
			}else if(e.getSource().equals(button_07)){
				if(!label_06.getText().equals("")){
					
					int x = JOptionPane.showConfirmDialog(null, 
							"确定要删除"+text_02.getText()+"的数据吗？", "确认删除", JOptionPane.YES_NO_OPTION);
					
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
					JOptionPane.showMessageDialog(null, "请到指纹录入的页面检查连接，确认连接且参数设置正确后重试");
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
		        { "学号", "姓名", "学院", "班级","身份" ,"已录指纹","日期"}; 
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
         * 得到列名 
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
