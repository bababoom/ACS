package BEAN;
import gnu.io.*;

import java.util.*;
import java.util.regex.Pattern;
import java.io.* ;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import UI.CON_Set;
import UI.finger_Printing;
public class UART_con implements SerialPortEventListener{
	
	@SuppressWarnings("unused")
	private finger_Printing other =null ;
	private CON_Set con_set = null ;
	
	private int count = 0 ;
	private boolean sure = false ;
	
	private Vector<String> UTAR_list ;//�����б�
	
	private HashMap<String,Integer> parity ;
	
	private String[] academy ;
	private String[] work ;//ѧ�����ְ��
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
    public UART_con(){
    	UTAR_list = new Vector<String>();
    	baud_list = new String[]{"1382400","921600","460800","256000","230400","128000","115200","76800",
    			"57600","43000","38400","19200","14400","9600","4800","2400","1200"};
    	databits_list = new String[]{"5","6","7","8"} ;
    	parity_list = new String[]{"��","��У��","żУ��"} ;
    	stopbits_list = new String[]{"1","3","2"} ;
    	work = new String[]{"ѧ��","��ְ��"} ;
    	academy = new String[]{"--��ѡ��--","��֯ѧԺ","���Ͽ�ѧ�빤��ѧԺ","�����뻯ѧ����ѧԺ","��е����ѧԺ","�������ѧ�����ѧԺ","�����������Զ���ѧԺ",
    			"��������Ϣ����ѧԺ","��ѧԺ","�������װѧԺ","����ѧԺ","����ѧԺ","�����뷨ѧԺ","�����ѧԺ","���˼����ѧԺ",
    			"Ӧ��������ѧԺ","���ʽ���ѧԺ","����"} ;
    	parity = new HashMap<String, Integer>() ;
    	parity.put("��", 0)  ;
    	parity.put("��У��", 1) ;
    	parity.put("żУ��", 2) ;
    }
   
    /**
     *  ���´����б�UTAR_list��
     */
    @SuppressWarnings("unchecked")
	public void update_UTAR_list(){
    	if(!UTAR_list.isEmpty())UTAR_list.removeAllElements();
    	UTAR_list.addElement("--��ѡ��--");
    	portList = CommPortIdentifier.getPortIdentifiers() ;
    	while(portList.hasMoreElements()){
    		portId = (CommPortIdentifier)portList.nextElement() ;
    		if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
    			//System.out.println(portId.getName());
    			UTAR_list.addElement(portId.getName());
    		}
    	}
    	
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
    	portList = CommPortIdentifier.getPortIdentifiers() ;
    	while(portList.hasMoreElements()){
    		portId = (CommPortIdentifier)portList.nextElement() ;
    		if(portId.getPortType()==CommPortIdentifier.PORT_SERIAL){
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
     * @param baud
     * @param databits
     * @param stopbits
     * @param parity
     * @throws UnsupportedCommOperationException
     * ���ô��ڵĲ���
     */
    public void setParameter(int baud,int databits,int stopbits,int parity) throws UnsupportedCommOperationException{
    
			serialPort.setSerialPortParams(baud,databits, stopbits, parity) ;
			//System.out.println(serialPort.getBaudRate()+" "+serialPort.getDataBits()+" "+serialPort.getStopBits()+" "+serialPort.getParity());
		
    }
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
	
	//��ȡӦ��
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
        	if(this.other!=null) this.other.update(); 
        	else if(this.con_set!=null) this.con_set.update();
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
    
    public void setOther(finger_Printing ff){
    	this.other = ff ;
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
    
    /**
     * 
     * @return
     * comboBox ��ģ��
     */
    public list_Model getList_Model(){
    	return new list_Model() ;
    }
    
    public arr_Model getArr_Model(String[] arr){
    	return new arr_Model(arr) ;
    }
    @SuppressWarnings("rawtypes")
	class list_Model extends AbstractListModel implements
	ComboBoxModel{
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String item = null;

		@Override
		public Object getElementAt(int index) {
			// TODO Auto-generated method stub
			return UTAR_list.get(index++);
		}
		
		// ���ڼ̳�AbstractListModel�����ࡣ������Ƿֱ��ڳ�����ʵ����getElementAt()��getSize()������
		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return UTAR_list.size();
		}
		
		@Override
		public void setSelectedItem(Object anItem) {
			// TODO Auto-generated method stub
			item = (String) anItem;
		}

		@Override
		public Object getSelectedItem() {
			// TODO Auto-generated method stub
			return item;
		}
    	
    }
    
    @SuppressWarnings("rawtypes")
	class arr_Model extends AbstractListModel implements
	ComboBoxModel{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String item = null ;
    	String[] temp = null;
    	public arr_Model(String[] arr){
    		temp = arr ;
    	}
		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return temp.length;
		}

		@Override
		public Object getElementAt(int index) {
			// TODO Auto-generated method stub
			return temp[index++];
		}

		@Override
		public void setSelectedItem(Object anItem) {
			// TODO Auto-generated method stub
			item  = (String) anItem ;
		}

		@Override
		public Object getSelectedItem() {
			// TODO Auto-generated method stub
			return item ;
		}
    	
    }
    public void setCON_SET(CON_Set c){
    	this.con_set = c ;
    }
    /**
     * 
     * @param str (str�Ǵ������ģ�� )
     * @return ������ģ��
     */
    public String getInfo(String str){
    	StringBuffer sb = new StringBuffer() ;
    	str = str.substring(36, str.length()) ;
    	String[] ss = str.split("EF 01 FF FF FF FF") ;
    	for(int i=1;i<ss.length;i++){
    		ss[i] = ss[i].trim() ;
    		ss[i] = ss[i].substring(9, ss[i].length()-6);
    		sb.append(ss[i]) ;
    		if(i!=ss.length-1){
    			sb.append(" ") ;
    		}
    		
    	}
    	return sb.toString() ;
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
	public String[] getWork() {
		return work;
	}
	public void setWork(String[] work) {
		this.work = work;
	}
	public String[] getAcademy() {
		return academy;
	}
	public void setAcademy(String[] academy) {
		this.academy = academy;
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
