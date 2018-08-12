package com.uttara.mvc;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ServiceModel {
	
	public String register(RegBean rb) {
		
		String msg = rb.Validate();
		
		
		if(msg.equals(Constants.SUCCESS)) {
			
			//send the request to the DAO factory. hold it in the parent APPDAO variable
			
			AppDAO var = DAOFactory.getDB();
			//since the default DB is HSQL call the create method on it!
			return var.create(rb);
			
		}
		else {
			return msg;
		}
		
	}
	
	public List<Object> getUsers(){
		
		AppDAO var = DAOFactory.getDB();
		List<Object> l;
	    l = var.readAll();
	    return l;
	  	
	}
	
	public String authenticate(LogBean lb) {
		
      String msg = lb.Validate();
		
		
		if(msg.equals(Constants.SUCCESS)) {
			
			//send the request to the DAO factory. hold it in the parent APPDAO variable
			
			AppDAO var = DAOFactory.getDB();
			//since the default DB is HSQL call the check method on it!
			return var.check(lb);
			
		}
		else {
			return msg;
		}
	}

	public RegBean getDetails(String email) {
		// TODO Auto-generated method stub
		
		AppDAO var = DAOFactory.getDB();
		return var.read(email);
	}
	
	public String updateDetails(RegBean rb) {
		
     String msg = rb.Validate();
		
		
		if(msg.equals(Constants.SUCCESS)) {
			
			//send the request to the DAO factory. hold it in the parent APPDAO variable
			
			AppDAO var = DAOFactory.getDB();
			//since the default DB is HSQL call the create method on it!
			return var.update(rb);
			
		}
		else {
			return msg;
		}
	}

	public String addContact(ConBean cb, String email) {
		// TODO Auto-generated method stub
       String msg = cb.Validate();
 		
		
		if(msg.equals(Constants.SUCCESS)) {
			
			//send the request to the DAO factory. hold it in the parent APPDAO variable
			
			AppDAO var = DAOFactory.getDB();
			return var.createContact(cb, email);
			//since the default DB is HSQL call the create method on it!
			
			
		}
		else {
			return msg;
		}
	
	}


	//search the DB using a string return a list of ConBeans
	public List<Object> searchContacts(String search, String email) {
		// TODO Auto-generated method stub
		AppDAO var = DAOFactory.getDB();
		return var.searchContacts(search,email);
	}

	
	//using the selected ch, and a comparator. Sort the list and return it
	public List<Object> listContacts(String ch,String email) {
		// TODO Auto-generated method stub
		
		AppDAO var = DAOFactory.getDB();
		return var.getContacts(ch,email);
		
	}

	public List<Object> listEdit(String name, String email) {
		// TODO Auto-generated method stub
		
		if(name==null || name.isEmpty())
		return null;
		
		else {
			
			AppDAO var = DAOFactory.getDB();
			return var.listEdit(name,email);
		}
	}

	public String UpdateContact(ConBean cb, String email, String name) {
		// TODO Auto-generated method stub
		 String msg = cb.Validate();
	 		
			
			if(msg.equals(Constants.SUCCESS)) {
				
				//send the request to the DAO factory. hold it in the parent APPDAO variable
				
				AppDAO var = DAOFactory.getDB();
				return var.updateContact(cb, email,name);
				//since the default DB is HSQL call the create method on it!
				
				
			}
			else {
				return msg;
			}
	}

	public String deleteContact(String name) {
		// TODO Auto-generated method stub
		AppDAO var = DAOFactory.getDB();
		return var.delete(name);
	}

	public List<Object> getBirthdayContacts(String email) {
		// TODO Auto-generated method stub
		List<Object> l = new ArrayList<Object>();
		List<Object> res = new ArrayList<Object>();
		
		AppDAO var = DAOFactory.getDB();
		l= var.birthdayList(email);
		
		for(Object o : l) {
			
			ConBean cb = (ConBean)o;
			if(isBirthday(cb.getDob()))
				res.add(cb);
			
		}
		return res;
		
		
		//loop over the list, for each contact, get the dob and call the datecomparison method and return a boolean
		//if true add to the resultant list, else dont
		
	}
	
	public static boolean isBirthday(String dob) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		Date current = null;
		Date dt = null;
		try {
			dt = sdf.parse(dob);
			Calendar c=Calendar.getInstance();
			current = new Date();
			c.setTime(current);
			c.add(Calendar.DATE, 7);
			
			if(dob.equals(sdf.format(current)))
				return true;
			
			else if(!(c.getTime().compareTo(dt)<0) && dt.after(current))
				return true;
			
			else return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}

	

}
