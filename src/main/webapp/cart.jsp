<%@ page language="java" 
contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'></c:url>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/cart.css'></c:url>">
<title>Cart</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>
<c:if test="${sessionScope.user == null}">
	<c:redirect url="login"></c:redirect>
</c:if>
<c:if test="${!sessionScope.cart.check}">
	<c:redirect url="/Controller?action=cart&page=${sessionScope.cart.page}"></c:redirect>
</c:if>
<c:out value="${sessionScope.cart.setCheck(false)}" ></c:out>
<c:set var="list" value="${sessionScope.cart.productList}"></c:set>

<ul class="top">
	<li><a href='<c:url value="${sessionScope.currentPage}"></c:url>' class="back">Back</a></li>
	<li><a class="${sessionScope.cart.page == 'selected' ? 'active' : ''}" 
	href='<c:url value="/Controller?action=cart&page=selected"></c:url>'>Selected</a></li>
	<li><a class="${sessionScope.cart.page == 'ordered' ? 'active' : ''}"
	href='<c:url value="/Controller?action=cart&page=ordered"></c:url>'>Ordered</a></li>
</ul>
<form class="cart">
	<c:forEach var="item" items="${list}">
		<div class="cart_product">
			<c:choose>
				<c:when test="${item.checked}">
					<label class="checkmark">
						<input type="hidden"> 
					</label>
				</c:when>
				<c:otherwise>
					<label>
						<input type="hidden"> 
					</label>
				</c:otherwise>
			</c:choose>
			
			<p><a href='<c:url value="/Controller?action=product&id=${item.id}"></c:url>' class="link">
			<img src="${item.img}"></a></p>
			<div class="product_info">
				<p class="name">${item.name}</p>
				<p class="price" data-price="${item.price}">${applicationScope.ft.vnd(item.price * 1000000)}đ</p>
			</div>
			<div class="quantity">
				<p>Quantity: <span class="reduce">-</span><input class="number" type="text" 
				data-id="${item.id}" value="${item.quantity}">
				<span class="increase">+</span>
				</p>
			</div>
			<span class="close" data-id="${item.id}">+</span>
		</div>
	</c:forEach>
	
	<div class="pay">
		<p>Total: <span class="total">${applicationScope.ft.vnd(sessionScope.cart.totalCart() * 1000000)}đ</span></p>
		<button type="submit">Pay</button>
	</div>
</form>
<script type="text/javascript" src='<c:url value="/js/script.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/js/cart.js"></c:url>'></script>
</body>
</html>
