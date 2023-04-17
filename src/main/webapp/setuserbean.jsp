<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="user" class="bean.User" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="user" />
<% if (request.getParameter("username").equals("")) { %>
	<jsp:setProperty property="username" name="user" value="" />
<% } %>
<% if (request.getParameter("password").equals("")) { %>
	<jsp:setProperty property="password" name="user" value="" />
<% } %>
<% response.sendRedirect("home"); %>
</body>
</html>