<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "com.uttara.mvc.RegBean,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>App Users</h1>
----------------------------------------------------------------------------------------
<%

List l = (List)request.getAttribute("list");
if(l!=null){
for(Object o:l){
	
	RegBean rb = (RegBean)o;
	out.println("<h2>Name :"+rb.getUname()+ " Email : "+rb.getEmail()+"</h2></br>");
	
}
}
else{
	
	out.println("Error. OOps <br/>");
}

%><br/>




</body>
</html>