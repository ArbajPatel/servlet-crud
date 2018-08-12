<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Update Contact</h1>

<form action="UpdateContactInt.jsp" method = "post">

Contact Name : <input type = "text" name ="uname" value="${con.uname}" /><br/>
Enter your Email(s): <input type = "text" name ="email" value="${con.email}" /><br/>
Enter the PhoneNumber(s) : <input type = "text" name ="phone" value="${con.phone}"/><br/>
Enter the Tags : <input type = "text" name ="tag" value="${con.tag}"/><br/>
Enter the DOB(dd/mm/yyyy) : <input type = "text" name ="dob" value="${con.dob}"/><br/>
Select the Gender : 
M : <input type = "radio" name = "gender" value="M"/>  
F : <input type = "radio" name = "gender" value="F"/>
T : <input type = "radio" name = "gender" value="T"/>
<!-- Show error messages if any -->
<h2>${errorMsg}</h2><br/>
<br/>
<br/>

<input type = "submit" value="Update Contact">


</form>

</body>
</html>