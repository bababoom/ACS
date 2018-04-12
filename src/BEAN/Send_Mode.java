package BEAN;

public class Send_Mode {

	/**
	 * 
	 * @param con
	 * @param finger
	 * @param id
	 * @see ���㲥/�������͵Ĳ�����װ���������
	 */
	public void broadcast(UART_con con,Send_Data_structure data){
		String str = "7E 02 0A "+data.getGoal_address()+" 01 "+data.getFinger().trim() ;
		if(data.getStu_id().trim().length()==8){
			str += " 00" ;
			for(int i=0;i<data.getStu_id().trim().length()-2;i+=2){
				str += " "+data.getStu_id().substring(i, i+2) ;
			}
		}else{
			for(int i=0;i<data.getStu_id().trim().length()-2;i+=2){
				str += " "+data.getStu_id().substring(i, i+2) ;
			}
		}
		str += " 02 08 7F" ;
		con.write(str);
		
	}
	
	
				/**
				 * 
				 * @param con
				 * @param finger ָ��ģ��
				 * @param id ѧ�Ż��߽�ְ����
				 * @param address Ŀ�ĵ�ַ
				 * �������ͷ�ʽ
				 */
	/**
	public void unicast(UART_con con ,Send_Data_structure data){
		String str = "7E 02 0A "+data.getGoal_address().trim()+" 01 "+data.getFinger().trim() ;
		if(data.getStu_id().trim().length()==8){
			str += " 00" ;
			for(int i=0;i<data.getStu_id().trim().length()-2;i+=2){
				str += " "+data.getStu_id().substring(i, i+2) ;
			}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		}else{
			for(int i=0;i<data.getStu_id().trim().length()-2;i+=2){
				str += " "+data.getStu_id().substring(i, i+2) ;
			}
		}
		str += " 02 08 7F" ;
		con.write(str);
	}
	*/
	

}
