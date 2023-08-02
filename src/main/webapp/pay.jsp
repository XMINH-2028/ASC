<%@ page language="java" contentType="text/html; text/css; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${sessionScope.user == null}">
	<c:redirect url="login"></c:redirect>
</c:if>
<c:if test="${!sessionScope.user.checkPay}">
	<c:redirect url="Controller?action=pay"></c:redirect>
</c:if>
<c:out value="${sessionScope.user.setCheckPay(false)}" ></c:out>
<c:set var="user" value="${sessionScope.user}"></c:set>
<c:set var="ft" value="${applicationScope.ft}"></c:set>
<c:set var="list" value="${sessionScope.cart.productList}"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'></c:url>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/cart.css'></c:url>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/pay.css'></c:url>">
<title>Pay</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>
<div class="container">
	<div class="top">
		<p class="totalMoney"><a href="<c:url value = 'cart'></c:url>"><i class='fas fa-reply back'></i></a>
			Total:<span class="total">${ft.vnd(sessionScope.cart.totalCart() * 1000000)}đ</span></p>
		<button>Order</button>
	</div>
	<form class="contact">
		<table>
			<tr>
				<th colspan="2">Receiver's information:</th>
			</tr>
			<tr>
				<td>Name:</td>
				<td><input id="name" value="${user.name}"><span class="checkName check"></span></td>
			</tr>
			<tr>
				<td>Phone:</td>
				<td><input id="phone" value="${user.phone}"><span class="checkPhone check"></span></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><p id="address">${ft.getAddress(user.address)}</p>
				<button type="button">Edit</button><span class="checkAddress check"></span></td>
			</tr>
		</table>
	</form>
	<form class="product">
		<c:forEach var="item" items="${list}">
			<c:if test="${item.isChecked()}">
				<div class="cart_product">
					<p><img src="${item.img}" class="link"></p>
					<div class="product_info">
						<p class="name">${item.name}</p>
						<p class="price">${applicationScope.ft.vnd(item.price * 1000000)}đ</p>
					</div>
					<div class="quantity">
						<p>Quantity: ${item.quantity}</p>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</form>
</div>
<script type="text/javascript" src="<c:url value='/js/script.js'></c:url>"></script>
<script type="text/javascript" src="<c:url value='/js/pay.js'></c:url>"></script>
</body>
</html>