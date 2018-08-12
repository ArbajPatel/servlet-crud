package com.uttara.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ControllerServlet
 */
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
       System.out.println("In CS's constr()");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       System.out.println("In CS's doGet()");
	       process(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In CS's doPost()");
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       System.out.println("In CS's process()");
	       //variables
	       String uri=request.getRequestURI();
	       RequestDispatcher rd = null;
	       ServiceModel sm = new ServiceModel();
	       //check the uri's
	       
	       if(uri.contains("/openRegisterView.do")) {
	    	   
	    	   System.out.println("IN CS's /openReg");
	    	   rd = request.getRequestDispatcher("Register.jsp");
	    	   rd.forward(request, response);
	       }
           if(uri.contains("/openLoginView.do")) {
	    	   
	    	   System.out.println("IN CS's /openLogin");
	    	   rd = request.getRequestDispatcher("Login.jsp");
	    	   rd.forward(request, response);
	       }
           
           //registering stuff
           if(uri.contains("/register.do")) {
        	   
        	   System.out.println("IN CS's /register");
        	   //extract the user data in the form of the bean 
        	   RegBean rb = (RegBean)request.getAttribute("reg");
        	   System.out.println("Bean Data "+rb);
        	   //forward this bean for validation over to the model
        	   String msg = sm.register(rb);
        	  
        	   //now do the post-processing
        	   if(msg.equals(Constants.SUCCESS)) {
        		   
        		   //forward to  a success page
        		   request.setAttribute("successMsg", "You have been successfully registered");
        		   rd = request.getRequestDispatcher("Success.jsp");
        		   rd.forward(request, response);
        	   }
        	   else {
        		   
        		   //put the error message into request and ask user to  re-enter the inputs
        		   request.setAttribute("errorMsg", "Registration Could Not Succeed <br/>"+msg);
        		   rd = request.getRequestDispatcher("Register.jsp");
        		   rd.forward(request, response);
        	   }
           }
           
           //viewing the registered users
           if(uri.contains("/viewRegisteredUsers.do")) {
        	   
        	   System.out.println("In /viewRegUsers ");
        	   List<Object> l = new ArrayList<Object>();
        	   l=sm.getUsers();
        	   System.out.println("Em list"+l);
        	   if(l!=null && !l.isEmpty()) {
        		   
        		   request.setAttribute("list", l);
        		   rd = request.getRequestDispatcher("AppUsers.jsp");
        		   rd.forward(request, response);
        	   }
        	   if(l.isEmpty()) {
        		   
        		   request.setAttribute("errorMsg", "List was empty. NO one has registered yet");
        		   rd = request.getRequestDispatcher("Error.jsp");
        		   rd.forward(request, response);
        	   }
        	   
        	  
           }
           
           //login
           if(uri.contains("/login.do")) {
        	   
        	   System.out.println("Inside /login");
        	   LogBean lb = (LogBean)request.getAttribute("user");
        	   System.out.println("User data "+lb);
        	   
        	   //use the model authenticate the user
        	   String msg = sm.authenticate(lb);
        	   
        	 //now do the post-processing
        	   if(msg.equals(Constants.SUCCESS)) {
        		   
        		   //user has successfully logged in
        		   HttpSession s = request.getSession(true);
        		   s.setAttribute("userLog", lb.getEmail());
        		   request.setAttribute("successMsg", "You have successfully Logged in. Welcome");
        		   rd = request.getRequestDispatcher("Menu.jsp");
        		   rd.forward(request, response);
        	   }
        	   else {
        		   
        		   //put the error message into request and ask user to  re-enter the inputs
        		   request.setAttribute("errorMsg", "Login Could Not Succeed <br/>"+msg);
        		   rd = request.getRequestDispatcher("Login.jsp");
        		   rd.forward(request, response);
        	   }
        	   
           }
           
           //edit
           if(uri.contains("/openEditAccountView.do")) {
        	   
        	   //first obtain the email from the session, which would've been created when the user
        	   //would have logged in
        	   HttpSession s = request.getSession(false);
        	   if(s!=null) {
        		   
        		   //user is logged in
        		   String email = (String) s.getAttribute("userLog");
        		   //pass this to model;
        		   RegBean rb = sm.getDetails(email);
        		   request.setAttribute("userBean", rb);
        		   rd = request.getRequestDispatcher("EditAccount.jsp");
        		   rd.forward(request, response);
        		   
        	   }
        	   else {
        		   //it means session has timed out.
        		   request.setAttribute("errorMsg", "The Session Has Timed Out");
        		   rd = request.getRequestDispatcher("Error.jsp");
            	   rd.forward(request, response);
        	   }
        	  
        	          	   
        	          	   
           }
           
           //updating after the edit
           if(uri.contains("/update.do")) {
        	   
        	   System.out.println("In /update");
        	   RegBean rb = (RegBean) request.getAttribute("reg");
        	   System.out.println(rb);
        	   
        	   //send the bean to SM's update/re-write method
        	   String msg = sm.updateDetails(rb);
               if(msg.equals(Constants.SUCCESS)) {
        		   
        		   //user has successfully logged in
        		   HttpSession s = request.getSession(true);
        		   s.setAttribute("userLog", rb.getEmail());
        		   request.setAttribute("successMsg", "Your account has been successfully updated.");
        		   rd = request.getRequestDispatcher("Success.jsp");
        		   rd.forward(request, response);
        	   }
        	   else {
        		   
        		   //put the error message into request and ask user to  re-enter the inputs
        		   request.setAttribute("errorMsg", "The update was unsuccessfull <br/>"+msg);
        		   rd = request.getRequestDispatcher("EditAccount.jsp");
        		   rd.forward(request, response);
        	   }
       
           }
           
           
           if(uri.contains("/backtoMenu.do")) {
        	   
        	   rd  = request.getRequestDispatcher("Menu.jsp");
        	   rd.forward(request, response);
           }
           
           
           //logout
           if(uri.contains("/logout.do")) {
        	   
        	   HttpSession s = request.getSession(false);
        	   if(s!=null) {
        		   
        		   s.removeAttribute("userLog");
        		   s.removeAttribute("cname");
        		   s.invalidate();
        	   }
        	   else {
        		   
        		   //you must say session has timed out
        		  // request.setAttribute("sessTimeOut", "The Session Has Timed Out");
        		   
        	   }
        	   response.sendRedirect("Home.html");
           }
           
           ////////////////////contacts edition//////////////////
           
           //for add contact
           if(uri.contains("/openAddContactView.do")) {
        	   
        	   System.out.println("In /addcontactview");
        	   rd = request.getRequestDispatcher("AddContact.jsp");
        	   rd.forward(request, response);
           }
           //add from bean to DB using service
           if(uri.contains("/addcontact.do")) {
        	   
        	   System.out.println("In /addcon with conBean");
        	   ConBean cb = (ConBean)request.getAttribute("con");
        	   System.out.println(cb);
        	   
        	   //now we have the bean. we have to add the info into the DB
        	   //invoke SM's addContact method and check the returned string for success/failure
        	   HttpSession s = request.getSession(false);
        	   if(s!=null) {
        		  
        	   String email = (String) s.getAttribute("userLog");   
        	   System.out.println("Session''s email "+email);
        	   String msg = sm.addContact(cb,email);
        	   
        	   if(msg.equals(Constants.SUCCESS)) {
        		   
        		   request.setAttribute("successMsg", "Your contact has been added successfully</br>");
        		   rd = request.getRequestDispatcher("Success.jsp");
        		   rd.forward(request, response);
        		   
        	   }
        	   else {
        		   
        		   request.setAttribute("errorMsg", "Your contact couldnt be added</br>"+msg);
        		   rd = request.getRequestDispatcher("AddContact.jsp");
        		   rd.forward(request, response);
        	   }
        	   }
        	   else {
        		   //forward to error page;
        		   request.setAttribute("errorMsg", "The Session Has Timed Out");
        		   rd = request.getRequestDispatcher("Error.jsp");
            	   rd.forward(request, response);
        	   }
           }
           
           //search using a string
           if(uri.contains("/openSearchView.do")) {
        	   
        	   System.out.println("In /searchView");
        	   rd = request.getRequestDispatcher("Search.jsp");
        	   rd.forward(request, response);
           }
           //search using a method from service.
           if(uri.contains("/search.do")) {
        	   
        	   System.out.println("in /search.do");
        	   String search = request.getParameter("word");
        	   System.out.println("Search Val = "+search);
               
        	   //send the string over to the getContacts implementation in Service
        	   List<Object> l = new ArrayList<Object>();
        	   
        	   if(search==null || search.isEmpty() ||search.trim().equals("")) {
        		  
        		   request.setAttribute("errorMsg", "Invalid value. Please try again<br/>");
        		   rd = request.getRequestDispatcher("Search.jsp");
            	   rd.forward(request, response);
            	   
        	   }
        	   else {
        		   
            	   //
            	   HttpSession s = request.getSession(false);
        		   if(s!=null) {
        			   String email = (String) s.getAttribute("userLog");
        			   
        		       l = sm.searchContacts(search,email);
        		       
        		       System.out.println("Em list"+l);
                	   
                	   if(l!=null && !l.isEmpty()) {
                		   
                		   request.setAttribute("list", l);
                		   rd = request.getRequestDispatcher("SearchResults.jsp");
                		   rd.forward(request, response);
                	   }
                	   if(l.isEmpty()) {
                		   
                		   request.setAttribute("errorMsg", "No match found");
                		   rd = request.getRequestDispatcher("Temporary.jsp");
                		   rd.forward(request, response);
                	   }
        		   }
        		   else {
        			 //forward to error page;
            		   request.setAttribute("errorMsg", "The Session Has Timed Out");
            		   rd = request.getRequestDispatcher("Error.jsp");
                	   rd.forward(request, response);        			   
        		   }
        	   }
           }
           
           //listing contacts
           if(uri.contains("/openListContactsView.do")) {
        	   
        	   System.out.println("In ListCV.do");
        	   rd = request.getRequestDispatcher("ListContacts.jsp");
        	   rd.forward(request, response);
           }
           if(uri.contains("/listcontacts.do")) {
        	   
        	   System.out.println("in listcontacts");
        	   String ch = request.getParameter("listC");
        	   
        	   //send the choice over to the SM who'll get the ConBean list from the DAO and then sort using comparators
        	   //the SM's method will then return a list of sorted ConBeans
        	   //we'll have to forward the list to a view and display it in a tabular format!
        	   
        	   List<Object> l = new ArrayList<Object>();
        	   
        	   HttpSession s = request.getSession(false);
        	   if(s!=null) {
        		  
        	   String email = (String) s.getAttribute("userLog");   
        	   System.out.println("Session''s email "+email);
        	   
        	   l = sm.listContacts(ch,email);
        	   System.out.println("Em list"+l);
        	   
        	   if(l!=null && !l.isEmpty()) {
        		   
        		   request.setAttribute("list", l);
        		   rd = request.getRequestDispatcher("ListResults.jsp");
        		   rd.forward(request, response);
        	   }
        	   if(l!=null && l.isEmpty()) {
        		   
        		   request.setAttribute("errorMsg", "List was empty. NO contact has been added yet");
        		   rd = request.getRequestDispatcher("Temporary.jsp");
        		   rd.forward(request, response);
        	   }
        	   
        	   }
        	   else {
        		   
        		   request.setAttribute("errorMsg", "The Session Has Timed Out");
        		   rd = request.getRequestDispatcher("Error.jsp");
            	   rd.forward(request, response);
        		   
        	   }
           }
           
           
           //editing the contacts......
           if(uri.contains("/openEditContactView.do")) {
        	   
        	   System.out.println("In openEditCon.view");
        	   rd = request.getRequestDispatcher("EditContact.jsp");
        	   rd.forward(request, response);
           }
           if(uri.contains("/editC.do")) {
        	   
        	   System.out.println("in editC.do");
        	  String name = request.getParameter("ename");
        	  List<Object> l = new ArrayList<Object>();
        	  //get the email of the person logged in. send both those params to ListEdit() method of service
        	  HttpSession s = request.getSession(false);
        	  if(s!=null) {
        		  
        		  String email = (String) s.getAttribute("userLog");
        		  System.out.println("Email "+email);
        		  
        		  //invoke the listEdit
        		  l = sm.listEdit(name,email);
        		  System.out.println("List "+l);
        		  
        		  if(l!=null && !l.isEmpty()) {
        			  
        		   request.setAttribute("list", l);
           		   rd = request.getRequestDispatcher("ListEditContacts.jsp");
           		   rd.forward(request, response);
        			  
        		  }
        		  if(l==null){
        			  
       			   request.setAttribute("errorMsg", "Invalid Input.Please try again<br/>");
             		   rd = request.getRequestDispatcher("EditContact.jsp");
             		   rd.forward(request, response);
       		      }
        		  if(l!=null && l.isEmpty()) {
           		   
           		   request.setAttribute("errorMsg", "List was empty. NO match found ");
           		   rd = request.getRequestDispatcher("Error.jsp");
           		   rd.forward(request, response);
           	      }
        		 
        		  
        	  }
        	  else {
        		  
        		  request.setAttribute("errorMsg", "The Session Has Timed Out");
       		   rd = request.getRequestDispatcher("Error.jsp");
           	   rd.forward(request, response);
        		  
        	  }
           }
           //changeover from edit into update!!
           //part1
           if(uri.contains("/openUpdateContactViewE.do")) {
        	   
        	   System.out.println("In open update ConV Edit");
        	   String name = request.getParameter("ename");
        	   System.out.println("Name = "+name);
        	   //we have the name of the contact we need to edit
        	   //we just need to call the listEdit() method again to retrieve the contact
        	   //upon retrieval, pass the bean to updateContacts.jsp
        	   
        	   List<Object> l = new ArrayList<Object>();
         	  //get the email of the person logged in. send both those params to ListEdit() method of service
         	  HttpSession s = request.getSession(false);
         	  if(s!=null) {
         		  s.setAttribute("cname", name);
         		  String email = (String) s.getAttribute("userLog");
         		  System.out.println("Email "+email);
         		  
         		  //invoke the listEdit
         		  l = sm.listEdit(name,email);
         		  System.out.println("List "+l);
         		  
         		  if(l!=null && !l.isEmpty()) {
         			 
         			   ConBean cb = (ConBean)l.get(0);
         		       request.setAttribute("con", cb);
            		   rd = request.getRequestDispatcher("UpdateContacts.jsp");
            		   rd.forward(request, response);
         			  
         		  }
         		  /*if(l==null){
         			  
        			   request.setAttribute("errorMsg", "Invalid Input.Please try again<br/>");
              		   rd = request.getRequestDispatcher("EditContact.jsp");
              		   rd.forward(request, response);
        		      }
         		    if(l!=null && l.isEmpty()) {
            		   
            		   request.setAttribute("errorMsg", "List was empty. NO match found ");
            		   rd = request.getRequestDispatcher("Error.jsp");
            		   rd.forward(request, response);
            	      }*/
         		 
         		  
         	  }
         	  else {
         		  
         		  request.setAttribute("errorMsg", "The Session Has Timed Out");
        		   rd = request.getRequestDispatcher("Error.jsp");
            	   rd.forward(request, response);
         		  
         	  }
        	   
           }
           //part 2
           if(uri.contains("/updatecontact.do")) {
        	   
        	   System.out.println("Finally!! in update contacts...");
        	   //we get the updated ConBean , and then using the name,email as fixtures, we get into the DB
        	   //we kinda use the add method again...except that were updating using the 
        	   //contact name as a pivot.
        	  
        	   
        	   ConBean cb = (ConBean)request.getAttribute("con");
        	   System.out.println(cb);
        	   
        	   //now we have the bean. we have to add the info into the DB
        	   //invoke SM's addContact method and check the returned string for success/failure
        	   HttpSession s = request.getSession(false);
        	  
        	   if(s!=null) {
        	   
        	   String name = (String) s.getAttribute("cname");
        	   System.out.println("Name = "+name);
        	   String email = (String) s.getAttribute("userLog");   
        	   System.out.println("Session''s email "+email);
        	   String msg = sm.UpdateContact(cb,email,name);
        	   
        	   if(msg.equals(Constants.SUCCESS)) {
        		   
        		   request.setAttribute("successMsg", "Your contact has been updated successfully</br>");
        		   rd = request.getRequestDispatcher("Success.jsp");
        		   rd.forward(request, response);
        		   
        	   }
        	   else {
        		   
        		   request.setAttribute("errorMsg", "Your contact couldnt be updated</br>"+msg);
        		   rd = request.getRequestDispatcher("UpdateContacts.jsp");
        		   rd.forward(request, response);
        	   }
        	   }
        	   else {
        		   //forward to error page;
        		   request.setAttribute("errorMsg", "The Session Has Timed Out");
        		   rd = request.getRequestDispatcher("Error.jsp");
            	   rd.forward(request, response);
        	   }
        	   
           }
           
          //changeover from edit into delete!!
          if(uri.contains("/openUpdateContactViewD.do")) {
        	   
        	   System.out.println("In open update ConV Delete");
        	   String name = request.getParameter("ename");
        	   System.out.println("Name = "+name);
        	   
        	   HttpSession s = request.getSession(false);
        	   if(s!=null) {
        		  
        	   String email = (String) s.getAttribute("userLog");   
        	   System.out.println("Session''s email "+email);
        	   String msg = sm.deleteContact(name);
        	   
        	   if(msg.equals(Constants.SUCCESS)) {
        		   
        		   request.setAttribute("successMsg", "Your contact has been deleted successfully</br>");
        		   rd = request.getRequestDispatcher("Success.jsp");
        		   rd.forward(request, response);
        		   
        	   }
        	   else {
        		   
        		   request.setAttribute("errorMsg", "Your contact couldnt be deleted</br>"+msg);
        		   rd = request.getRequestDispatcher("UpdateContacts.jsp");
        		   rd.forward(request, response);
        	   }
        	   }
        	   else {
        		   //forward to error page;
        		   request.setAttribute("errorMsg", "The Session Has Timed Out");
        		   rd = request.getRequestDispatcher("Error.jsp");
            	   rd.forward(request, response);
        	   }
        	   
           }
          
          //birthday reminder
          if(uri.contains("/openBirthdayReminderView")) {
        	  
        	  System.out.println("in /BirthDay Reminder");
        	  List<Object> l = new ArrayList<Object>();
       	   
       	   HttpSession s = request.getSession(false);
       	   if(s!=null) {
       		  
       	   String email = (String) s.getAttribute("userLog");   
       	   System.out.println("Session''s email "+email);
       	   
       	   l = sm.getBirthdayContacts(email);
       	   System.out.println("Em list"+l);
       	   
       	   if(l!=null && !l.isEmpty()) {
       		   
       		   request.setAttribute("list", l);
       		   rd = request.getRequestDispatcher("BirthdayReminder.jsp");
       		   rd.forward(request, response);
       	   }
       	   if(l.isEmpty()) {
       		   
       		   request.setAttribute("errorMsg", "Nobody's birthday is coming up soon.. we gotta wait</br>");
       		   rd = request.getRequestDispatcher("Temporary.jsp");
       		   rd.forward(request, response);
       	   }
       	   
       	   }
       	   else {
       		   
       		   request.setAttribute("errorMsg", "The Session Has Timed Out");
       		   rd = request.getRequestDispatcher("Error.jsp");
           	   rd.forward(request, response);
       		   
       	   }
        	  
          }
        	   

	}

}
