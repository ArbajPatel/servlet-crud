<jsp:useBean id="con" class = "com.uttara.mvc.ConBean" scope = "request">

<jsp:setProperty name = "con" property = "*"/>

</jsp:useBean>

<jsp:forward page="updatecontact.do"></jsp:forward>