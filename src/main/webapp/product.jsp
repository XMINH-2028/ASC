<%@ page language="java" contentType="text/html; charset=utf-8; text/css; text/javascript" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${sessionScope.currentPage == null}">
	<c:redirect url="/Controller?action=product"></c:redirect>
</c:if>

<%-- Chèn header với tham số title và đường dẫn css--%>
<c:import url="header.jsp">
	<c:param name="title" value="Product"></c:param>
	<c:param name="style" value="product.css"></c:param>
</c:import>

<div class="grid-container content">
	<sql:transaction dataSource="jdbc/shoppingdb">
		<c:choose>
			<c:when test="${param.id == null || !applicationScope.ft.checkInt(param.id)}">
				<sql:query var="list" sql="select * from products"></sql:query>
			</c:when>
			<c:otherwise>
				<sql:query var="list" sql="select * from products where product_id = ${param.id}"></sql:query>
				<c:if test="${fn:length(list.rows) == 0 }">
					<sql:query var="list" sql="select * from products"></sql:query>
				</c:if>
			</c:otherwise>
		</c:choose>	
	</sql:transaction>

	<c:forEach items="${list.rows}" var="x" varStatus="st">
		<div class="product_wrap">
			<h1 class="name">${x.product_name}</h1>
			<div class="content">
				<img alt="" src="${x.product_img_source}">
				<div class="info">
				<sql:transaction dataSource="jdbc/shoppingdb">
					<sql:query var="currentPrice" sql="select FORMAT(?, 0, 'vi-VN') as value">
						<sql:param value="${x.product_price * 1000000}"></sql:param>
					</sql:query>
					<sql:query var="oldPrice" sql="select FORMAT(?, 0, 'vi-VN') as value">
						<sql:param value="${x.product_price * 1000000 * 2}"></sql:param>
					</sql:query>
				</sql:transaction>
					<div>
						<p class="price"><ins>${currentPrice.rows[0].value}đ</ins><del>${oldPrice.rows[0].value}đ</del></p>
						<p class="more">
						<c:set var = "splitDes" value = "${fn:split(x.product_des,',')}"/>
							<c:forEach items="${splitDes}" var="des">
								<c:out value="${des}"></c:out>
								<br>
							</c:forEach>
						</p>
					</div>
					<p>
						<span class="buynow" data-id="${row.product_id}" data-price="${price.rows[0].value}"
						data-img="${row.product_img_source}" data-name="${row.product_name}" data-url="${pageContext.servletContext.contextPath}">Buy now</span>
						<span class="addcart" data-id="${x.product_id}" data-price="${currentPrice.rows[0].value}"
						data-img="${x.product_img_source}" data-name="${x.product_name}" data-url="${pageContext.servletContext.contextPath}">Add to Cart</span>
					</p>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<%-- Chèn file footer.jsp vào trang chủ --%>
<c:import url="footer.jsp"></c:import>


