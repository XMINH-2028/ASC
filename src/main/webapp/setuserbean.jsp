<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="admin" class="bean.User" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="admin" />
<% if (request.getParameter("username") == null || request.getParameter("username").equals("")) { %>
	<jsp:setProperty property="username" name="admin" value="" />
<% } %>
<% if (request.getParameter("password") == null || request.getParameter("password").equals("")) { %>
	<jsp:setProperty property="password" name="admin" value="" />
<% } %>
<% response.sendRedirect("home"); %>
</body>
</html>