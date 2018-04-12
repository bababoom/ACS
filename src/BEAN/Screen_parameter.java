package BEAN;

import java.awt.Dimension;
import java.awt.Toolkit;
 
public class Screen_parameter {
	static final int width = 1920 ; 
	static final int height = 1080 ;
	private int current_width ;
	private int current_height ;
	private double width_pro ;
	private double height_pro ;
	private Toolkit kit ;
	@SuppressWarnings("static-access")
	public Screen_parameter(){
		kit = Toolkit.getDefaultToolkit();
		Dimension  screensize = kit. getScreenSize();
		this.current_width = screensize.width ;
		this.current_height = screensize.height ;
		this.width_pro = (this.getCurrent_width()*1.0)/this.width ;
		this.height_pro = (this.getCurrent_height()*1.0)/this.height ;
	}
	public int getCurrent_width() {
		return current_width;
	}
	public void setCurrent_width(int current_width) {
		this.current_width = current_width;
	}
	public int getCurrent_height() {
		return current_height;
	}
	public void setCurrent_height(int current_height) {
		this.current_height = current_height;
	}
	public double getWidth_pro() {
		return width_pro;
	}
	public void setWidth_pro(double width_pro) {
		this.width_pro = width_pro;
	}
	public double getHeight_pro() {
		return height_pro;
	}
	public void setHeight_pro(double height_pro) {
		this.height_pro = height_pro;
	}
	/*
	public static void main(String args[]){
		Screen_parameter screen = new Screen_parameter() ;
		
	}
	 */
}
