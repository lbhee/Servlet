package kr.or.bit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SingletonHelper {
	private static Connection conn = null;   //conn이라는 static자원을 private로 만든다.
	private SingletonHelper() {}             //다른곳에서 new할수없도록 private로 막는다.
	
	
	// 다른곳에서 사용할 수 있는 public함수를 만든다.
	public static Connection getConnection(String dsn){
		if(conn != null) {
			return conn;
		}
		
		try {
			if(dsn.equals("oracle")) {
				Class.forName("oracle.jdbc.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BITUSER","1004");
			}else if(dsn.equals("mysql")) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://192.168.0.218:3306/sampledb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=true","BITUSER","1004");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conn;
    }
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				
			}
		}
	}
	
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				
			}
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				
			}
		}
	}
}


    

