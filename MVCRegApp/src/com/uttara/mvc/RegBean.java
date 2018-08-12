package com.uttara.mvc;

import java.io.Serializable;

public class RegBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String uname,email,pass,rpass;

	public RegBean(String uname, String email, String pass, String rpass) {
		super();
		this.uname = uname;
		this.email = email;
		this.pass = pass;
		this.rpass = rpass;
	}

	public RegBean() {
		super();
		System.out.println("In no-arg constr of RB");
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		System.out.println("In RB's setUname() "+uname);
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		System.out.println("In RB's setEmail() "+email);
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		System.out.println("In RB's setPass() "+pass);
		this.pass = pass;
	}

	public String getRpass() {
		return rpass;
	}

	public void setRpass(String rpass) {
		System.out.println("In RB's setEmail() "+rpass);
		this.rpass = rpass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		result = prime * result + ((rpass == null) ? 0 : rpass.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RegBean))
			return false;
		RegBean other = (RegBean) obj;
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
		if (rpass == null) {
			if (other.rpass != null)
				return false;
		} else if (!rpass.equals(other.rpass))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegBean [uname=" + uname + ", email=" + email + ", pass=" + pass + ", rpass=" + rpass + "]";
	}
	
	public String Validate() {
		
		StringBuilder sb = new StringBuilder();
		
		if(uname==null || uname.isEmpty() || uname.trim().equals(""))
			sb.append("Name is invalid. Please try again<br/>");
		if(email==null || email.isEmpty() || email.trim().equals(""))
			sb.append("Email is invalid. Please try again<br/>");
		if(pass==null || pass.isEmpty() || pass.trim().equals(""))
			sb.append("Password is invalid. Please try again<br/>");
		if(pass!=null && !pass.equals(rpass))
			sb.append("Passwords don't match.Please Enter them again<br/>");
		
		
		String msg = sb.toString();
		if(msg == null || msg.isEmpty()) {
			return Constants.SUCCESS;
		}
		else
			return msg;
		
	}
	
}
