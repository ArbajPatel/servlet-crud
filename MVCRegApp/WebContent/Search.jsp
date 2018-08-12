<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Search</h1>
<br/>
<h2>Search for a Contact using a String</h2>

<form action="search.do" method = "post">

Enter the String : <input type = "text" name = "word" value = "${word}"/><br>

<h3>${errorMsg}</h3>

<input type = "submit" value="Search"/>

</form>
</body>
</html>