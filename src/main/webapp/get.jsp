<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="controller.*,java.util.*,database.*,javax.sql.DataSource,beans.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="admin" class="beans.Testbean" scope="application"></jsp:useBean>
<jsp:setProperty name="admin" property="age" value="20"/>
<%--
	Account forget = (Account)session.getAttribute("register");
 	out.print(forget.getAction());
	out.print(new Date());
--%>


<p>User: <%= admin.getAge() %></p>



<% Testbean a = new Testbean(); out.print(a.age);%>



</body>
</html>