package BEAN;

public class Send_Data_structure {
	private String finger ;
	private String stu_id ;
	private String goal_address ;
	private String stu_name ;
	private String stu_class ;
	private String send_pattern ;
	public Send_Data_structure(){
		
	} 
	public Send_Data_structure(String finger,String stu_id,String stu_name,String stu_class){
		this.finger = finger ;
		this.stu_id = stu_id ;
		this.stu_name = stu_name ;
		this.stu_class = stu_class ; 
	}
	
	public String getFinger() {
		return finger;
	}
	public void setFinger(String finger) {
		this.finger = finger;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getGoal_address() {
		return goal_address;
	}
	public void setGoal_address(String goal_address) {
		this.goal_address = goal_address;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getStu_class() {
		return stu_class;
	}
	public void setStu_class(String stu_class) {
		this.stu_class = stu_class;
	}
	public String getSend_pattern() {
		return send_pattern;
	}
	public void setSend_pattern(String send_pattern) {
		this.send_pattern = send_pattern;
	} 
}
