package UI;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.awt.BorderLayout;

import BEAN.Screen_parameter;
import BEAN.UART_con;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TooManyListenersException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import UI.finger_Printing.F_button;
import UI.finger_Printing.F_button_02;
import UI.finger_Printing.HB;
import UI.finger_Printing.itemListener;
import BEAN.UART_con;


public class CON_Set extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6706216114182619414L;
	private final JPanel pane = new JPanel();
	private Screen_parameter screen = new Screen_parameter() ;//为了获取屏幕的参数，使得在任何电脑上的大小比例保持一致
	
	private UART_con con ;
	private JLabel label_01;
	private JLabel label_10;
	private JLabel label_02;
	private JLabel label_03;
	private JLabel label_04;
	private JLabel label_05;
	
	private JButton button_05;
	private JButton button_01;
	private JButton button_02 ;
	private JButton button_03 ;
	
	private JComboBox comboBox_01;
	private JComboBox comboBox_02;
	private JComboBox comboBox_03;
	private JComboBox comboBox_04;
	private JComboBox comboBox_05;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			CON_Set dialog = new CON_Set();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	/**
	 * Create the dialog.
	 */
	public CON_Set(JFrame frame) {
		super(frame,"设置串口",true) ;
		this.setSize(700, 500);
		setLocationRelativeTo(null) ;
		getContentPane().setLayout(null);
		add(pane) ;
		pane.setBounds(0, 0, 700, 500);
		pane.setLayout(null);
		pane.setVisible(true);
		
		init() ;
		
		
	}
	
	/**
	 * 初始化页面
	 */
	public void init()
	{
			
			con = new UART_con() ;
			
			this.getCon().setCON_SET(this);
			label_01 = new JLabel("可用串口： ") ;
			label_01.setBounds((int)(50*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
			label_01.setVisible(true);
			pane.add(label_01) ;
			
			button_05 = new JButton() ;
			button_05.setBounds((int)(340*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(40*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			ImageIcon icon = new ImageIcon("src/images/fresh.png");
			icon.setImage(icon.getImage().getScaledInstance((int)(40*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),Image.SCALE_DEFAULT ));
			button_05.setIcon(icon);
			button_05.setVisible(true);
			button_05.addActionListener(new Listener());
			pane.add(button_05) ;
			
			button_01 = new JButton("打开串口") ;
			button_01.setBounds((int)(390*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_01.setVisible(true);
			button_01.setEnabled(false);
			button_01.addActionListener(new Listener());
			pane.add(button_01) ;
			
			
			con.update_UTAR_list();
			comboBox_01 = new JComboBox(con.getList_Model()) ;
			comboBox_01.setSelectedIndex(0);
			comboBox_01.setBounds((int)(170*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			pane.add(comboBox_01) ;
			comboBox_01.addItemListener(new itemListener());
			comboBox_01.setVisible(true);
			
			label_10 = new JLabel() ;
			label_10.setBounds((int)(180*screen.getWidth_pro()), (int)(90*screen.getHeight_pro()), (int)(250*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			pane.add(label_10) ;
			label_10.setVisible(true);
			
			
			label_02 = new JLabel("波特率： ") ;
			label_02.setBounds((int)(180*screen.getWidth_pro()), (int)(150*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			label_02.setVisible(true);
			pane.add(label_02) ;
			label_02.setEnabled(false);
			
			comboBox_02 = new JComboBox(con.getArr_Model(con.getBaud_list())) ;
			comboBox_02.setSelectedIndex(8);
			comboBox_02.setBounds((int)(300*screen.getWidth_pro()), (int)(150*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			pane.add(comboBox_02) ;
			comboBox_02.setVisible(true);
			comboBox_02.setEnabled(false);
			
			
			label_03 = new JLabel("数据位： ") ;
			label_03.setBounds((int)(180*screen.getWidth_pro()), (int)(200*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			label_03.setVisible(true);
			pane.add(label_03) ;
			label_03.setEnabled(false);
			
			comboBox_03 = new JComboBox(con.getArr_Model(con.getDatabits_list())) ;
			comboBox_03.setSelectedIndex(3);
			comboBox_03.setBounds((int)(300*screen.getWidth_pro()), (int)(200*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			pane.add(comboBox_03) ;
			comboBox_03.setVisible(true);
			comboBox_03.setEnabled(false);
			
			label_04 = new JLabel("停止位： ") ;
			label_04.setBounds((int)(180*screen.getWidth_pro()), (int)(250*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			label_04.setVisible(true);
			pane.add(label_04) ;
			label_04.setEnabled(false);
			
			comboBox_04 = new JComboBox(con.getArr_Model(con.getStopbits_list())) ;
			comboBox_04.setSelectedIndex(0);
			comboBox_04.setBounds((int)(300*screen.getWidth_pro()), (int)(250*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			pane.add(comboBox_04) ;
			comboBox_04.setVisible(true);
			comboBox_04.setEnabled(false);
			
			label_05 = new JLabel("检验位： ") ;
			label_05.setBounds((int)(180*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			label_05.setVisible(true);
			pane.add(label_05) ;
			label_05.setEnabled(false);
			
			comboBox_05 = new JComboBox(con.getArr_Model(con.getParity_list())) ;
			comboBox_05.setSelectedIndex(0);
			comboBox_05.setBounds((int)(300*screen.getWidth_pro()), (int)(300*screen.getHeight_pro()), (int)(160*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			pane.add(comboBox_05) ;
			comboBox_05.setVisible(true);
			comboBox_05.setEnabled(false);
			
			button_02 = new JButton("确 定") ;
			button_02.setBounds((int)(180*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_02.setVisible(true);
			button_02.setEnabled(false);
			button_02.addActionListener(new Listener());
			pane.add(button_02) ;
			
			
			button_03 = new JButton("关闭窗口") ;
			button_03.setBounds((int)(420*screen.getWidth_pro()), (int)(380*screen.getHeight_pro()),(int)(100*screen.getWidth_pro()), (int)(40*screen.getHeight_pro()));
			button_03.setVisible(true);
			button_03.addActionListener(new Listener());
			pane.add(button_03) ;
			
			
	}
	
	/**
	 * 
	 * @author Administrator
	 * 这个类是用来对组件的事件作出响应的
	 *
	 */
	class Listener implements ActionListener{

		@SuppressWarnings({ "unchecked", "deprecation" })
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String port_name ="";
			if(e.getSource()==button_01&&button_01.getLabel().equals("打开串口")){
				
					port_name = comboBox_01.getSelectedItem().toString() ;
					try {
						getCon().port_con(comboBox_01.getSelectedItem().toString());
						//System.out.println(con.getSerialPort().getName());
						button_02.setEnabled(true);
						label_02.setEnabled(true);
						label_03.setEnabled(true);
						label_04.setEnabled(true);
						label_05.setEnabled(true);
						comboBox_02.setEnabled(true);
						comboBox_03.setEnabled(true);
						comboBox_04.setEnabled(true);
						comboBox_05.setEnabled(true);
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
				//comboBox_01.addItemListener(new itemListener());
		
			
			}else if(e.getSource()==button_01&&button_01.getLabel().equals("关闭串口")){
				
				getCon().closeSerialPort();
				//System.out.println(con.getSerialPort()==null);
				button_02.setEnabled(false);
				label_02.setEnabled(false);
				label_03.setEnabled(false);
				label_04.setEnabled(false);
				label_05.setEnabled(false);
				comboBox_02.setEnabled(false);
				comboBox_03.setEnabled(false);
				comboBox_04.setEnabled(false);
				comboBox_05.setEnabled(false);
				label_10.setText("");
				button_01.setLabel("打开串口");
				
				
				
				if(comboBox_01.getSelectedItem().equals("--请选择--")){
					button_01.setEnabled(false);
				}
				button_01.repaint();
				label_10.repaint();
			
			}else if(e.getSource().equals(button_02)){
			
				try {
					getCon().setParameter(Integer.valueOf(comboBox_02.getSelectedItem().toString()), Integer.valueOf(comboBox_03.getSelectedItem().toString()), Integer.valueOf(comboBox_04.getSelectedItem().toString()), getCon().getParity().get(comboBox_05.getSelectedItem().toString()));
					label_10.setText("已配置参数完成");
					getDialog().setVisible(false);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					
				} catch (UnsupportedCommOperationException e1) {
					// TODO Auto-generated catch block
					label_10.setForeground(Color.red);
					label_10.setText("参数配置错误");
					
				}
			}else if(e.getSource().equals(button_03)){
				getDialog().setVisible(false);
			}
					
				
			}
		}
	
	/**
	 * 
	 * @author Administrator
	 *这个类是用来对下拉框的选择事件作出响应的
	 */
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
	public void update(){
		
		//System.out.println(con.getSerialPort()==null);
		button_02.setEnabled(false);
		label_02.setEnabled(false);
		label_03.setEnabled(false);
		label_04.setEnabled(false);
		label_05.setEnabled(false);
		comboBox_02.setEnabled(false);
		comboBox_03.setEnabled(false);
		comboBox_04.setEnabled(false);
		comboBox_05.setEnabled(false);
		label_10.setText("");
		button_01.setLabel("打开串口");
		
		getCon().update_UTAR_list();
		comboBox_01 .setModel(getCon().getList_Model());
		comboBox_01.setSelectedIndex(0);
		
		if(comboBox_01.getSelectedItem().equals("--请选择--")){
			button_01.setEnabled(false);
		}
	}
	/**
	 * 
	 * @return
	 * 得到串口的对象
	 */
	public UART_con getCon() {
		return con;
	}
	public void setCon(UART_con con) {
		this.con = con;
	}
	
	public JDialog getDialog(){
		return this ;
	}

}
