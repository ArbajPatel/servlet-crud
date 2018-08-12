package com.uttara.mvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	
  public static void close(Connection con) {
		
		if(con!=null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}	
	
	
  public static void close(ResultSet rs) {
		
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	} 
	
	public static void close(Statement smt) {
		
		if(smt!=null)
			try {
				smt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public static Connection createConnection() {
		
		Connection con = null;
		
		try {
			
			Class.forName(Constants.DRIVER_NAME);
			con = DriverManager.getConnection(Constants.JDBC_URL, Constants.JDBC_UID, Constants.JDBC_PASS);
			
			if(con!=null) {
				
				System.out.println("Connection has been established successfully");
			}
			else {
				
				System.out.println("Sorry  , there was a problem. Please inform the DBA");
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO: handle exception
			con = null;
			e.printStackTrace();
		}
		
		return con;
		
	}
}
