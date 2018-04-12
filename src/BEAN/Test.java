package BEAN;
import gnu.io.*;

import java.util.*;
import java.util.regex.Pattern;
import java.io.* ;

import UI.CON_Set;

public class Test implements SerialPortEventListener{
	
	@SuppressWarnings("unused")
	
	private int count = 0 ;
	private boolean sure = false ;
	
	private Vector<String> UTAR_list ;//串口列表
	
	private HashMap<String,Integer> parity ;//这个不用在意
	
	private String[] baud_list ;//波特率
	private String[] databits_list ;//数据位
	private String[] parity_list ;//校验位
	private String[] stopbits_list ;//停止位
	private CommPortIdentifier portId; //获取可用通信端口的类
	private SerialPort serialPort;  //串口
	
	//输入输出流
	private InputStream inputStream;  
    private OutputStream outputStream;
    
    private String test ="" ;//存储输入流中读取的数据
    private Enumeration<CommPortIdentifier> portList; //可用通信端口列表
    
    /*
    public static void main(String[] args){
    	System.out.println(SerialPort.PARITY_EVEN+" "+SerialPort.PARITY_MARK+" "+SerialPort.PARITY_NONE+" "+SerialPort.PARITY_ODD+" "+SerialPort.PARITY_SPACE);
    	System.out.println(SerialPort.STOPBITS_1+" "+SerialPort.STOPBITS_1_5+" "+SerialPort.STOPBITS_2);
    }
    */
    /**
     * 构造
     */
    public Test(){
    	UTAR_list = new Vector<String>();
    	baud_list = new String[]{"1382400","921600","460800","256000","230400","128000","115200","76800",
    			"57600","43000","38400","19200","14400","9600","4800","2400","1200"};
    	databits_list = new String[]{"5","6","7","8"} ;
    	parity_list = new String[]{"无","奇校验","偶校验"} ;
    	stopbits_list = new String[]{"1","3","2"} ;
    	
    	
    }
   

    
    //连接串口 
    /**
     * 
     * @param port_name 串口的名字
     * @throws TooManyListenersException 
     * @throws PortInUseException 
     */
    @SuppressWarnings("unchecked")
	public void port_con(String port_name) throws TooManyListenersException, PortInUseException{
    	//得到列表
    	portList = CommPortIdentifier.getPortIdentifiers() ;
    	//遍历PortList
    	while(portList.hasMoreElements()){
    		portId = (CommPortIdentifier)portList.nextElement() ;
    		//判断是否为串口
    		if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
    			//对比串口名字  如果为变量Port_name对应的字符串，则打开串口
    			if(portId.getName().equals(port_name)){
    					serialPort = (SerialPort)portId.open("port_con", 2000) ;
    					serialPort.addEventListener(this);
    					serialPort.notifyOnDataAvailable(true);
    					
    				
    				break ;
    			}
    			
    		}
    	}
 
    }
    /**
     * 
     * @param baud 波特率
     * @param databits 数据位
     * @param stopbits 停止位
     * @param parity 校验位
     * @throws UnsupportedCommOperationException
     * 设置串口的参数
     */
    public void setParameter(int baud,int databits,int stopbits,int parity) throws UnsupportedCommOperationException{
    		//serialPort 是一个串口对象 ，调用这个方法对串口设置参数
			serialPort.setSerialPortParams(baud,databits, stopbits, parity) ;
			//System.out.println(serialPort.getBaudRate()+" "+serialPort.getDataBits()+" "+serialPort.getStopBits()+" "+serialPort.getParity());
		
    }
    
    /**
     * 串口事件
     */
	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		switch (event.getEventType()) {  
        case SerialPortEvent.BI: System.out.println(1); break ;
        case SerialPortEvent.OE: System.out.println(2); break;  //溢位错误  
        case SerialPortEvent.FE: System.out.println(3); break ;   //帧错误  
        case SerialPortEvent.PE: System.out.println(4); break ;   //奇偶校验错误  
        case SerialPortEvent.CD: System.out.println(5); break ;  //载波检测  
        case SerialPortEvent.CTS:System.out.println(6); break ;  //清除发送  
        case SerialPortEvent.DSR: System.out.println(7); break ;  //数据设备准备好  
        case SerialPortEvent.RI:  System.out.println(8); break ; //响铃侦测  
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:   //输出缓冲区已清空  
            break;  
        case SerialPortEvent.DATA_AVAILABLE:    //有数据到达  
            {
            	readComm();  
            	break;  
            }
        default:  
        	System.out.println("default");
            break;  
        }  
	} 
	
	//从串口中读取数据   转化为16进制字符串Test
	public void readComm(){
		
		
		
		byte[] readBuffer = new byte[1024];  
        try {  
            inputStream = serialPort.getInputStream();  
            
            // 从线路上读取数据流  
            int len = 0;  
            while ((len = inputStream.read(readBuffer)) != -1) {  
            	byte[] temp = new byte[len] ;
            	for(int i=0;i<len;i++){
            		temp[i] = readBuffer[i] ;
            	}
                test += " "+byte2HexStr(temp) ;
               // System.out.println(test);
                break;  
            }  
            //test+="\n" ;
        } catch (IOException e) {  
        	closeSerialPort() ;
        	
        	//System.out.println(this.serialPort==null);
           // e.printStackTrace();  
        }  
       
	}
     
	/**
	 * 
	 * @param str
	 * @return  判断一个字符串是否全由0-9的字符组成
	 */
	public boolean isNum(String str){
		return Pattern.matches("[1-9][0-9]*", str);
	}
	
	/**
	 * 关闭串口
	 */
    public void closeSerialPort() {  
        if (serialPort != null) {  
            serialPort.notifyOnDataAvailable(false);  
            serialPort.removeEventListener();  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                    inputStream = null;  
                }  
                catch (IOException e) {}  
            }  
            if (outputStream != null) {  
                try {  
                    outputStream.close();  
                    outputStream = null;  
                }  
                catch (IOException e) {}  
            }  
            serialPort.close();  
            serialPort = null;  
        }  
    }  
    
    //转化发送的指令
    /**
     * 
     * @param hex
     * @return
     * 将16进制的字符串转换为一个byte的数组，用于发送指令
     */
    public byte[] hex2byte(String hex) {
        String digital = "0123456789ABCDEF";
        String hex1 = hex.replace(" ", "");
        char[] hex2char = hex1.toCharArray();
        byte[] bytes = new byte[hex1.length() / 2];
        byte temp;
        for (int p = 0; p < bytes.length; p++) {
            temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
            temp += digital.indexOf(hex2char[2 * p + 1]);
            bytes[p] = (byte) (temp & 0xff);
        }
        return bytes;
    }
    
    //转换接受的的应答
    /**
     * 
     * @param b
     * @return
     * 将字节装换为对应的16进制字符串
     */
    public static String byte2HexStr(byte[] b)    
	{    
	    String stmp="";    
	    StringBuilder sb = new StringBuilder("");    
	    for (int n=0;n<b.length;n++)    
	    {    
	        stmp = Integer.toHexString(b[n] & 0xFF);    
	        sb.append((stmp.length()==1)? "0"+stmp : stmp);    
	        sb.append(" ");    
	    }    
	    return sb.toString().toUpperCase().trim();    
	}
    
 
    /**
     * 
     * @param message 
     * 向串口发送message
     */
    public void write(String message) {
             	message = message.replace(" ", "") ;
    	        try{
    	        	outputStream = new BufferedOutputStream(serialPort.getOutputStream());
    	        }catch(IOException e){
    	        	this.closeSerialPort();
    	        }
    	         
    	         try{
    	             outputStream.write(hex2byte(message));
    	             System.out.println("发送成功");
    	         }catch(IOException e){
    	            throw new RuntimeException("向端口发送信息时出错："+e.getMessage());
    	         }finally{
    	             try{
    	                 outputStream.close();
    	             }catch(Exception e){
    	             }
    	        }
    	     }
    
   
    
   
    
    //Getter setter 
	public Vector<String> getUTAR_list() {
		return UTAR_list;
	}
	public String[] getBaud_list() {
		return baud_list;
	}
	public void setBaud_list(String[] baud_list) {
		this.baud_list = baud_list;
	}
	public String[] getDatabits_list() {
		return databits_list;
	}
	public void setDatabits_list(String[] databits_list) {
		this.databits_list = databits_list;
	}
	public String[] getParity_list() {
		return parity_list;
	}
	public void setParity_list(String[] parity_list) {
		this.parity_list = parity_list;
	}
	public String[] getStopbits_list() {
		return stopbits_list;
	}
	public void setStopbits_list(String[] stopbits_list) {
		this.stopbits_list = stopbits_list;
	}
	
	public HashMap<String,Integer> getParity() {
		return parity;
	}
	public void setParity(HashMap<String,Integer> parity) {
		this.parity = parity;
	}
	public String getTest() {
		return this.test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public SerialPort getSerialPort() {
		return serialPort;
	}
	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
	public boolean isSure() {
		return sure;
	}
	public void setSure(boolean sure) {
		this.sure = sure;
	}

	
    
}
