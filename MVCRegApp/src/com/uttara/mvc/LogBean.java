package com.uttara.mvc;

import java.io.Serializable;

public class LogBean implements Serializable {

	private String email,pass;

	public LogBean(String email, String pass) {
		super();
		this.email = email;
		this.pass = pass;
	}

	public LogBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LogBean))
			return false;
		LogBean other = (LogBean) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (pass == null) {
			if (other.pass != null)
				return false;
		} else if (!pass.equals(other.pass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogBean [email=" + email + ", pass=" + pass + "]";
	}
	
    public String Validate() {
		
		StringBuilder sb = new StringBuilder();
		
		
		if(email==null || email.isEmpty() || email.trim().equals(""))
			sb.append("Email is invalid. Please try again<br/>");
		if(pass==null || pass.isEmpty() || pass.trim().equals(""))
			sb.append("Password is invalid. Please try again<br/>");
		
		
		String msg = sb.toString();
		if(msg == null || msg.isEmpty()) {
			return Constants.SUCCESS;
		}
		else
			return msg;
		
	}
	
}
