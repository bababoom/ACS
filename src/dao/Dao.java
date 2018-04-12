package dao;
import java.sql.*;
public class Dao {
	private Connection con ;
	private String driver = "com.mysql.jdbc.Driver" ;
	private String url = "jdbc:mysql://localhost:3306/ACS_DATABASE" ;
	private String user = "root" ;
	private String pass = "123456" ;
	private Statement stat = null  ;
	private ResultSet rs = null;
	
	public Dao() throws ClassNotFoundException, SQLException{
		
				//加载驱动程序
				
					Class.forName(driver);
					//1.getConnection()方法，连接MySQL数据库！！
					con = DriverManager.getConnection(url,user,pass);
					if(!con.isClosed()){
						System.out.println("Succeeded connecting to the Database!");
						
					}
					stat = con.createStatement() ;
				
				
		
	}
	
	public void close() throws SQLException{
		
			con.close();
		
			stat.close();
		
	}
	public ResultSet executeQuery(String sql){
		try{
			rs = stat.executeQuery(sql) ;
			//System.out.println("OK");
		
		}catch(Exception e){
			System.out.println("数据库链接错误！");
			rs = null ;
		}
		return rs ;
	}
	public boolean execute(String sql){
		try {
			stat.execute(sql) ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false ;
			
			
		}
		return true ;
	}
	
	public boolean isContainID(String str){
		String sql = "select id from Info " ;
		ResultSet rs = this.executeQuery(sql) ;
		try {
			while(rs.next()){
				if(str.trim().equals(rs.getString("id"))){
					return true ;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false  ;
	}
	/*
	public static void main(String args[]){
		Dao dao = new Dao() ;
		ResultSet rs = dao.executeQuery("select *from Info") ;
		try {
			while(rs.next()){
				System.out.println(rs.getString("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.close();
		
	}
	*/
	
	
	
}
