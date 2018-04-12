package UI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



import dao.Dao;
import BEAN.Screen_parameter;
import BEAN.Send_Data_structure ;
import BEAN.Send_Mode;
import BEAN.Table_Model;

public class Send_Dialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Screen_parameter screen = new Screen_parameter() ;

	private final JPanel contentPanel = new JPanel();
	private JPanel pane ;

	private Dao dao ;
	private JScrollPane scrollPane;
	
	private ArrayList<String> list_name ;
	private ArrayList<String> list_id ;
	private ArrayList<JCheckBox> list_check ;
	
	
	
	private JButton button_31 ;
	private JButton button_32 ;
	private JButton button_33 ;
	private JButton button_34 ;
	private JButton button_35 ;
	
	private Send sen ;
	
	private ConcurrentLinkedQueue<Send_Data_structure> first_send_queue ; //发送队列
	
	private Send_Mode send_mode ;
	private send_que Send_que ;
	
	
	/**
	 * Create the dialog.
	 */
	public Send_Dialog(JFrame frame,Dao dao) {
		super(frame,"发送",true) ;
		this.dao = dao ;
		this.setBounds((int)(460*screen.getWidth_pro()), (int)(240*screen.getHeight_pro()),(int)(1000*screen.getWidth_pro()), (int)(600*screen.getHeight_pro()));
		getContentPane().setLayout(null);
		contentPanel.setLayout(null);
		contentPanel.setBounds(0, 0, (int)(1000*screen.getWidth_pro()), (int)(600*screen.getHeight_pro()));
		getContentPane().add(contentPanel);
		first_send_queue = new ConcurrentLinkedQueue<Send_Data_structure>() ;
		new send_first() ;
		this.Send_que = new send_que(frame) ;
		initContent() ;
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	/**
	 * 初始化 页面
	 */
	private  void initContent(){
		send_mode = new Send_Mode() ;
		list_name = new ArrayList<String>() ;
		list_id = new ArrayList<String>() ;
		list_check = new ArrayList<JCheckBox>() ;
		ResultSet rs = dao.executeQuery("select name,id from address_zigbee ") ;
		try {
			while(rs.next()){
				list_name.add(rs.getString("name")) ;
				list_id.add(rs.getString("id")) ;
				JCheckBox temp = new JCheckBox(rs.getString("name")) ;
				list_check.add(temp) ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pane = new JPanel(null) ;
		//pane.setBounds(5,5,(int)(790*screen.getWidth_pro()), (int)((list_check.size()*60+20)*screen.getHeight_pro()));
		int temp = list_check.size()/3*70+70 ;
		pane.setPreferredSize(new Dimension((int)(760*screen.getWidth_pro()),(int)(temp*screen.getHeight_pro())));
		
		
		scrollPane = new JScrollPane(pane) ;
		scrollPane.setBounds((int)(100*screen.getWidth_pro()), (int)(100*screen.getHeight_pro()),(int)(800*screen.getWidth_pro()), (int)(350*screen.getHeight_pro()));
		addCheck(list_check, pane);
		scrollPane.repaint();
		scrollPane.setVisible(true);
		contentPanel.add(scrollPane) ;
		
		
		button_31 = new JButton("全   选") ;
		button_31.setBounds((int)(700*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_31.addActionListener(new button_Listener());
		button_31.setVisible(true);
		contentPanel.add(button_31) ;
		
		button_32 = new JButton("取消选定") ;
		button_32.setBounds((int)(820*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_32.addActionListener(new button_Listener());
		button_32.setVisible(true);
		contentPanel.add(button_32) ;
		
		button_33 = new JButton("关闭窗口") ;
		button_33.setBounds((int)(350*screen.getWidth_pro()), (int)(480*screen.getHeight_pro()),(int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_33.addActionListener(new button_Listener());
		button_33.setVisible(true);
		contentPanel.add(button_33) ;
		
		button_34 = new JButton("单/多播发送") ;
		button_34.setBounds((int)(600*screen.getWidth_pro()), (int)(480*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_34.addActionListener(new button_Listener());
		button_34.setVisible(true);
		contentPanel.add(button_34) ;
		
		button_35 = new JButton("广 播发送") ;
		button_35.setBounds((int)(760*screen.getWidth_pro()), (int)(480*screen.getHeight_pro()),(int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_35.addActionListener(new button_Listener());
		button_35.setVisible(true);
		contentPanel.add(button_35) ;
		
		
		
	}
	
	public void addCheck(ArrayList<JCheckBox> list,JPanel pane){
		int y = (int)(20*screen.getHeight_pro()) ;
		int[] x = new int[]{150,330,510} ;
		int k = 0 ;
		for(int i=0;i<list.size();i++){
			
			list.get(i).setBounds((int)(x[k]*screen.getWidth_pro()),(int)((i==0?20:y)*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
			pane.add(list.get(i)) ;
			list.get(i).setVisible(true);
			if(k<2)k++ ;
			else{
				k = 0 ;
				y += 70 ;
			}
		}
		
	}
	
	class send_first extends Thread{
		public send_first(){
			this.start();
		}
		public void run(){
			/**
			 * 只要发送队列不为空  则没次队列弹出一个发送数据并且发送出去
			 */
			while(true){
				if(getFirst_send_queue().size()!=0){
					
					
					if(getSend().getSen().getCon_set().getCon()!=null){
					Send_Data_structure data = getFirst_send_queue().poll() ;
					getSend_mode().broadcast(getSend().getSen().getCon_set().getCon(), data);
					send_que t = getSend_que() ;
					int x= t.getCol("发送状态") ;
					int y = t.getRow(data.getStu_id(), data.getGoal_address()) ;
					if(x!=-1&&y!=-1){
						t.getT1().get(y)[x] = "正在发送" ;
						t.getTable_001().repaint();
					}
					
					}
				}
			}
		}
	}
	class button_Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(button_33)){
				getSend().setVisible(false);
			}else if(e.getSource().equals(button_31)){
				for(int i=0 ;i<getList_check().size();i++){
					getList_check().get(i).setSelected(true);
				}
			}else if(e.getSource().equals(button_32)){
				for(int i=0 ;i<getList_check().size();i++){
					getList_check().get(i).setSelected(false);
				}
			}else if(e.getSource().equals(button_35)){
				Vector<String[]> ve = getSend().getSen().getSend_list() ;
				for(int i=0;i<ve.size();i++){
					Send_Data_structure temp = new Send_Data_structure(ve.get(i)[1],ve.get(i)[0],ve.get(i)[2],ve.get(i)[3]) ;
					temp.setGoal_address("FF FF");
					temp.setSend_pattern("广  播");
					String[] tt = new String[]{temp.getStu_id(),temp.getStu_name(),temp.getStu_class(),"--","等待发送"} ;
					getSend_que().getT1().add(tt) ;
					getSend_que().getTable_001().repaint(); 
					getSend().getFirst_send_queue().add(temp) ;
				}
				 getSend().getSen().getButton_30().doClick();
				 getSend().setVisible(false);
				
			}
			else if(e.getSource().equals(button_34)){
				Vector<String[]> ve = getSend().getSen().getSend_list() ;
				Vector<String> address = new Vector<String>() ;
				for(int i=0;i<getList_check().size();i++){
					if(getList_check().get(i).isSelected()){
						address.addElement(getList_id().get(i));
					}
				}
				for(int i = 0;i<ve.size();i++){
					for(int j = 0 ;j<address.size();j++){
						Send_Data_structure temp = new Send_Data_structure(ve.get(i)[1],ve.get(i)[0],ve.get(i)[2],ve.get(i)[3]) ;
						temp.setGoal_address(address.get(j));
						temp.setSend_pattern("单  播");
						String[] tt = new String[]{temp.getStu_id(),temp.getStu_name(),temp.getStu_class(),temp.getGoal_address(),"等待发送"} ;
						getSend_que().getT1().add(tt) ;
						getSend_que().getTable_001().repaint(); 
						getSend().getFirst_send_queue().add(temp) ;
					}
				}
				 getSend().getSen().getButton_30().doClick();
				 getSend().setVisible(false);
				
			}
		}
		
	}
	
	private Send_Dialog getSend(){
		return this ;
	}
	public Send getSen() {
		return sen;
	}
	public void setSen(Send sen) {
		this.sen = sen;
	}
	public ConcurrentLinkedQueue<Send_Data_structure> getFirst_send_queue() {
		return first_send_queue;
	}
	public void setFirst_send_queue(ConcurrentLinkedQueue<Send_Data_structure> first_send_queue) {
		this.first_send_queue = first_send_queue;
	}
	
	public Send_Mode getSend_mode() {
		return send_mode;
	}
	public void setSend_mode(Send_Mode send_mode) {
		this.send_mode = send_mode;
	}
	public ArrayList<JCheckBox> getList_check() {
		return list_check;
	}
	public void setList_check(ArrayList<JCheckBox> list_check) {
		this.list_check = list_check;
	}
	public ArrayList<String> getList_id() {
		return list_id;
	}
	public void setList_id(ArrayList<String> list_id) {
		this.list_id = list_id;
	}
	
	public send_que getSend_que() {
		return Send_que;
	}
	public void setSend_que(send_que send_que) {
		Send_que = send_que;
	}

	class send_que extends JDialog{
		
		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;
		private JTable table_001 ;//发送队列  
		private JTable table_002 ;//发送历史
		private JButton button_001 ;
		private JButton button_002 ;
		private Vector<String[]> t1 = new Vector<String[]>() ;
		private String[] t1_name = new String[]{"学号/教职工号","姓名","专业班级","目的地","发送状态"} ;
		
		private JPanel contentPanel_01 ;
		public send_que(JFrame frame){
			super(frame,"发送",true) ;
			contentPanel_01 = new JPanel() ;
			this.setBounds((int)(460*screen.getWidth_pro()), (int)(240*screen.getHeight_pro()),(int)(1000*screen.getWidth_pro()), (int)(600*screen.getHeight_pro()));
			getContentPane().setLayout(null);
			contentPanel_01.setLayout(null);
			contentPanel_01.setBounds(0, 0, (int)(1000*screen.getWidth_pro()), (int)(600*screen.getHeight_pro()));
			getContentPane().add(contentPanel_01);
			initContent_que() ;
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			
		}
		
		public  void initContent_que(){
			//表一：显示发送的学号，姓名，专业班级，发送目的地，发送状态
			//打开显示目前正在发送的队列，当收到应答的时候发送完成，表一删除该条记录 ，并且将这条记录存入数据库
			table_001 = new JTable(new Table_Model(t1,t1_name)) ;
			table_001.setBounds((int)(0*screen.getWidth_pro()), (int)(0*screen.getHeight_pro()),(int)(800*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()));
			
			scrollPane = new JScrollPane(table_001) ;
			scrollPane.setBounds((int)(50*screen.getWidth_pro()), (int)(80*screen.getHeight_pro()),(int)(900*screen.getWidth_pro()), (int)(340*screen.getHeight_pro()));
			scrollPane.setVisible(true);
			scrollPane.addComponentListener(new ComponentAdapter() { 
	            @Override 
	            public void componentResized(ComponentEvent e) { 
	                resizeTable(true,scrollPane,table_001) ;
	            } 
			}); 
			contentPanel_01.add(scrollPane) ;
			
			button_001 = new JButton("关闭窗口") ;
			button_001.setBounds((int)(800*screen.getWidth_pro()), (int)(430*screen.getHeight_pro()),(int)(150*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_001.addActionListener(new bu_Listener());
			contentPanel_01.add(button_001) ;
			button_001.setVisible(true);
			
			button_002 = new JButton("发送历史") ;
			button_002.setBounds((int)(50*screen.getWidth_pro()), (int)(30*screen.getHeight_pro()),(int)(150*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			contentPanel_01.add(button_002) ;
			button_002.setVisible(true);
		}
		class bu_Listener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().equals(button_001)){
					getSend_que().setVisible(false);
				}
			}
			
		}
		/**
		 * 得到 ID和address所在的行号
		 * @param id
		 * @param address
		 * @return
		 */
		public int getRow(String id,String address){
			int y = -1 ;
			int x = this.getCol("目的地") ;
			int z = this.getCol("学号/教职工号") ;
			Table_Model tt = (Table_Model) this.getTable_001().getModel() ;
			for(int i=0;i<tt.getRowCount();i++){
				if(tt.getValueAt(i, z).equals(id)&&tt.getValueAt(i, x).equals(address)){
					return i ;
				}
			}
			return y ;
		}
		/**
		 * 由某一列的名称得到列号
		 * @param name
		 * @return
		 */
		public int getCol(String name){
			int x  = -1  ;
			Table_Model tt = (Table_Model) this.getTable_001().getModel() ;
			for(int i=0;i<tt.getColumnCount();i++){
				if(tt.getColumnName(i).equals(name)){
					return i ;
				}
			}
			return x ;
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

		public Vector<String[]> getT1() {
			return t1;
		}

		public void setT1(Vector<String[]> t1) {
			this.t1 = t1;
		}

		public JTable getTable_001() {
			return table_001;
		}

		public void setTable_001(JTable table_001) {
			this.table_001 = table_001;
		}

	}
}
