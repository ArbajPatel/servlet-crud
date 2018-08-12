package com.uttara.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HSQLDbDAO implements AppDAO {

	@Override
	public String create(RegBean rb) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement ps_ins=null,ps_sel=null;
		ResultSet rs = null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				return "Sorry. There was an error establishing the connection</br>";
		 
			//connection has been established
			
			else {
				
				ps_sel = con.prepareStatement("select * from REGUSERS where email = ?");
				ps_sel.setString(1,rb.getEmail());
				ps_sel.execute();
				
				rs = ps_sel.getResultSet();
				
				if(rs.next())
					return "This email id has already been taken. Please enter a new one<br/>";
				
				//original enter into the db
				else {
					
					ps_ins = con.prepareStatement("insert into REGUSERS(name,email,pass) values(?,?,?)");
					ps_ins.setString(1,rb.getUname());
					ps_ins.setString(2, rb.getEmail());
					ps_ins.setString(3,rb.getPass());
					
					ps_ins.execute();
					
					return Constants.SUCCESS;
					
				}
				
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			return "Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>";
		}
		finally {
			
			JDBCUtil.close(rs);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(ps_ins);
			JDBCUtil.close(con);
		}
		
		
		
	}

	@Override
	public String update(RegBean rb) {


		Connection con = null;
		PreparedStatement ps_upd=null;
		ResultSet rs = null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				return "Sorry. There was an error establishing the connection</br>";
		 
			//connection has been established
			
			else {
				
				ps_upd = con.prepareStatement("update REGUSERS set name = ?,pass = ? where email = ?");
				ps_upd.setString(1,rb.getUname());
				ps_upd.setString(2,rb.getPass());
				ps_upd.setString(3, rb.getEmail());
				ps_upd.execute();
				
				
					
					return Constants.SUCCESS;
				
				}
				
			
		} catch (SQLException e) {
			// TODO: handle exception
			return "Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>";
		}
		finally {
			
			JDBCUtil.close(rs);
			JDBCUtil.close(ps_upd);
			JDBCUtil.close(con);
		}
	}

	@Override
	public String delete(String name) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement ps_del1=null,ps_del=null,ps_sel2=null,ps_del2=null,ps_del3=null;
		ResultSet rs2=null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				return "Sorry. There was an error establishing the connection</br>";
		 
			//connection has been established
			
			else {
				con.setAutoCommit(false);
				ps_sel2 = con.prepareStatement("select sl_no from contacts where name = ? ");
				ps_sel2.setString(1,name);
				ps_sel2.execute();
				
				rs2 = ps_sel2.getResultSet();
				rs2.next();
				
				
				int secsl = rs2.getInt(1);
				
				
				ps_del = con.prepareStatement("delete from contacts_emails where contacts_sl = ?");
				ps_del.setInt(1, secsl);
				ps_del.execute();
				
					
				
				
				//for the phone numbers
				ps_del1 = con.prepareStatement("delete from contacts_phones where contacts_sl = ?");
				ps_del1.setInt(1, secsl);
				ps_del1.execute();
				
                //last, but certainly not the least, for the tags
				ps_del2 = con.prepareStatement("delete from contacts_tags where contacts_sl = ?");
				ps_del2.setInt(1, secsl);
				ps_del2.execute();
				
				//finally from the contacts table itself
				ps_del3 = con.prepareStatement("delete from contacts where name = ?");
				ps_del3.setString(1, name);
				ps_del3.execute();
                con.commit();
				return Constants.SUCCESS;
				
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			return "Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>";
		}
		finally {
			
			JDBCUtil.close(rs2);
			JDBCUtil.close(ps_del3);
			JDBCUtil.close(ps_del2);
			JDBCUtil.close(ps_del1);
			JDBCUtil.close(ps_del);
			JDBCUtil.close(ps_sel2);
			JDBCUtil.close(con);
		}
		
		

	}
     
	@Override
	public RegBean read(String email) {

		
		Connection con = null;
		PreparedStatement ps_sel=null;
		ResultSet rs = null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				throw new RuntimeException("Sorry. There was an error establishing the connection</br>");
		 
			//connection has been established
			
			else {
				
				ps_sel = con.prepareStatement("select * from REGUSERS where email = ?");
				ps_sel.setString(1,email);
				ps_sel.execute();
				
				rs = ps_sel.getResultSet();
				RegBean rb = null;
				while(rs.next()){
					
					rb = new RegBean();
					rb.setUname(rs.getString("name"));
					rb.setEmail(rs.getString("email"));
					rb.setPass(rs.getString("pass"));
					rb.setRpass(rs.getString("pass"));
				
				
			   }
			   
			   return rb;
				
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			
			JDBCUtil.close(rs);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(con);
		}
		
	}
     
	
	///for login authentication
	@Override
    public String check(LogBean lb) {

		
		Connection con = null;
		PreparedStatement ps_sel=null;
		ResultSet rs = null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				return "Sorry. There was an error establishing the connection</br>";
		 
			//connection has been established
			
			else {
				
				ps_sel = con.prepareStatement("select * from REGUSERS where email = ?");
				ps_sel.setString(1,lb.getEmail());
				ps_sel.execute();
				
				rs = ps_sel.getResultSet();
				
				if(rs.next()) {
					if(rs.getString("email").equals(lb.getEmail()) && !rs.getString("pass").equals(lb.getPass())) {
						
						return "Your password is incorrect!. Please try again<br/>";
				}
					
					return Constants.SUCCESS;
				
				}
				else {
					
					
					return "You haven't registered yet ! Please register first";
					
				}
				
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			return "Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>";
		}
		finally {
			
			JDBCUtil.close(rs);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(con);
		}
	}
	
	@Override
	public List<Object> readAll(){
       
		List<Object> l = new ArrayList<Object>();
		Connection con = null;
		PreparedStatement ps_sel=null;
		ResultSet rs = null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null) {
				throw new RuntimeException("Sorry. We Couldn't connect to the database!");
			}
			
			//connection has been established
			
			else {
				
				ps_sel = con.prepareStatement("select * from REGUSERS");
				ps_sel.execute();
				
				rs = ps_sel.getResultSet();
				
				
					
					//create a regBean
					RegBean rb = null;
				 while(rs.next()) {
					 
					 rb = new RegBean();
					 rb.setUname(rs.getString("name"));
					 rb.setEmail(rs.getString("email"));
					 rb.setPass(rs.getString("pass"));
					 rb.setRpass(rs.getString("pass"));
					 l.add(rb);
					 
				 }
				 
				
				return l;
				
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			
			JDBCUtil.close(rs);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(con);
			
		}
		

	}

	
	
	///////creating contacts///////dayum!!!///////

	@Override
	public String createContact(ConBean cb, String email) {
		// TODO Auto-generated method stub
		
		
		Connection con = null;
		PreparedStatement ps_sel=null,ps_ins=null,ps_ins2=null,ps_ins3=null,ps_ins4=null;
		ResultSet rs = null,rs2=null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				return "Sorry. There was an error establishing the connection</br>";
		 
			//connection has been established
			
			else {
				con.setAutoCommit(false);
				ps_sel = con.prepareStatement("select sl_no from REGUSERS where email = ?");
				ps_sel.setString(1,email);
				ps_sel.execute();
				
				rs = ps_sel.getResultSet();
				rs.next();
				
				int prsl = rs.getInt(1);
				System.out.println(prsl);
				
				ps_ins = con.prepareStatement("insert into contacts(reg_sl,name,dob,gender) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
				ps_ins.setInt(1,prsl);
				ps_ins.setString(2, cb.getUname());
				ps_ins.setString(3, cb.getDob());
				ps_ins.setString(4,cb.getGender());
				ps_ins.execute();
				
				rs2 = ps_ins.getGeneratedKeys();
				rs2.next();
				
				
				int secsl = rs2.getInt(1);
				
				//for the emails
				String[] mail = cb.getEmail().split(",");
				ps_ins2 = con.prepareStatement("insert into contacts_emails(contacts_sl,email) values(?,?)");
				
				for(String s:mail) {
					
					ps_ins2.setInt(1, secsl);
					if(!s.isEmpty())
					ps_ins2.setString(2,s);
					ps_ins2.execute();
					
				}
				
				//for the phone numbers
				String[] ph = cb.getPhone().split(",");
				ps_ins3 = con.prepareStatement("insert into contacts_phones(contacts_sl,phone) values(?,?)");
				
                for(String s:ph) {
					
					ps_ins3.setInt(1, secsl);
					if(!s.isEmpty())
					ps_ins3.setString(2,s);
					ps_ins3.execute();
					
				}
				
                //last, but certainly not the least, for the tags
                String[] tag = cb.getTag().split(",");
                ps_ins4 = con.prepareStatement("insert into contacts_tags(contacts_sl,tag) values(?,?)");
                
                for(String s:tag) {
                	
                	ps_ins4.setInt(1, secsl);
                	if(!s.isEmpty())
					ps_ins4.setString(2,s);
					ps_ins4.execute();
                }
                con.commit();
				return Constants.SUCCESS;
				
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			return "Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>";
		}
		finally {
			
			JDBCUtil.close(rs2);
			JDBCUtil.close(rs);
			JDBCUtil.close(ps_ins4);
			JDBCUtil.close(ps_ins3);
			JDBCUtil.close(ps_ins2);
			JDBCUtil.close(ps_ins);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(con);
		}
		
	}

	//for listing...just return all the contacts in the DB in a list
	@Override
	public List<Object> getContacts(String ch,String email) {
		// TODO Auto-generated method stub
		
		List<Object> l = new ArrayList<Object>();
		Connection con = null;
		PreparedStatement ps_sel=null,ps_sel2=null,ps_sel3=null,ps_sel4=null,ps_sel5=null;
		ResultSet rs = null,rs2=null,rs3=null,rs4=null,rs5=null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				throw new RuntimeException("Sorry. There was an error establishing the connection</br>");
		 
			//connection has been established
			
			else {
				con.setAutoCommit(false);
				
				ps_sel5 = con.prepareStatement("select sl_no from regusers where email = ?");
				ps_sel5.setString(1, email);
				ps_sel5.execute();
				
				rs5 = ps_sel5.getResultSet();
				rs5.next();
				int reg = rs5.getInt("sl_no");
				
				
				switch(ch) {
				
				case  "name" : 
					ps_sel = con.prepareStatement("select distinct name,gender,dob,contacts_sl from contacts,contacts_emails,contacts_phones,contacts_tags where contacts.sl_no=contacts_emails.contacts_sl and contacts.sl_no=contacts_phones.contacts_sl and contacts.sl_no=contacts_tags.contacts_sl and contacts.reg_sl = ? order by name");
					ps_sel.setInt(1, reg);
					ps_sel.execute();
					rs= ps_sel.getResultSet();
					break;
					
				case  "dob" : 
					ps_sel = con.prepareStatement("select distinct name,gender,dob,contacts_sl from contacts,contacts_emails,contacts_phones,contacts_tags where contacts.sl_no=contacts_emails.contacts_sl and contacts.sl_no=contacts_phones.contacts_sl and contacts.sl_no=contacts_tags.contacts_sl and contacts.reg_sl=? order by dob");
					ps_sel.setInt(1, reg);
					ps_sel.execute();
					rs= ps_sel.getResultSet();
					break;
					
				case  "gender" : 
					ps_sel = con.prepareStatement("select distinct name,gender,dob,contacts_sl from contacts,contacts_emails,contacts_phones,contacts_tags where contacts.sl_no=contacts_emails.contacts_sl and contacts.sl_no=contacts_phones.contacts_sl and contacts.sl_no=contacts_tags.contacts_sl and contacts.reg_sl= ? order by gender");
					ps_sel.setInt(1, reg);
					ps_sel.execute();
					rs= ps_sel.getResultSet();
					break;	
					
					
				}
				
				/*cb= new ConBean();
				sl = rs.getInt("contacts_sl");
			    cb.setUname(rs.getString("name"));
			    cb.setEmail(rs.getString("email"));
			    cb.setPhone(rs.getString("phone"));
			    cb.setTag(rs.getString("tag"));
			    cb.setDob(rs.getString("dob"));
			    cb.setGender(rs.getString("gender"));
			    l.add(cb);*/
				
				String mail="",phone="",tag="";
				int sl=0;
				ConBean cb = null;
				while(rs.next()) {
					
					sl = rs.getInt("contacts_sl");
					
					//for retrieving  email
					ps_sel2 = con.prepareStatement("select*from contacts_emails where contacts_emails.contacts_sl=?");
					ps_sel2.setInt(1, sl);
					ps_sel2.execute();
					
					rs2 = ps_sel2.getResultSet();
					mail="";
					while(rs2.next()) {
						mail+=rs2.getString("email")+",";
						
					}
					
					//for retrieving  phone
					ps_sel3 = con.prepareStatement("select*from contacts_phones where contacts_phones.contacts_sl=?");
					ps_sel3.setInt(1, sl);
					ps_sel3.execute();
					
					rs3 = ps_sel3.getResultSet();
					phone="";
					while(rs3.next()) {
						phone+=rs3.getString("phone")+",";
					}
					
					//for retrieving tags
					ps_sel4 = con.prepareStatement("select*from contacts_tags where contacts_tags.contacts_sl=?");
					ps_sel4.setInt(1, sl);
					ps_sel4.execute();
					
					rs4 = ps_sel4.getResultSet();
					tag="";
					while(rs4.next()) {
						tag+=rs4.getString("tag")+",";
					}
					cb= new ConBean();
					cb.setUname(rs.getString("name"));
				    cb.setEmail(mail);
				    cb.setPhone(phone);
				    cb.setTag(tag);
				    cb.setDob(rs.getString("dob"));
				    cb.setGender(rs.getString("gender"));
				    l.add(cb);
					
					
				}
				
				return l;
				
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>");
		}
		finally {
			
			JDBCUtil.close(rs4);
			JDBCUtil.close(rs3);
			JDBCUtil.close(rs2);
			JDBCUtil.close(rs);
			JDBCUtil.close(rs5);
			JDBCUtil.close(ps_sel4);
			JDBCUtil.close(ps_sel3);
			JDBCUtil.close(ps_sel2);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(ps_sel5);
			JDBCUtil.close(con);
		}
		
		
	}

	@Override
	public List<Object> listEdit(String name, String email) {
		// TODO Auto-generated method stub
		
		List<Object> l = new ArrayList<Object>();
		Connection con = null;
		PreparedStatement ps_sel=null,ps_sel2=null,ps_sel3=null,ps_sel4=null,ps_sel5=null;
		ResultSet rs = null,rs2=null,rs3=null,rs4=null,rs5=null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				throw new RuntimeException("Sorry. There was an error establishing the connection</br>");
		 
			//connection has been established
			
			else {
				con.setAutoCommit(false);
				
				ps_sel5 = con.prepareStatement("select sl_no from regusers where email = ?");
				ps_sel5.setString(1, email);
				ps_sel5.execute();
				
				rs5 = ps_sel5.getResultSet();
				rs5.next();
				int reg = rs5.getInt("sl_no");
				
				
				
					ps_sel = con.prepareStatement("select distinct name,gender,dob,contacts_sl from contacts,contacts_emails,contacts_phones,contacts_tags where contacts.sl_no=contacts_emails.contacts_sl and contacts.sl_no=contacts_phones.contacts_sl and contacts.sl_no=contacts_tags.contacts_sl and contacts.reg_sl = ? and name like ?");
					ps_sel.setInt(1, reg);
					ps_sel.setString(2, "%"+name+"%");
					ps_sel.execute();
					rs= ps_sel.getResultSet();
					
					
				
				
				/*cb= new ConBean();
				sl = rs.getInt("contacts_sl");
			    cb.setUname(rs.getString("name"));
			    cb.setEmail(rs.getString("email"));
			    cb.setPhone(rs.getString("phone"));
			    cb.setTag(rs.getString("tag"));
			    cb.setDob(rs.getString("dob"));
			    cb.setGender(rs.getString("gender"));
			    l.add(cb);*/
				
				String mail="",phone="",tag="";
				int sl=0;
				ConBean cb = null;
				while(rs.next()) {
					
					sl = rs.getInt("contacts_sl");
					
					//for retrieving  email
					ps_sel2 = con.prepareStatement("select*from contacts_emails where contacts_emails.contacts_sl=?");
					ps_sel2.setInt(1, sl);
					ps_sel2.execute();
					
					rs2 = ps_sel2.getResultSet();
					mail="";
					while(rs2.next()) {
						mail+=rs2.getString("email")+",";
						
					}
					
					//for retrieving  phone
					ps_sel3 = con.prepareStatement("select*from contacts_phones where contacts_phones.contacts_sl=?");
					ps_sel3.setInt(1, sl);
					ps_sel3.execute();
					
					rs3 = ps_sel3.getResultSet();
					phone="";
					while(rs3.next()) {
						phone+=rs3.getString("phone")+",";
					}
					
					//for retrieving tags
					ps_sel4 = con.prepareStatement("select*from contacts_tags where contacts_tags.contacts_sl=?");
					ps_sel4.setInt(1, sl);
					ps_sel4.execute();
					
					rs4 = ps_sel4.getResultSet();
					tag="";
					while(rs4.next()) {
						tag+=rs4.getString("tag")+",";
					}
					cb= new ConBean();
					cb.setUname(rs.getString("name"));
				    cb.setEmail(mail);
				    cb.setPhone(phone);
				    cb.setTag(tag);
				    cb.setDob(rs.getString("dob"));
				    cb.setGender(rs.getString("gender"));
				    l.add(cb);
					
					
				}
				
				return l;
				
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>");
		}
		finally {
			
			JDBCUtil.close(rs4);
			JDBCUtil.close(rs3);
			JDBCUtil.close(rs2);
			JDBCUtil.close(rs);
			JDBCUtil.close(rs5);
			JDBCUtil.close(ps_sel4);
			JDBCUtil.close(ps_sel3);
			JDBCUtil.close(ps_sel2);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(ps_sel5);
			JDBCUtil.close(con);
		}
	}

	@Override
	public String updateContact(ConBean cb, String email,String name) {
		// TODO Auto-generated method stub

      String msg = delete(name);
      
      if(msg.equals(Constants.SUCCESS)) {
    	  
    	 msg =  createContact(cb, email);
    	 
    	 if(msg.equals(Constants.SUCCESS))
    		 return Constants.SUCCESS;
    	 
    	 else return msg;
      }
      else
	  return msg;
		
	    
	}

	@Override
	public List<Object> searchContacts(String search, String email) {
		// TODO Auto-generated method stub

		List<Object> l = new ArrayList<Object>();
		Connection con = null;
		PreparedStatement ps_sel=null,ps_sel2=null,ps_sel3=null,ps_sel4=null,ps_sel5=null;
		ResultSet rs = null,rs2=null,rs3=null,rs4=null,rs5=null;
		String line ="";
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				throw new RuntimeException("Sorry. There was an error establishing the connection</br>");
		 
			//connection has been established
			
			else {
				con.setAutoCommit(false);
				
				ps_sel5 = con.prepareStatement("select sl_no from regusers where email = ?");
				ps_sel5.setString(1, email);
				ps_sel5.execute();
				
				rs5 = ps_sel5.getResultSet();
				rs5.next();
				int reg = rs5.getInt("sl_no");
				
				
				
					ps_sel = con.prepareStatement("select distinct name,gender,dob,contacts_sl from contacts,contacts_emails,contacts_phones,contacts_tags where contacts.sl_no=contacts_emails.contacts_sl and contacts.sl_no=contacts_phones.contacts_sl and contacts.sl_no=contacts_tags.contacts_sl and contacts.reg_sl = ?");
					ps_sel.setInt(1, reg);
					ps_sel.execute();
					rs= ps_sel.getResultSet();
					
					
				
				
				/*cb= new ConBean();
				sl = rs.getInt("contacts_sl");
			    cb.setUname(rs.getString("name"));
			    cb.setEmail(rs.getString("email"));
			    cb.setPhone(rs.getString("phone"));
			    cb.setTag(rs.getString("tag"));
			    cb.setDob(rs.getString("dob"));
			    cb.setGender(rs.getString("gender"));
			    l.add(cb);*/
				
				String mail="",phone="",tag="";
				int sl=0;
				ConBean cb = null;
				while(rs.next()) {
					
					sl = rs.getInt("contacts_sl");
					
					//for retrieving  email
					ps_sel2 = con.prepareStatement("select*from contacts_emails where contacts_emails.contacts_sl=?");
					ps_sel2.setInt(1, sl);
					ps_sel2.execute();
					
					rs2 = ps_sel2.getResultSet();
					mail="";
					while(rs2.next()) {
						mail+=rs2.getString("email")+",";
						
					}
					
					//for retrieving  phone
					ps_sel3 = con.prepareStatement("select*from contacts_phones where contacts_phones.contacts_sl=?");
					ps_sel3.setInt(1, sl);
					ps_sel3.execute();
					
					rs3 = ps_sel3.getResultSet();
					phone="";
					while(rs3.next()) {
						phone+=rs3.getString("phone")+",";
					}
					
					//for retrieving tags
					ps_sel4 = con.prepareStatement("select*from contacts_tags where contacts_tags.contacts_sl=?");
					ps_sel4.setInt(1, sl);
					ps_sel4.execute();
					
					rs4 = ps_sel4.getResultSet();
					tag="";
					while(rs4.next()) {
						tag+=rs4.getString("tag")+",";
					}
					line ="";
					cb= new ConBean();
					cb.setUname(rs.getString("name"));
				    cb.setEmail(mail);
				    cb.setPhone(phone);
				    cb.setTag(tag);
				    cb.setDob(rs.getString("dob"));
				    cb.setGender(rs.getString("gender"));
				    line+= rs.getString("name")+" "+mail+" "+phone+" "+tag+" "+rs.getString("dob")+" "+rs.getString("gender");
				    if(line.contains(search))
				    l.add(cb);
					
					
				}
				
				return l;
				
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>");
		}
		finally {
			
			JDBCUtil.close(rs4);
			JDBCUtil.close(rs3);
			JDBCUtil.close(rs2);
			JDBCUtil.close(rs);
			JDBCUtil.close(rs5);
			JDBCUtil.close(ps_sel4);
			JDBCUtil.close(ps_sel3);
			JDBCUtil.close(ps_sel2);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(ps_sel5);
			JDBCUtil.close(con);
		}

	}

	@Override
	public List<Object> birthdayList(String email) {
		// TODO Auto-generated method stub
		List<Object> l = new ArrayList<Object>();
		Connection con = null;
		PreparedStatement ps_sel=null,ps_sel2=null,ps_sel3=null,ps_sel4=null,ps_sel5=null;
		ResultSet rs = null,rs2=null,rs3=null,rs4=null,rs5=null;
		
		try {
			
			con = JDBCUtil.createConnection();
			
			if(con == null)
				throw new RuntimeException("Sorry. There was an error establishing the connection</br>");
		 
			//connection has been established
			
			else {
				con.setAutoCommit(false);
				
				ps_sel5 = con.prepareStatement("select sl_no from regusers where email = ?");
				ps_sel5.setString(1, email);
				ps_sel5.execute();
				
				rs5 = ps_sel5.getResultSet();
				rs5.next();
				int reg = rs5.getInt("sl_no");
				
				
				
					ps_sel = con.prepareStatement("select distinct name,gender,dob,contacts_sl from contacts,contacts_emails,contacts_phones,contacts_tags where contacts.sl_no=contacts_emails.contacts_sl and contacts.sl_no=contacts_phones.contacts_sl and contacts.sl_no=contacts_tags.contacts_sl and contacts.reg_sl = ?");
					ps_sel.setInt(1, reg);
					ps_sel.execute();
					rs= ps_sel.getResultSet();
					
					
				
				
				/*cb= new ConBean();
				sl = rs.getInt("contacts_sl");
			    cb.setUname(rs.getString("name"));
			    cb.setEmail(rs.getString("email"));
			    cb.setPhone(rs.getString("phone"));
			    cb.setTag(rs.getString("tag"));
			    cb.setDob(rs.getString("dob"));
			    cb.setGender(rs.getString("gender"));
			    l.add(cb);*/
				
				String mail="",phone="",tag="";
				int sl=0;
				ConBean cb = null;
				while(rs.next()) {
					
					sl = rs.getInt("contacts_sl");
					
					//for retrieving  email
					ps_sel2 = con.prepareStatement("select*from contacts_emails where contacts_emails.contacts_sl=?");
					ps_sel2.setInt(1, sl);
					ps_sel2.execute();
					
					rs2 = ps_sel2.getResultSet();
					mail="";
					while(rs2.next()) {
						mail+=rs2.getString("email")+",";
						
					}
					
					//for retrieving  phone
					ps_sel3 = con.prepareStatement("select*from contacts_phones where contacts_phones.contacts_sl=?");
					ps_sel3.setInt(1, sl);
					ps_sel3.execute();
					
					rs3 = ps_sel3.getResultSet();
					phone="";
					while(rs3.next()) {
						phone+=rs3.getString("phone")+",";
					}
					
					//for retrieving tags
					ps_sel4 = con.prepareStatement("select*from contacts_tags where contacts_tags.contacts_sl=?");
					ps_sel4.setInt(1, sl);
					ps_sel4.execute();
					
					rs4 = ps_sel4.getResultSet();
					tag="";
					while(rs4.next()) {
						tag+=rs4.getString("tag")+",";
					}
					cb= new ConBean();
					cb.setUname(rs.getString("name"));
				    cb.setEmail(mail);
				    cb.setPhone(phone);
				    cb.setTag(tag);
				    cb.setDob(rs.getString("dob"));
				    cb.setGender(rs.getString("gender"));
				    l.add(cb);
					
					
				}
				
				return l;
				
				}
				
				
			
			
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException("Sorry. There was an error establishing the connection</br>"+e.getMessage()+"<br/>");
		}
		finally {
			
			JDBCUtil.close(rs4);
			JDBCUtil.close(rs3);
			JDBCUtil.close(rs2);
			JDBCUtil.close(rs);
			JDBCUtil.close(rs5);
			JDBCUtil.close(ps_sel4);
			JDBCUtil.close(ps_sel3);
			JDBCUtil.close(ps_sel2);
			JDBCUtil.close(ps_sel);
			JDBCUtil.close(ps_sel5);
			JDBCUtil.close(con);
		}
	}
}
