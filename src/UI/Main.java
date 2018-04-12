package UI;


import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import BEAN.Screen_parameter;

import javax.swing.JTabbedPane;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import dao.Dao;
public class Main extends JFrame implements ChangeListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dao dao ;
	private Screen_parameter screen = new Screen_parameter() ;//为了获取屏幕的参数，使得在任何电脑上的大小比例保持一致
	private display_Time time ;
	private JTabbedPane Tab ;
	private finger_Printing finger ;
	private display dis ;
	private Send send ;
	private CON_Set con_set ;
	private Send_Dialog send_Dialog ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
			    {
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			        UIManager.put("RootPane.setupButtonVisible", false);
			        
			    }
			    catch(Exception e)
			    {
			        //TODO exception
			    }
			    
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		this.setTitle("门禁管理系统");
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)(260*screen.getWidth_pro()), (int)(120*screen.getHeight_pro()), (int)(1400*screen.getWidth_pro()), (int)(780*screen.getHeight_pro()));
		getContentPane().setLayout(null);
		time = new display_Time() ;
		getContentPane().add(time) ;
		initial() ;
	}
	
	public void initial(){
		try {
			dao = new Dao() ;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接错误，程序即将结束！");
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接错误，程序即将结束！");
			e.printStackTrace();
			System.exit(0);
		}
		finger = new finger_Printing(dao) ; 
		dis = new display(dao) ;
		finger.setDis(dis);
		dis.setFrame(this);
		
		send = new Send(dao) ;
		
		con_set = new CON_Set(this) ;
		send_Dialog = new Send_Dialog(this,dao) ;
		send_Dialog.setSen(send);
		send.SetSend_Dialog(send_Dialog);
		send.setCon_set(con_set);
		
		Tab = new JTabbedPane() ;
		Tab.setBounds(0, (int)(80*screen.getHeight_pro()), (int)(1400*screen.getWidth_pro()), (int)(700*screen.getHeight_pro())) ;
		this.add(Tab) ;
		Tab.setVisible(true);
		Tab.addTab("指纹录入", finger);
		Tab.addTab("查看数据", dis);
		Tab.addTab("数据发送", send);
		Tab.addChangeListener(this);
		
		
		
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		int x = Tab.getSelectedIndex() ;
		
		switch(x){
		case 0 : break ;
		case 1:
			dis.update_list("select *from Info");
			//dis.sort_List();
			dis.getTable_01().setModel(dis.Create_Model(dis.getList()));
			dis.resizeTable(true);
			break ;
		case 2:
				if(con_set.getCon().getSerialPort()==null){
					con_set.setVisible(true);
				}
				break ;
			
		}
	}
}
