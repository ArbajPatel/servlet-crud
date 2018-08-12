<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Listing All The Contacts !</h1>
<br/>
<br/>
<br/>


<table align="left" bordercolor="DodgerBlue" bgcolor="Yellow"  border="1">

<tr>

<th>Name  </th>
<th>Email  </th>
<th>Phone </th>
<th>Tag  </th>
<th>DOB  </th>
<th>Gender  </th>

</tr>

<c:forEach items="${list}" var="bean">



<tr>

<td>${bean.uname}</td>
<td>${bean.email}</td>
<td>${bean.phone}</td>
<td>${bean.tag}</td>
<td>${bean.dob}</td>
<td>${bean.gender}</td>

</tr>



</c:forEach>
</table>
</body>
</html>