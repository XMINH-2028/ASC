<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Cookie ck[] = request.getCookies();
String mail = getServletContext().getInitParameter("mail");
if (ck != null) {
	for (Cookie i : ck) {
		out.print(i.getName() + ": " + i.getValue() );
	}
}
%>
</body>
</html>