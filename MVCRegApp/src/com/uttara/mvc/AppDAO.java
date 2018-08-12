package com.uttara.mvc;


import java.util.List;

public interface AppDAO {

	public String create(RegBean rb);
	public String update(RegBean rb);
	public String delete(String name);
	public List<Object> readAll();
	public RegBean read(String email);
	public String check(LogBean lb);
	public String createContact(ConBean cb,String email);
	public List<Object> getContacts(String ch,String email);
	public List<Object> listEdit(String name, String email);
	public String updateContact(ConBean cb, String email, String name);
	public List<Object> searchContacts(String search, String email);
	public List<Object> birthdayList(String email);
}
