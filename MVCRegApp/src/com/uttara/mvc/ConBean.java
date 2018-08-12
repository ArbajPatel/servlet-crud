package com.uttara.mvc;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String uname,email,phone,tag,gender,dob;
	
	
	
	public ConBean() {
		super();
		// TODO Auto-generated constructor stub
	}






	public ConBean(String uname, String email, String phone, String tag, String gender, String dob) {
		super();
		this.uname = uname;
		this.email = email;
		this.phone = phone;
		this.tag = tag;
		this.gender = gender;
		this.dob = dob;
	}






	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}






	@Override
	public String toString() {
		return "ConBean [uname=" + uname + ", email=" + email + ", phone=" + phone + ", tag=" + tag + ", gender="
				+ gender + ", dob=" + dob + "]";
	}







	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}






	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ConBean))
			return false;
		ConBean other = (ConBean) obj;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}






	public String Validate() {
		
		StringBuilder sb = new StringBuilder();
		
			if(uname==null || uname.isEmpty() ||uname.trim().equals(""))
				sb.append("Name is invalid. Please try again<br/>");
			if(email==null || email.isEmpty() ||email.trim().equals(""))
				sb.append("Email(s) is(are) invalid. Please try again<br/>");
			if(phone == null || phone.isEmpty() || phone.trim().equals(""))
				sb.append("Phone Number(s) is(are) invalid. Please try again<br/>");
			if(tag==null || tag.isEmpty() || tag.trim().equals(""))
				sb.append("Tag(s) is(are) invalid. Please try again<br/>");
			if(gender==null || gender.isEmpty() || gender.trim().equals(""))
				sb.append("Gender hasn't been selected. Please try again<br/>");
			if(dob==null || !dateValidate(dob))
				sb.append("DOB is invalid. Please try again<br/>");
			
			String msg = sb.toString();
			if(msg == null || msg.isEmpty()) {
				return Constants.SUCCESS;
			}
			else
				return msg;
			
	}
	
	
	public static boolean dateValidate(String value) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		Date dt = null;
		
		try {
			
			dt = sdf.parse(value);
			if(value.equals(sdf.format(dt)))
				return true;
			else
				return false;
			
		} catch (ParseException e) {
			// TODO: handle exception
			return false;
		}
		
	}

}
