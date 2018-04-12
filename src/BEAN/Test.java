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
	
	private Vector<String> UTAR_list ;//�����б�
	
	private HashMap<String,Integer> parity ;//�����������
	
	private String[] baud_list ;//������
	private String[] databits_list ;//����λ
	private String[] parity_list ;//У��λ
	private String[] stopbits_list ;//ֹͣλ
	private CommPortIdentifier portId; //��ȡ����ͨ�Ŷ˿ڵ���
	private SerialPort serialPort;  //����
	
	//���������
	private InputStream inputStream;  
    private OutputStream outputStream;
    
    private String test ="" ;//�洢�������ж�ȡ������
    private Enumeration<CommPortIdentifier> portList; //����ͨ�Ŷ˿��б�
    
    /*
    public static void main(String[] args){
    	System.out.println(SerialPort.PARITY_EVEN+" "+SerialPort.PARITY_MARK+" "+SerialPort.PARITY_NONE+" "+SerialPort.PARITY_ODD+" "+SerialPort.PARITY_SPACE);
    	System.out.println(SerialPort.STOPBITS_1+" "+SerialPort.STOPBITS_1_5+" "+SerialPort.STOPBITS_2);
    }
    */
    /**
     * ����
     */
    public Test(){
    	UTAR_list = new Vector<String>();
    	baud_list = new String[]{"1382400","921600","460800","256000","230400","128000","115200","76800",
    			"57600","43000","38400","19200","14400","9600","4800","2400","1200"};
    	databits_list = new String[]{"5","6","7","8"} ;
    	parity_list = new String[]{"��","��У��","żУ��"} ;
    	stopbits_list = new String[]{"1","3","2"} ;
    	
    	
    }
   

    
    //���Ӵ��� 
    /**
     * 
     * @param port_name ���ڵ�����
     * @throws TooManyListenersException 
     * @throws PortInUseException 
     */
    @SuppressWarnings("unchecked")
	public void port_con(String port_name) throws TooManyListenersException, PortInUseException{
    	//�õ��б�
    	portList = CommPortIdentifier.getPortIdentifiers() ;
    	//����PortList
    	while(portList.hasMoreElements()){
    		portId = (CommPortIdentifier)portList.nextElement() ;
    		//�ж��Ƿ�Ϊ����
    		if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
    			//�Աȴ�������  ���Ϊ����Port_name��Ӧ���ַ�������򿪴���
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
     * @param baud ������
     * @param databits ����λ
     * @param stopbits ֹͣλ
     * @param parity У��λ
     * @throws UnsupportedCommOperationException
     * ���ô��ڵĲ���
     */
    public void setParameter(int baud,int databits,int stopbits,int parity) throws UnsupportedCommOperationException{
    		//serialPort ��һ�����ڶ��� ��������������Դ������ò���
			serialPort.setSerialPortParams(baud,databits, stopbits, parity) ;
			//System.out.println(serialPort.getBaudRate()+" "+serialPort.getDataBits()+" "+serialPort.getStopBits()+" "+serialPort.getParity());
		
    }
    
    /**
     * �����¼�
     */
	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		switch (event.getEventType()) {  
        case SerialPortEvent.BI: System.out.println(1); break ;
        case SerialPortEvent.OE: System.out.println(2); break;  //��λ����  
        case SerialPortEvent.FE: System.out.println(3); break ;   //֡����  
        case SerialPortEvent.PE: System.out.println(4); break ;   //��żУ�����  
        case SerialPortEvent.CD: System.out.println(5); break ;  //�ز����  
        case SerialPortEvent.CTS:System.out.println(6); break ;  //�������  
        case SerialPortEvent.DSR: System.out.println(7); break ;  //�����豸׼����  
        case SerialPortEvent.RI:  System.out.println(8); break ; //�������  
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:   //��������������  
            break;  
        case SerialPortEvent.DATA_AVAILABLE:    //�����ݵ���  
            {
            	readComm();  
            	break;  
            }
        default:  
        	System.out.println("default");
            break;  
        }  
	} 
	
	//�Ӵ����ж�ȡ����   ת��Ϊ16�����ַ���Test
	public void readComm(){
		
		
		
		byte[] readBuffer = new byte[1024];  
        try {  
            inputStream = serialPort.getInputStream();  
            
            // ����·�϶�ȡ������  
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
	 * @return  �ж�һ���ַ����Ƿ�ȫ��0-9���ַ����
	 */
	public boolean isNum(String str){
		return Pattern.matches("[1-9][0-9]*", str);
	}
	
	/**
	 * �رմ���
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
    
    //ת�����͵�ָ��
    /**
     * 
     * @param hex
     * @return
     * ��16���Ƶ��ַ���ת��Ϊһ��byte�����飬���ڷ���ָ��
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
    
    //ת�����ܵĵ�Ӧ��
    /**
     * 
     * @param b
     * @return
     * ���ֽ�װ��Ϊ��Ӧ��16�����ַ���
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
     * �򴮿ڷ���message
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
    	             System.out.println("���ͳɹ�");
    	         }catch(IOException e){
    	            throw new RuntimeException("��˿ڷ�����Ϣʱ����"+e.getMessage());
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
