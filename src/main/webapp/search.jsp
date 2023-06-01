<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Chèn header với tham số title và đường dẫn css--%>
<c:import url="header.jsp">
	<c:param name="title" value="Search"></c:param>
	<c:param name="style" value="home.css"></c:param>
</c:import>

<%-- Nội dung trang tìm kiếm --%>
<div class="grid-container content search">
	<%--Tạo biến lưu kết quả tìm kiếm trả về từ servlet --%>
	<c:set var="search" value="${sessionScope.search}"></c:set>
	<c:set var="text" value="${sessionScope.text}"></c:set>
	<%--Tạo biến lưu thông tin trang hiện tại --%>
	<c:set var="page" value="${param.page == null ? 1 :  param.page}"></c:set>
	<%--Tạo biến lưu số lượng sản phẩm xuất hiện tối đa mỗi trang --%>
	<c:set var="pagesize" value="10"></c:set>
	<c:choose>
		<%--Nếu kết quả trả về là dãy không có giá trị thì in thông báo --%>
		<c:when test="${fn:length(search) == 0}">
			<p>No products were found.</p>
		</c:when>
		<%--Nếu kết quả trả về là dãy có giá trị thì in thông tin tìm kiếm được --%>
		<c:otherwise>
			<div class="grid-item main">
			<sql:transaction dataSource="jdbc/shoppingdb">
				<%--Lấy data tất cả sản phẩm --%>
				<sql:query var="rs" sql="select * from products"></sql:query>
				<%--Lặp qua tất cả các sản phẩm --%>
				<c:forEach items="${search}" var="id" varStatus="v">
					<c:forEach items="${rs.rows}" var="row" varStatus="vs">
						<c:if test="${row.product_id == id}">
							<%--Hiển thị sản phẩm theo số trang hiện tại--%>
							<c:if test="${(v.index >= (page - 1) * pagesize) && (v.index <= (page * pagesize - 1))}">
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
						</c:if>
					</c:forEach>
				</c:forEach>	
			</sql:transaction>
			</div>
		</c:otherwise>
	</c:choose>
	
	<div class="grid-item side">
	<c:set var="fm" value="${sessionScope.filterMap}"></c:set>
		<div class="filter">
			<h1><i class='fas fa-filter'></i><span>Filter</span></h1>
			<form action="<c:url value='/Controller'></c:url>">
				<input type="hidden" name="url" value="<%= request.getServletPath()%>">
				<input type="hidden" name="action" value="search">
				<input type="hidden" name="type" value="1">
				<div class="price filter_child">
					<p class="title">Price</p>
					<div class="content">
						<label>Under 2 million<input type="checkbox" name="p0" value="0" 
						<c:if test="${fm.price['p0'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<label>2 - 4 million<input type="checkbox" name="p1" value="1" 
						<c:if test="${fm.price['p1'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<label>4 - 7 million<input type="checkbox" name="p2" value="2"
						<c:if test="${fm.price['p2'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<label>7 - 13 million<input type="checkbox" name="p3" value="3"
						<c:if test="${fm.price['p3'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<label>13 - 20 million<input type="checkbox" name="p4" value="4"
						<c:if test="${fm.price['p4'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<label>Over 20 million<input type="checkbox" name="p5" value="5"
						<c:if test="${fm.price['p5'] != null}">checked</c:if>><span class="checkmark"></span></label>
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
						<label>Apple<input type="checkbox" name="b0" value="apple"
						<c:if test="${fm.brand['b0'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<label>Samsung<input type="checkbox" name="b1" value="samsung"
						<c:if test="${fm.brand['b1'] != null}">checked</c:if>><span class="checkmark"></span></label>
						<p><button type="submit">See result</button></p>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<%--Kiểm tra nếu số lượng kết quả trả về lớn hơn pagesize thì tạo pagination --%>
	<c:if test="${fn:length(search) > pagesize}">
		<div class="pagination">
			<%--Thay đổi biến lưu trang khi ấn nút chuyển tới trang trước --%>
			<a href="<c:url value="/search?page=${page == 1 ? 1 : page - 1}&text=${param.text}"></c:url>" class="prev">&laquo;</a>
				<%--Tính tổng số trang theo số lượng sản phẩm  --%>
				<c:choose>
					<c:when test="${fn:length(search) % pagesize == 0}">
						<c:set var="total" value="${fn:substringBefore(fn:length(search) / pagesize, '.')}"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="total" value="${fn:substringBefore(fn:length(search) / pagesize, '.') + 1}"></c:set>
					</c:otherwise>
				</c:choose>
				<%--Hiển thị danh sách trang--%>
				<c:forEach begin="1" end="${total}" step="1" varStatus="v">
					<c:choose>
						<c:when test="${page == v.index}">
							<a href="<c:url value="/search?page=${v.index}&text=${param.text}"></c:url>" class="page active">${v.index}</a>
						</c:when>
						<c:otherwise>
							<a href="<c:url value="/search?page=${v.index}&text=${param.text}"></c:url>" class="page">${v.index}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			<%--Thay đổi biến lưu trang khi ấn nút chuyển tới trang tiếp theo --%>
		  	<a href="<c:url value="/search?page=${page == total ? total : page + 1}&text=${param.text}"></c:url>" class="next">&raquo;</a>
		</div>
	</c:if>
	<script type="text/javascript" src='<c:url value="/js/home.js"></c:url>'></script>
</div>

<%-- Chèn file footer.jsp vào trang chủ --%>
<c:import url="footer.jsp"></c:import>

</body>
</html>