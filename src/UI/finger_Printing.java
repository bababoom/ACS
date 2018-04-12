package UI;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.Dao;
import BEAN.Screen_parameter;
import BEAN.UART_con;

public class finger_Printing extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dao dao ;
	
	private display dis ;
	private String str ="" ;
	
	private UART_con con ;
	private Screen_parameter screen = new Screen_parameter() ;//为了获取屏幕的参数，使得在任何电脑上的大小比例保持一致
	//label 
	private JLabel label_01 ;
	private JLabel label_02 ;
	private JLabel label_03 ;
	private JLabel label_04 ;
	private JLabel label_05 ;
	private JLabel label_06 ;
	private JLabel label_07 ;
	private JLabel label_08 ;
	private JLabel label_09 ;
	private JLabel label_10 ;
	private JLabel label_11 ;
	
	//JCOMBOBOX
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_01 ;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_02 ;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_03 ;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_04 ;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_05 ;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_06 ;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_07 ;
	
	//button
	private JButton button_01 ;
	private JButton button_02 ;
	private JButton button_03 ;
	private JButton button_04 ;
	private JButton button_05 ;
	private JButton button_06 ;
	private JButton button_07 ;
	private JButton button_08 ;
	private JButton button_09 ;
	
	//textArea
	private JTextArea display_text ;
	
	//textfield
	private JTextField text_01 ;
	private JTextField text_02 ;
	
	//滚动条
	private JScrollPane pane ;
	

	/**
	 * Create the panel.
	 */
	public finger_Printing(Dao dao) {
		this.setBounds(0, (int)(150*screen.getHeight_pro()), (int)(1400*screen.getWidth_pro()), (int)(550*screen.getHeight_pro()));
		this.setLayout(null); 
		this.setVisible(true);
		this.dao =dao ;
		initial() ;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initial(){

		con = new UART_con() ;
		con.setOther(this);
		
		label_01 = new JLabel("可用串口： ") ;
		label_01.setBounds((int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		label_01.setVisible(true);
		this.add(label_01) ;
		
		button_05 = new JButton() ;
		button_05.setBounds((int)(340*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(40*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		ImageIcon icon = new ImageIcon("src/images/fresh.png");
		icon.setImage(icon.getImage().getScaledInstance((int)(40*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),Image.SCALE_DEFAULT ));
		button_05.setIcon(icon);
		button_05.setVisible(true);
		
		button_05.addActionListener(new Listener());
		this.add(button_05) ;
		
		button_01 = new JButton("打开串口") ;
		button_01.setBounds((int)(390*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_01.setVisible(true);
		button_01.setEnabled(false);
		button_01.addActionListener(new Listener());
		this.add(button_01) ;
		
		
		con.update_UTAR_list();
		comboBox_01 = new JComboBox(con.getList_Model()) ;
		comboBox_01.setSelectedIndex(0);
		comboBox_01.setBounds((int)(170*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(comboBox_01) ;
		comboBox_01.addItemListener(new itemListener());
		//comboBox_01.addPopupMenuListener(new PopListener());
		comboBox_01.setVisible(true);
		
		label_10 = new JLabel() ;
		label_10.setBounds((int)(130*screen.getWidth_pro()), (int)(90*screen.getHeight_pro()), (int)(350*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(label_10) ;
		label_10.setVisible(true);
		
		
		label_02 = new JLabel("波特率： ") ;
		label_02.setBounds((int)(50*screen.getWidth_pro()), (int)(150*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		label_02.setVisible(true);
		this.add(label_02) ;
		label_02.setEnabled(false);
		
		comboBox_02 = new JComboBox(con.getArr_Model(con.getBaud_list())) ;
		comboBox_02.setSelectedIndex(8);
		comboBox_02.setBounds((int)(170*screen.getWidth_pro()), (int)(150*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(comboBox_02) ;
		comboBox_02.setVisible(true);
		comboBox_02.setEnabled(false);
		
		
		label_03 = new JLabel("数据位： ") ;
		label_03.setBounds((int)(50*screen.getWidth_pro()), (int)(230*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		label_03.setVisible(true);
		this.add(label_03) ;
		label_03.setEnabled(false);
		
		comboBox_03 = new JComboBox(con.getArr_Model(con.getDatabits_list())) ;
		comboBox_03.setSelectedIndex(3);
		comboBox_03.setBounds((int)(170*screen.getWidth_pro()), (int)(230*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(comboBox_03) ;
		comboBox_03.setVisible(true);
		comboBox_03.setEnabled(false);
		
		label_04 = new JLabel("停止位： ") ;
		label_04.setBounds((int)(50*screen.getWidth_pro()), (int)(310*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		label_04.setVisible(true);
		this.add(label_04) ;
		label_04.setEnabled(false);
		
		comboBox_04 = new JComboBox(con.getArr_Model(con.getStopbits_list())) ;
		comboBox_04.setSelectedIndex(0);
		comboBox_04.setBounds((int)(170*screen.getWidth_pro()), (int)(310*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(comboBox_04) ;
		comboBox_04.setVisible(true);
		comboBox_04.setEnabled(false);
		
		label_05 = new JLabel("检验位： ") ;
		label_05.setBounds((int)(50*screen.getWidth_pro()), (int)(390*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		label_05.setVisible(true);
		this.add(label_05) ;
		label_05.setEnabled(false);
		
		comboBox_05 = new JComboBox(con.getArr_Model(con.getParity_list())) ;
		comboBox_05.setSelectedIndex(0);
		comboBox_05.setBounds((int)(170*screen.getWidth_pro()), (int)(390*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		this.add(comboBox_05) ;
		comboBox_05.setVisible(true);
		comboBox_05.setEnabled(false);
		
		button_02 = new JButton("确定") ;
		button_02.setBounds((int)(110*screen.getWidth_pro()), (int)(460*screen.getHeight_pro()), (int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_02.setVisible(true);
		button_02.setEnabled(false);
		button_02.addActionListener(new Listener());
		this.add(button_02) ;
		
		display_text = new JTextArea() ;
		display_text.setBounds((int)(600*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()), (int)(600*screen.getWidth_pro()), (int)(230*screen.getHeight_pro()));
		display_text.setBackground(new Color(127,255,212));
		display_text.setForeground(Color.RED);
		display_text.setEditable(false);
		display_text.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
		pane=new JScrollPane(display_text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setBounds((int)(600*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()), (int)(600*screen.getWidth_pro()), (int)(230*screen.getHeight_pro()));
		pane.setVisible(true);
		display_text.setBorder(BorderFactory.createEtchedBorder());
		display_text.setVisible(true);
		//this.add(display_text) ;
		this.add(pane) ;
		
		button_09 = new JButton("清除消息") ;
		button_09.setBounds((int)(1230*screen.getWidth_pro()), (int)(225*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_09.setVisible(true);
		button_09.addActionListener(new Listener());
		this.add(button_09) ;
		/***********************************************************************************/
		
		label_11 = new JLabel() ;
		label_11.setBounds((int)(600*screen.getWidth_pro()), (int)(280*screen.getHeight_pro()), (int)(600*screen.getWidth_pro()), (int)(30*screen.getHeight_pro()));
		label_11.setVisible(true);
		label_11.setForeground(Color.RED);
		this.add(label_11) ;
		
		label_06 = new JLabel("学号/职工号 :") ;
		label_06.setBounds((int)(440*screen.getWidth_pro()), (int)(320*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_06.setEnabled(false);
		label_06.setVisible(true);
		this.add(label_06) ;
		
		text_01 = new JTextField() ;
		text_01.setBounds((int)(550*screen.getWidth_pro()), (int)(320*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		text_01.setVisible(true);
		text_01.setEnabled(false);
		this.add(text_01) ;
		
		label_07 = new JLabel("职  位 :") ;
		label_07.setBounds((int)(680*screen.getWidth_pro()), (int)(320*screen.getHeight_pro()), (int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_07.setEnabled(false);
		label_07.setVisible(true);
		this.add(label_07) ;
		
		comboBox_06 = new JComboBox(con.getWork()) ;
		comboBox_06.setBounds((int)(760*screen.getWidth_pro()), (int)(320*screen.getHeight_pro()), (int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		comboBox_06.setVisible(true);
		comboBox_06.setEnabled(false);
		this.add(comboBox_06) ;
		
		label_08 = new JLabel("学  院 :") ;
		label_08.setBounds((int)(920*screen.getWidth_pro()), (int)(320*screen.getHeight_pro()), (int)(70*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_08.setEnabled(false);
		label_08.setVisible(true);
		this.add(label_08) ;
		
		comboBox_07 = new JComboBox(con.getAcademy()) ;
		comboBox_07.setBounds((int)(1000*screen.getWidth_pro()), (int)(320*screen.getHeight_pro()), (int)(200*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		comboBox_07.setVisible(true);
		comboBox_07.setEnabled(false);
		this.add(comboBox_07) ;
		
		label_09 = new JLabel("    姓  名 :") ;
		label_09.setBounds((int)(440*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		label_09.setEnabled(false);
		label_09.setVisible(true);
		this.add(label_09) ;
		
		text_02 = new JTextField() ;
		text_02.setBounds((int)(550*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		text_02.setVisible(true);
		text_02.setEnabled(false);
		this.add(text_02) ;
		
		button_06 = new JButton("指纹录入(一)") ;
		button_06.setBounds((int)(700*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()), (int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_06.setVisible(true);
		button_06.setEnabled(false);
		button_06.addActionListener(new Listener());
		this.add(button_06) ;
		
		button_07 = new JButton("指纹录入(二)") ;
		button_07.setBounds((int)(850*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()), (int)(120*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_07.setVisible(true);
		button_07.setEnabled(false);
		button_07.addActionListener(new Listener());
		this.add(button_07) ;
		
		button_08 = new JButton("合并模板") ;
		button_08.setBounds((int)(1000*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()), (int)(80*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_08.setVisible(true);
		button_08.setEnabled(false);
		button_08.addActionListener(new Listener());
		this.add(button_08) ;
		
		
		button_03 = new JButton("上传") ;
		button_03.setBounds((int)(750*screen.getWidth_pro()), (int)(440*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_03.setVisible(true);
		button_03.setEnabled(false);
		button_03.addActionListener(new Listener());
		this.add(button_03) ;
		
		button_04 = new JButton("重置") ;
		button_04.setBounds((int)(950*screen.getWidth_pro()), (int)(440*screen.getHeight_pro()), (int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
		button_04.setVisible(true);
		button_04.setEnabled(false);
		button_04.addActionListener(new Listener());
		this.add(button_04) ;
		
		
	}
	
	
	
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}



	public UART_con getCon() {
		return con;
	}
	public void setCon(UART_con con) {
		this.con = con;
	}



	class Listener implements ActionListener{

		@SuppressWarnings({ "unchecked", "deprecation" })
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String port_name ="";
			if(e.getSource()==button_01&&button_01.getLabel().equals("打开串口")){
					port_name = comboBox_01.getSelectedItem().toString() ;
					setDis_con_off() ;
					try {
						
						
						getCon().port_con(comboBox_01.getSelectedItem().toString());
						label_02.setEnabled(true);
						label_03.setEnabled(true);
						label_04.setEnabled(true);
						label_05.setEnabled(true);
						comboBox_02.setEnabled(true);
						comboBox_03.setEnabled(true);
						comboBox_04.setEnabled(true);
						comboBox_05.setEnabled(true);
						button_02.setEnabled(true);
						label_10.setForeground(Color.green);
						label_10.setText("正在与"+comboBox_01.getSelectedItem().toString()+"通信中");
						button_01.setLabel("关闭串口");
						button_01.repaint();
						label_10.repaint();
					} catch (TooManyListenersException e1) {
						// TODO Auto-generated catch block
						label_10.setForeground(Color.RED);
						label_10.setText("too many Listener");
						label_10.repaint();
						//e1.printStackTrace();
					} catch (PortInUseException e1) {
						// TODO Auto-generated catch block
						label_10.setForeground(Color.red);
						label_10.setText(port_name+"正在使用中");
						label_10.repaint();
						//e1.printStackTrace();
						
					}
					
				
			}else if(e.getSource().equals(button_05)){
				getCon().update_UTAR_list();
				comboBox_01 .setModel(getCon().getList_Model());
				comboBox_01.setSelectedIndex(0);
				comboBox_01.repaint();
				comboBox_01.addItemListener(new itemListener());
		
			
			}else if(e.getSource()==button_01&&button_01.getLabel().equals("关闭串口")){
				
			
				setDis_con_off() ;
				getCon().closeSerialPort();
				label_02.setEnabled(false);
				label_03.setEnabled(false);
				label_04.setEnabled(false);
				label_05.setEnabled(false);
				comboBox_02.setEnabled(false);
				comboBox_03.setEnabled(false);
				comboBox_04.setEnabled(false);
				comboBox_05.setEnabled(false);
				button_02.setEnabled(false);
				label_10.setText("");
				button_01.setLabel("打开串口");
				
				label_06.setEnabled(false);
				label_07.setEnabled(false);
				label_08.setEnabled(false);
				label_09.setEnabled(false);
				text_01.setEnabled(false);
				text_02.setEnabled(false);
				comboBox_06.setEnabled(false);
				comboBox_07.setEnabled(false);
				button_03.setEnabled(false);
				button_04.setEnabled(false);
				button_06.setEnabled(false);
				button_07.setEnabled(false);
				button_08.setEnabled(false);
				
				if(comboBox_01.getSelectedItem().equals("--请选择--")){
					button_01.setEnabled(false);
				}
				button_01.repaint();
				label_10.repaint();
			
			}else if(e.getSource().equals(button_02)){
			
				try {
					getCon().setParameter(Integer.valueOf(comboBox_02.getSelectedItem().toString()), Integer.valueOf(comboBox_03.getSelectedItem().toString()), Integer.valueOf(comboBox_04.getSelectedItem().toString()), getCon().getParity().get(comboBox_05.getSelectedItem().toString()));
					setDis_con_on() ;
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					
				} catch (UnsupportedCommOperationException e1) {
					// TODO Auto-generated catch block
					label_10.setForeground(Color.red);
					label_10.setText("参数配置错误");
					label_10.repaint();
				}
				label_06.setEnabled(true);
				label_07.setEnabled(true);
				label_08.setEnabled(true);
				label_09.setEnabled(true);
				text_01.setEnabled(true);
				text_02.setEnabled(true);
				comboBox_06.setEnabled(true);
				comboBox_07.setEnabled(true);
				button_03.setEnabled(true);
				button_04.setEnabled(true);
				button_06.setEnabled(true);
 				
				
			}else if(e.getSource().equals(button_06)){
				
				new Thread(new F_button()).start();
				
				
			}else if(e.getSource().equals(button_07)){
				new Thread(new F_button_02()).start();
			}else if(e.getSource().equals(button_08)){
				new Thread(new HB()).start();
			}else if(e.getSource().equals(button_09)){
				display_text.setText("");
			}else if(e.getSource().equals(button_03)){
				if(text_01.getText().trim().equals("")||comboBox_07.getSelectedItem().equals("--请选择--")||text_02.getText().trim().equals("")||str.equals("")){
					
					label_11.setText("请完善相关信息");
				}else if(comboBox_06.getSelectedItem().equals("学生")&&text_01.getText().trim().length()!=10){
					label_11.setText("请检查学号或职工号的长度后重试（学号：10位，职工号：8位）");
				}else if(comboBox_06.getSelectedItem().equals("学生")&&text_01.getText().trim().length()==10&&!getCon().isNum(text_01.getText().trim())){
					
						label_11.setText("请检查学号或职工号是否有误！");
					
					
				}else if(comboBox_06.getSelectedItem().equals("教职工")&&text_01.getText().trim().length()!=8){
					label_11.setText("请检查学号或职工号的长度后重试（学号：10位，职工号：8位）");
				}else if(comboBox_06.getSelectedItem().equals("教职工")&&text_01.getText().trim().length()==8&&!getCon().isNum(text_01.getText().trim())){
					
						label_11.setText("请检查学号或职工号是否有误！");
					
					
				}else if(dao.isContainID(text_01.getText())){
					label_11.setText("此id已存在");
				}else{
					label_11.setText("");
					Date date = new Date() ;
					SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String sql = "insert into Info(id,name,work,academy,finger,date) values('"+text_01.getText().trim()+"','"+ 
					text_02.getText()+"','"+comboBox_06.getSelectedItem().toString().trim()+"','"+comboBox_07.getSelectedItem().toString().trim()+"','"
					+str+"','"+sf.format(date)+"')";
					if(!dao.execute(sql)){
						display_text.setText(display_text.getText()+"上传失败请重试！");
					}else{
						str="" ;
						label_11.setText("");
						text_01.setText("");
						text_02.setText("");
						comboBox_06.setSelectedIndex(0);
						comboBox_07.setSelectedIndex(0);
						button_07.setEnabled(false);
						button_08.setEnabled(false);
						display_text.setText(display_text.getText()+"已上传至数据库！");
					}
					
					
				}
			}else if(e.getSource().equals(button_04)){
				text_01.setText("");
				text_02.setText("");
				comboBox_06.setSelectedIndex(0);
				comboBox_07.setSelectedIndex(0);
				button_07.setEnabled(false);
				button_08.setEnabled(false);
				str = "" ;
			}
		}
		
	}
	
	
	public void  setDis_con_on(){
		if(dis!=null){
			dis.setCon(con);
		}
	}
	
	public void  setDis_con_off(){
		if(dis!=null){
			dis.setCon(null);
		}
	}
	public display getDis() {
		return dis;
	}
	public void setDis(display dis) {
		this.dis = dis;
	}



	class HB implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			display_text.setText(display_text.getText()+"合成模板中...\n");
			while(true){
				getCon().write("EF 01 FF FF FF FF 01 00 03 05 00 09");
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 0A 00 14".replace(" ", ""))){
					display_text.setText(display_text.getText()+"合成失败！两个特征不是一个手指！请重新录入指纹特征并重试 \n");
					getCon().setTest("");
					break ;
				}else if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
					display_text.setText(display_text.getText()+"合成成功，准备上传... \n");
					getCon().setTest("");
					getCon().write("EF 01 FF FF FF FF 01 00 04 08 01 00 0E");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String temp = getCon().getTest().trim().replace(" ", "").substring(18,20) ;
					//System.out.println(temp);
					if(temp.equals("00")){
						str = getCon().getTest().trim() ;
						display_text.setText(display_text.getText()+"模板上传成功 ！\n");
						str = getCon().getInfo(str) ;
						getCon().setTest("");
						break ;
					}else{
						display_text.setText(display_text.getText()+"模板上传失败，请重试 ！\n");
						getCon().setTest("");
						break ;
					}
				}else{
					display_text.setText(display_text.getText()+"发生错误，请重试！\n");
					getCon().setTest("");
					break ;
				}
			}
		}
		
	}
	
	public void update(){
		setDis_con_off() ;
		getCon().closeSerialPort();
		label_02.setEnabled(false);
		label_03.setEnabled(false);
		label_04.setEnabled(false);
		label_05.setEnabled(false);
		comboBox_02.setEnabled(false);
		comboBox_03.setEnabled(false);
		comboBox_04.setEnabled(false);
		comboBox_05.setEnabled(false);
		button_02.setEnabled(false);
		label_10.setText("");
		button_01.setLabel("打开串口");
		
		label_06.setEnabled(false);
		label_07.setEnabled(false);
		label_08.setEnabled(false);
		label_09.setEnabled(false);
		text_01.setEnabled(false);
		text_02.setEnabled(false);
		comboBox_06.setEnabled(false);
		comboBox_07.setEnabled(false);
		button_03.setEnabled(false);
		button_04.setEnabled(false);
		button_06.setEnabled(false);
		button_07.setEnabled(false);
		button_08.setEnabled(false);
		
		label_10.setForeground(Color.RED);
		label_10.setText("设备被意外拔出，请插好设备点击刷新重新连接");
		getCon().update_UTAR_list();
		comboBox_01 .setModel(getCon().getList_Model());
		comboBox_01.setSelectedIndex(0);
		comboBox_01.addItemListener(new itemListener());
		
		if(comboBox_01.getSelectedItem().equals("--请选择--")){
			button_01.setEnabled(false);
		}
		
	}
	
	/**************************************************************/
	
	class F_button implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int count = 0 ;
			display_text.setText(display_text.getText()+"正在录入第一次指纹：\n");
			display_text.repaint();
			while(true){
				if(count ==6){
					display_text.setText(display_text.getText()+"超时！请重试 \n");
					break ;
				}
				getCon().write("EF 01 FF FF FF FF 01  00 03 01  00 05");
				count++ ;
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count ++ ;
				if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
					getCon().setTest("");
					display_text.setText(display_text.getText()+"第一次指纹图像录入成功！\n");
					display_text.setText(display_text.getText()+"正在处理中，请稍后\n");
					getCon().write("EF 01 FF FF FF FF 01 00 04 02 01 00 08");
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
						getCon().setTest("");
						display_text.setText(display_text.getText()+"第一次指纹录入完成！\n");
						button_07.setEnabled(true);
					}else{
						getCon().write("EF 01 FF FF FF FF 01 00 04 02 01 00 08");
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
							getCon().setTest("");
							display_text.setText(display_text.getText()+"第一次指纹录入完成！\n");
							button_07.setEnabled(true);
						}else{
							getCon().setTest("");
							display_text.setText(display_text.getText()+"处理失败，请重新录入！\n");
						}
					}
					break ;
				}else{
					getCon().setTest("");
				}
			}
		}
		
	}
	
	class F_button_02 implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int count = 0 ;
			display_text.setText(display_text.getText()+"正在录入第二次指纹：\n");
			display_text.repaint();
			while(true){
				if(count ==6){
					display_text.setText(display_text.getText()+"超时！请重试 \n");
					break ;
				}
				getCon().write("EF 01 FF FF FF FF 01  00 03 01  00 05");
				count++ ;
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count ++ ;
				if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
					getCon().setTest("");
					display_text.setText(display_text.getText()+"第二次指纹图像录入成功！\n");
					display_text.setText(display_text.getText()+"正在处理中，请稍后\n");
					getCon().write("EF 01 FF FF FF FF 01 00 04 02 02 00 09");
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
						getCon().setTest("");
						display_text.setText(display_text.getText()+"第二次指纹录入完成！\n");
						button_08.setEnabled(true);
						
					}else{
						getCon().write("EF 01 FF FF FF FF 01 00 04 02 02 00 09");
						try {
							Thread.sleep(800);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(getCon().getTest().trim().replace(" ", "").equals("EF 01 FF FF FF FF 07 00 03 00 00 0A".replace(" ", ""))){
							getCon().setTest("");
							display_text.setText(display_text.getText()+"第二次指纹录入完成！\n");
							button_08.setEnabled(true);
							
						}else{
							getCon().setTest("");
							display_text.setText(display_text.getText()+"处理失败，请重新录入！\n");
						}
					}
					break ;
				}else{
					getCon().setTest("");
				}
			}
		}
		
	}
	
	
	class itemListener implements ItemListener{

		@SuppressWarnings("deprecation")
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getStateChange()==ItemEvent.SELECTED){
				if(e.getSource().equals(comboBox_01)){
					
					// System.out.println(1);
					if(!e.getItem().toString().equals("--请选择--")||button_01.getLabel().equals("关闭串口")){
						button_01.setEnabled(true);
					}else {
						button_01.setEnabled(false);
					}
				
				}
			}
		}
		
	}
}
