package com.uttara.mvc;

public class DAOFactory {

	public static AppDAO getDB() {
		
		switch(Constants.DB) {
		
		case "hsql" : 
			return new HSQLDbDAO();
		}
		return null;
	}
	
}
