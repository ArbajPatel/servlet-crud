<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Register</h1>

<form action="RegInt.jsp" method = "post">

Enter the Name : <input type = "text" name ="uname" value="${reg.uname}"/><br/>
Enter your Email: <input type = "text" name ="email" value="${reg.email}"/><br/>
Enter the Password : <input type = "password" name ="pass" /><br/>
Repeat the Password : <input type = "password" name ="rpass"/><br/>

<!-- Show error messages if any -->
<h2>${errorMsg}</h2><br/>
<br/>
<br/>

<input type = "submit" value="Register">


</form>

</body>
</html>