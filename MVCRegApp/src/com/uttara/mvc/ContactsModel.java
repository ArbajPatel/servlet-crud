package com.uttara.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsModel {

	//declare all the important variables as static for them to be used throughout the class, in any method
	
	static Connection con = null;
	static PreparedStatement ps_ins = null;
	static PreparedStatement ps_ins2 = null;
	static PreparedStatement ps_upd = null;
	static PreparedStatement ps_del = null;
	static PreparedStatement ps_sel = null;
    static ResultSet rs = null;
    
    static String query;
    
    
    public static void addContact(String name,String email,String phone,Date dtob,Date createdDate,String phoneNum,String numType) {
    	
    	java.sql.Date dob = new java.sql.Date(dtob.getTime());
    	java.sql.Date dtCreate = new java.sql.Date(createdDate.getTime());
    	try {
    		
    		//create a connection
    		con = JDBCUtil.createConnection();
    		con.setAutoCommit(false);
    		
			//sql cmd exec
    		query = "insert into contacts(name,email,phone,dob,createddate) values(?,?,?,?,?)";
    		ps_ins = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
    		ps_ins.setString(1, name);
    		ps_ins.setString(2, email);
    		ps_ins.setString(3, phone);
    		ps_ins.setDate(4,dob );
    		ps_ins.setDate(5, dtCreate);
    		
    		ps_ins.execute();
    		
    		//now, for the secondary table
    		rs = ps_ins.getGeneratedKeys();
    		rs.next();
    		int sl = rs.getInt(1);
    		
    		//now, lets split up both phonenumbers and numtypes and add them to the second table
    		String[] ph = phoneNum.split(",");
    		String[] ty = numType.split(",");
    		query = "insert into contacts_phoneinfo(contacts_sl,phonenumber,type) values(?,?,?)";
    		ps_ins2 = con.prepareStatement(query);
    		
    		for(int i=0;i<ph.length;i++) {
    			
    			ps_ins2.setInt(1, sl);
    			ps_ins2.setString(2,ph[i]);
    			ps_ins2.setString(3, ty[i]);
    			ps_ins2.execute();
    		}
    		
    		con.commit();
    		System.out.println();
    		System.out.println("Contact Added Successfully....");
    		
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
    	finally {
    		
    		JDBCUtil.close(rs);
    		JDBCUtil.close(ps_ins);
    		JDBCUtil.close(con);
    	}
    	
    	return;
    }
    
    
    ////deleting a contact
    
    public static void deleteContact(String name) {
    	
    	try {
			
    		con = JDBCUtil.createConnection();
    		
    		//sql cmd exec
    		
    		query = "select * from contacts where name = " + "'"+ name +"'";
    		System.out.println(query);
    		ps_sel = con.prepareStatement(query);
    		ps_sel.execute();
    		
    		rs = ps_sel.getResultSet();
    		rs.next();
    		int sl = rs.getInt("sl_no");
    		System.out.println(sl);
    		
    		
    		query = "delete from contacts_phoneinfo where contacts_sl = "+sl;
    		ps_del = con.prepareStatement(query);
    		ps_del.execute();
    		
    		//now delete entry from the primary table
    		query = "delete from contacts where name = " + "'" + name + "'";
    		ps_del = con.prepareStatement(query);
    		ps_del.execute();
    		
    		
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        finally {
    		
    		JDBCUtil.close(rs);
    		JDBCUtil.close(ps_del);
    		JDBCUtil.close(con);
    	}
    	
    	return;
    }
    
    ///retreiving contacts 
    
    public static List<String> retrieveContact(String name) {
    	
    	List<String> l = new ArrayList<String>();
    	
    	try {
			
    		con = JDBCUtil.createConnection();
    		
    		//sql cmd exec
    		query = "select * from contacts,contacts_phoneinfo where contacts.sl_no = contacts_phoneinfo.contacts_sl";
    		ps_sel = con.prepareStatement(query);
    		ps_sel.execute();
    		
    		rs = ps_sel.getResultSet();
    		
    		
    		while(rs.next()) {
    		
    			if(rs.getString("name").equals(name))
    			l.add("Name : "+rs.getString("name")+" Email : "+rs.getString("email")+" Phone : "+rs.getString("phone")+" Dob : "+rs.getString("dob")+" PhoneNum : "+rs.getString("phonenumber")+" Type : "+rs.getString("type"));
    		
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        finally {
    		
    		JDBCUtil.close(rs);
    		JDBCUtil.close(ps_sel);
    		JDBCUtil.close(con);
    	}
    	
    	return l;
    }
    
    
    /////display sorting sorted
    
    public static List<String> displayContacts(int ch) {
    	
    	List<String> l = new ArrayList<String>();
    	
    	try {
			
    		con = JDBCUtil.createConnection();
    		
    		//sql cmd exec
    		
    		switch(ch) {
    		
    		case 1 : query = "select * from contacts,contacts_phoneinfo where contacts.sl_no = contacts_phoneinfo.contacts_sl order by name";
    		ps_sel = con.prepareStatement(query);
    		ps_sel.execute();
    		
    		rs = ps_sel.getResultSet();
    			break;
    		
    		case 2 : query = "select * from contacts,contacts_phoneinfo where contacts.sl_no = contacts_phoneinfo.contacts_sl order by dob";
    		ps_sel = con.prepareStatement(query);
    		ps_sel.execute();
    		
    		rs = ps_sel.getResultSet();
    			break;
    			
    		case 3 : query = "select * from contacts,contacts_phoneinfo where contacts.sl_no = contacts_phoneinfo.contacts_sl order by createddate";
    		ps_sel = con.prepareStatement(query);
    		ps_sel.execute();
    		
    		rs = ps_sel.getResultSet();
    			break;
    		}
    		
    		
    		
    		while(rs.next()) {
    		
    			l.add("Name : "+rs.getString("name")+" Email : "+rs.getString("email")+" Phone : "+rs.getString("phone")+" Dob : "+rs.getString("dob")+" PhoneNum : "+rs.getString("phonenumber")+" Type : "+rs.getString("type"));
    		
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        finally {
    		
        	JDBCUtil.close(rs);
    		JDBCUtil.close(ps_sel);
    		JDBCUtil.close(con);
    	}
    	
    	return l;
    }
    
    public static void Update(String name , String replaceWith, int ch) {
    	
    	SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
    	
    	
    	try {
			
    		//establish the connection first
    		con = JDBCUtil.createConnection();
    		
    		//sql cmd exec
            switch(ch) {
    		
    		case 1 : query = "update  from  contacts set name = "+ "'" + replaceWith + "'" + " where name = "+ "'" + name + "'" ;
    		ps_upd = con.prepareStatement(query);
    		ps_upd.execute();
    		
    			break;
    		
    		case 2 : query = "update  from  contacts set email = "+ "'" + replaceWith + "'" + " where name = "+ "'" + name + "'" ;
    		ps_upd = con.prepareStatement(query);
    		ps_upd.execute();
    		
    			break;
    			
    		case 3 : 
    		
    			Date dtob = sfd.parse(replaceWith);
    			java.sql.Date dob = new java.sql.Date(dtob.getTime());
    			
    			query = "update  from  contacts set dob = "+ "'" + dob + "'" + " where name = "+ "'" + name + "'" ;
    		ps_upd = con.prepareStatement(query);
    		ps_upd.execute();
    		
    			break;
    		}
            
            System.out.println(" Update Successful ");
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        finally {
    		
        	
    		JDBCUtil.close(ps_upd);
    		JDBCUtil.close(con);
    	}
    	
    }
    
}
