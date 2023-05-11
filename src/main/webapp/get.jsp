<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="user" class="beans.Testbean" scope="session"></jsp:useBean>
<jsp:setProperty property="age" name="user" value = "123"/>
<jsp:getProperty property="age" name="user"/>
</body>
</html>