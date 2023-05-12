<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="user1" class="beans.Testbean" scope="session"></jsp:useBean>

<c:out value="${user1.age}"></c:out>

<p>user1: <%= user1.age %></p>

<p>user1: <jsp:getProperty property="age" name="user1"/></p>

<p>Name: <c:out value="${param.name}"></c:out></p>

<c:if test="${param.name == 'Minh' }">
I am Minh
</c:if>

<c:choose>
	<c:when test="${param.name == 'Minh'}">
		<p><b>I am Minh</b></p>
	</c:when>
	<c:when test="${param.name == 'Nam' }">
		<p><b>I am Nam</b></p>
	</c:when>
	<c:otherwise>
		<p><b>I am Minh or Nam</b></p>
	</c:otherwise>
</c:choose>

<c:forEach var="i" begin="0" end="10" step="2" varStatus="status">
	Loop counter is: <c:out value="${i}"></c:out><br>
	<c:if test="${status.last}">
		<c:out value="${status}"></c:out>
	</c:if>
</c:forEach>

<c:out value="${user2['Minh']}"></c:out>

<c:out value="${link}"></c:out>

<%= session.getAttribute("link") %>

${link}

<table style="border: 1px solid black">
	<tr>
	<c:forEach var="i" items="${list}">
		<td style="border: 1px solid black">Number: ${i}</td>
	</c:forEach>
	</tr>
</table>
<% String text = "123"; 
	int num = 123;	
%>

<c:set var="number" value="123"/>

<c:out value="${number/12}"/>
<c:out value="${num/12}"/>

${empty""}
${empty"123"}
${empty text}

${paramValues.a[0]}

<c:if test="${param.c == null}">
I am Minh
</c:if>

<sql:setDataSource var="ds" dataSource="jdbc/shoppingdb"/>
<sql:query var="rs" dataSource="${ds}" sql="select * from account"></sql:query>

<table style="border: 1px solid black">
	<c:forEach var="i" items="${rs.rows}">
	<tr>
		<td style="border: 1px solid black">${i.user_name}</td>
		<td style="border: 1px solid black">${i.password}</td>
	</tr>
	</c:forEach>
</table>

<sql:query var="rs" dataSource="${ds}" sql="select * from products"></sql:query>

<table style="border: 1px solid black">
	<c:forEach var="i" items="${rs.rows}" varStatus="x">
	<c:if test="${x.index % 2 == 0}">
		<tr>
	</c:if>
		<td style="border: 1px solid black">
			<a href='<c:url value="/test.jsp?product_id=${i.product_id}"></c:url>'>
				<img alt="" src="${i.product_img_source}" style="width: 100px">
			</a>
		</td>
	<c:if test="${x.index % 2 == 1}">
		</tr>
	</c:if>
	</c:forEach>
</table>

<c:if test="${3 + 1 % 2 == 0}">
I am Minh
</c:if>

<c:out value="${fn:length(list)}"></c:out>

<c:out value="${fn:substring('Minh',0,1)}"></c:out>

</html>