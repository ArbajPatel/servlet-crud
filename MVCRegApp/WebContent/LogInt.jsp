<jsp:useBean id="user" class = "com.uttara.mvc.LogBean" scope = "request">

<jsp:setProperty name = "user" property = "*"/>

</jsp:useBean>

<jsp:forward page="login.do"></jsp:forward>

