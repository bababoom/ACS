package UI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

import BEAN.Screen_parameter;
public class display_Time extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Screen_parameter screen = new Screen_parameter() ;
	private JLabel time_Label ;
	private String time ;
	private String DEFAULT_TIME_FORMAT = "yyyy年-MM月-dd日  HH:mm";
	private int ONE_SECOND = 1000; 
	/**
	 * Create the panel.
	 */
	
	public display_Time() {
		this.setBounds((int)(1100*screen.getWidth_pro()), (int)(700*screen.getHeight_pro()), (int)(400*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		this.setLayout(null);
		this.setVisible(true);
		//初始化time_Label
		time_Label = new JLabel() ;
		time_Label.setBounds(0, 0, (int)(300*screen.getWidth_pro()), (int)(50*screen.getHeight_pro()));
		
		this.add(time_Label) ;
		this.configTimeArea();
		
	}
	private void configTimeArea() {  
        Timer tmr = new Timer();  
        tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);  
    }  
	protected class JLabelTimerTask extends TimerTask {  
        SimpleDateFormat dateFormatter = new SimpleDateFormat(  
                DEFAULT_TIME_FORMAT);  
  
        @Override  
        public void run() {  
            time = dateFormatter.format(Calendar.getInstance().getTime());  
            time_Label.setText(time);  
        }  
    }  
}
