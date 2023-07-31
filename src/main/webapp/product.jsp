<%@ page language="java" contentType="text/html; charset=utf-8; text/css; text/javascript" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${sessionScope.currentPage == null || sessionScope.currentPage != 'product'}">
	<c:redirect url="/Controller?action=product"></c:redirect>
</c:if>

<%-- Chèn header với tham số title và đường dẫn css--%>
<c:import url="header.jsp">
	<c:param name="title" value="Product"></c:param>
	<c:param name="style" value="product.css"></c:param>
</c:import>

<div class="grid-container content">
	<c:set var="ft" value="${applicationScope.ft}"></c:set>
	<sql:transaction dataSource="jdbc/shoppingdb">
		<c:choose>
			<c:when test="${param.id == null || !ft.checkInt(param.id)}">
				<sql:query var="rs" sql="select * from products"></sql:query>
			</c:when>
			<c:otherwise>
				<sql:query var="rs" sql="select * from products where product_id = ${param.id}"></sql:query>
				<c:if test="${fn:length(rs.rows) == 0 }">
					<sql:query var="rs" sql="select * from products"></sql:query>
				</c:if>
			</c:otherwise>
		</c:choose>	
	</sql:transaction>

	<c:forEach items="${rs.rows}" var="list" varStatus="v">
		<div class="product_wrap">
			<h1 class="name">${list.product_name}</h1>
			<div class="content">
				<img alt="" src="${list.product_img_source}">
				<div class="info">
					<div>
						<p class="price"><ins>${ft.vnd(list.product_price * 1000000)}đ</ins>
						<!-- <del>${ft.vnd(list.product_price * 2000000)}đ</del></p>-->
						<p class="more">
						<c:set var = "splitDes" value = "${fn:split(list.product_des,',')}"/>
							<c:forEach items="${splitDes}" var="des">
								<c:out value="${des}"></c:out>
								<br>
							</c:forEach>
						</p>
					</div>
					<p class="buy_action">
						<c:choose>
							<c:when test="${sessionScope.user == null }">
								<a href='<c:url value="login"></c:url>'>Buy now</a>
								<a href='<c:url value="login"></c:url>'>Add to Cart</a>
							</c:when>
							<c:otherwise>
								<a class="buynow" href='<c:url value="/Controller?action=buynow&id=${list.product_id}"></c:url>'>Buy now</a>
								<span class="addcart" data-id="${list.product_id}" data-price="${ft.vnd(list.product_price * 1000000)}"
								data-img="${list.product_img_source}" data-name="${list.product_name}" data-url="<%=request.getServletPath()%>">Add to Cart</span>
							</c:otherwise>
						</c:choose>
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<%-- Chèn file footer.jsp vào trang chủ --%>
<c:import url="footer.jsp"></c:import>


