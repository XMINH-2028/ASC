<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%--
Cookie ck[] = request.getCookies();
String mail = getServletContext().getInitParameter("mail");
if (ck != null) {
	for (Cookie i : ck) {
		out.print(i.getName() + ": " + i.getValue() );
	}
}
--%>
<sql:setDataSource dataSource="jdbc/shoppingdb" var="ds"/>
<sql:query var="rs" sql="select * from products where product_id = ?" dataSource="${ds}">
	<sql:param>${param.product_id}</sql:param>
</sql:query>
<c:set var="image" value="${rs.rows[0]}"></c:set>
<img alt="" src="${image.product_img_source}" style="width: 200px">
</body>
</html>