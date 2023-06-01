<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Chèn header với tham số title và đường dẫn css--%>
<c:import url="header.jsp">
	<c:param name="title" value="Home"></c:param>
	<c:param name="style" value="home.css"></c:param>
</c:import>

<%-- Nội dung trang chủ --%>
<div class="grid-container content">
	<div class="grid-item main">
		<h1>All products</h1>
		<%--Tạo biến lưu thông tin trang hiện tại --%>
		<c:set var="page" value="${param.page == null ? 1 :  param.page}"></c:set>
		<%--Tạo biến lưu số lượng sản phẩm xuất hiện tối đa mỗi trang --%>
		<c:set var="pagesize" value="12"></c:set>
		<sql:transaction dataSource="jdbc/shoppingdb">
			<%--Lấy data tất cả sản phẩm --%>
			<sql:query var="rs" sql="select * from products"></sql:query>
			<%--Lặp qua tất cả các sản phẩm --%>
			<c:forEach items="${rs.rows}" var="row" varStatus="vs">
				<%--Hiển thị sản phẩm theo số trang hiện tại--%>
				<c:if test="${(vs.index >= (page - 1) * pagesize) && (vs.index <= (page * pagesize - 1))}">
					<%--Định dạng đơn vị tiền tệ --%>
					<sql:query var="price" sql="select concat(FORMAT(?, 0, 'vi-VN'),'đ') as value">
						<sql:param value="${row.product_price * 1000000}"></sql:param>
					</sql:query>
					<%--Hiển thị thông tin sản phẩm --%>
					<div class="home_wrap">
						<img alt="${row.product_name}"
							src="${row.product_img_source}">
						<div class="info">
							<p class="name">${row.product_name}</p>
							<p class="price">
								${price.rows[0].value}<span class="discount">-50%</span>
							</p>
							<p class="more">
								5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
							</p>
						</div>
						<a href='<c:url value="/Controller?action=product&id=${row.product_id}"></c:url>' class="productinfo"></a>
						<a href='<c:url value="#"></c:url>' class="addcart">Add to Cart</a>
					</div>
				</c:if>
			</c:forEach>	
		</sql:transaction>
	</div>
	<div class="grid-item side">
		<div class="popular">
			<h1>Popular products</h1>
			<ul>
				<sql:transaction dataSource="jdbc/shoppingdb">
					<%--Đếm số lượng thương hiệu--%>
					<sql:query var="rs" sql="select distinct product_brand from products"></sql:query>
					<%--Hiển thị sản phẩm theo thương hiệu --%>
					<c:forEach items="${rs.rows}" var="row">
						<li><span><i class='fas fa-angle-right control'></i> ${row.product_brand}</span></li>
						<li class="more">
							<ul>
								<sql:query var="brand" sql="select * from products where product_brand = ?">
									<sql:param>${row.product_brand}</sql:param>
								</sql:query>
								<c:forEach var="row" items="${brand.rows}" varStatus="vs">
									<%--Hiển thị nhiều nhất 5 sản phẩm --%>
									<c:if test="${vs.index <= 4}">
										<sql:query var="price" sql="select concat(FORMAT(?, 0, 'vi-VN'),'đ') as value">
											<sql:param value="${row.product_price * 1000000}"></sql:param>
										</sql:query>
										<li class="home_wrap">
											<img alt="${row.product_name}" src="${row.product_img_source}">
											<div class="info">
												<p class="name">${row.product_name}</p>
												<p class="price">
													${price.rows[0].value}<span class="discount">-50%</span>
												</p>
												<p class="more">
													5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
												</p>
											</div>
											<a href='<c:url value="/Controller?action=product&id=${row.product_id}"></c:url>' class="productinfo"></a>
											<a href='<c:url value="#"></c:url>' class="addcart">Add to Cart</a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</sql:transaction>
			</ul>
		</div>
		
		<div class="filter">
			<h1><i class='fas fa-filter'></i><span>Filter</span></h1>
			<form action="<c:url value='/Controller'></c:url>">
				<input type="hidden" name="url" value="<%= request.getServletPath()%>">
				<input type="hidden" name="action" value="search">
				<input type="hidden" name="type" value="1">
				<div class="price filter_child">
					<p class="title">Price</p>
					<div class="content">
						<label>Under 2 million<input type="checkbox" name="p0" value="0"><span class="checkmark"></span></label>
						<label>2 - 4 million<input type="checkbox" name="p1" value="1"><span class="checkmark"></span></label>
						<label>4 - 7 million<input type="checkbox" name="p2" value="2"><span class="checkmark"></span></label>
						<label>7 - 13 million<input type="checkbox" name="p3" value="3"><span class="checkmark"></span></label>
						<label>13 - 20 million<input type="checkbox" name="p4" value="4"><span class="checkmark"></span></label>
						<label>Over 20 million<input type="checkbox" name="p5" value="5"><span class="checkmark"></span></label>
						<p><button type="submit">See result</button></p>
					</div>
				</div>
			</form>
			<form action="<c:url value='/Controller'></c:url>">
				<input type="hidden" name="action" value="search">
				<input type="hidden" name="type" value="2">
				<div class="price filter_child">
					<p class="title">Brand</p>
					<div class="content">
						<label>Apple<input type="checkbox" name="b0" value="apple"><span class="checkmark"></span></label>
						<label>Samsung<input type="checkbox" name="b1" value="samsung"><span class="checkmark"></span></label>
						<p><button type="submit">See result</button></p>
					</div>
				</div>
			</form>
		</div>
	</div>
	<sql:transaction dataSource="jdbc/shoppingdb">
		<%--Đếm tổng số lượng sản phẩm  --%>
		<sql:query var="rs" sql="select count(*) as num from products"></sql:query>
		<%--Nếu số lượng sản phẩm lớn hơn pagesize thì hiện pagination --%>
		<c:if test="${rs.rows[0].num > pagesize }">
			<div class="pagination">
				<%--Thay đổi biến lưu trang khi ấn nút chuyển tới trang trước --%>
				<a href="<c:url value="/home?page=${page == 1 ? 1 : page - 1}"></c:url>" class="prev">&laquo;</a>
					<%--Tính tổng số trang theo số lượng sản phẩm  --%>
					<c:choose>
						<c:when test="${rs.rows[0].num % pagesize == 0}">
							<c:set var="total" value="${fn:substringBefore(rs.rows[0].num / pagesize, '.')}"></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="total" value="${fn:substringBefore(rs.rows[0].num / pagesize, '.') + 1}"></c:set>
						</c:otherwise>
					</c:choose>
					<%--Hiển thị danh sách trang--%>
					<c:forEach begin="1" end="${total}" step="1" varStatus="v">
						<c:choose>
							<c:when test="${page == v.index}">
								<a href="<c:url value="/home?page=${v.index}"></c:url>" class="page active">${v.index}</a>
							</c:when>
							<c:otherwise>
								<a href="<c:url value="/home?page=${v.index}"></c:url>" class="page">${v.index}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				<%--Thay đổi biến lưu trang khi ấn nút chuyển tới trang tiếp theo --%>
			  	<a href="<c:url value="/home?page=${page == total ? total : page + 1}"></c:url>" class="next">&raquo;</a>
			</div>
		</c:if>
	</sql:transaction>
	<script type="text/javascript" src='<c:url value="/js/home.js"></c:url>'></script>
</div>

<%-- Chèn file footer.jsp vào trang chủ --%>
<c:import url="footer.jsp"></c:import>
	
</body>
</html>