<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>List Contacts</h1>

<form action="listcontacts.do" method = "post">

<select name = "listC">
<option  value = "name">Name</option>
<option  value = "dob">DOB</option>
<option  value = "gender">Gender</option>
</select>
<br/>
<br/>
<br/>

<input type ="submit" value = "List"/>

</form>

</body>
</html>