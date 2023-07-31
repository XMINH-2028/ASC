<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css; text/javascript" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${sessionScope.currentPage == null || sessionScope.currentPage != 'search'}">
	<c:redirect url="/Controller?action=home"></c:redirect>
</c:if>

<%--Tạo biến lưu kết quả tìm kiếm trả về từ servlet --%>
	<c:set var="search" value="${sessionScope.search}"></c:set>
	<c:set var="text" value="${sessionScope.text}"></c:set>

<%-- Chèn header với tham số title và đường dẫn css--%>
<c:import url="header.jsp">
	<c:param name="title" value="Search"></c:param>
	<c:param name="style" value="home.css"></c:param>
</c:import>

<%-- Nội dung trang tìm kiếm --%>
<div class="grid-container content search">
	<c:set var="ft" value="${applicationScope.ft}"></c:set>
	<%--Tạo biến lưu thông tin trang hiện tại --%>
	<c:choose>
		<c:when test="${param.page == null || !ft.checkInt(param.page)}">
			<c:set var="page" value="1"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="page" value="${param.page}"></c:set>
		</c:otherwise>
	</c:choose>
	<%--Tạo biến lưu số lượng sản phẩm xuất hiện tối đa mỗi trang --%>
	<c:set var="pagesize" value="10"></c:set>
	<c:choose>
		<%--Nếu kết quả trả về là null thì chuyển qua trang chủ --%>
		<c:when test="${search == null}">
			<c:redirect url="/home"></c:redirect>
		</c:when>
		<%--Nếu kết quả trả về là dãy không có giá trị thì in thông báo --%>
		<c:when test="${fn:length(search) == 0}">
			<p>No products were found.</p>
		</c:when>
		<%--Nếu kết quả trả về là dãy có giá trị thì in thông tin tìm kiếm được --%>
		<c:otherwise>
			<div class="grid-item main">
				<%--Tính tổng số trang theo số lượng sản phẩm  --%>
				<c:choose>
					<c:when test="${fn:length(search) % pagesize == 0}">
						<c:set var="total" value="${fn:substringBefore(fn:length(search) / pagesize, '.')}"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="total" value="${fn:substringBefore(fn:length(search) / pagesize, '.') + 1}"></c:set>
					</c:otherwise>
				</c:choose>
				<c:if test="${page > total}">
					<c:set var="page" value="${total}"></c:set>
				</c:if>
				<%--Lặp qua tất cả các sản phẩm --%>
				<c:forEach items="${search}" var="list" varStatus="v">
					<%--Hiển thị sản phẩm theo số trang hiện tại--%>
					<c:if test="${(v.index >= (page - 1) * pagesize) && (v.index <= (page * pagesize - 1))}">
						<%--Hiển thị thông tin sản phẩm --%>
						<div class="home_wrap">
							<div class="content">
								<img alt="${list.name}"
								src="${list.img}">
								<div class="info">
									<p class="name">${list.name}</p>
									<p class="price">
										${ft.vnd(list.price * 1000000)}đ
									</p>
									<p class="more">
										5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
									</p>
								</div>
								<a href='<c:url value="/Controller?action=product&id=${list.id}"></c:url>' class="productinfo"></a>
							</div>
							<div class="buy_action">
								<c:choose>
									<c:when test="${sessionScope.user == null }">
										<a href='<c:url value="login"></c:url>'>Buy now</a>
										<a href='<c:url value="login"></c:url>'>Add to Cart</a>
									</c:when>
									<c:otherwise>
										<a class="buynow" href='<c:url value="/Controller?action=buynow&id=${list.id}"></c:url>'>Buy now</a>
										<span class="addcart" data-id="${list.id}" data-price="${ft.vnd(list.price * 1000000)}"
										data-img="${list.img}" data-name="${list.name}" data-url="<%=request.getServletPath()%>">Add to Cart</span>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</c:if>
				</c:forEach>	
			</div>
		</c:otherwise>
	</c:choose>
	
	<div class="grid-item side">
	<%--Lấy session lưu các nội dung cần lọc đã lựa chọn --%>
	<c:set var="fm" value="${sessionScope.filterMap}"></c:set>
		<div class="filter">
			<h1><i class='fas fa-filter'><span></span></i><span>Filter</span></h1>
			<form action="<c:url value='/Controller'></c:url>">
				<input type="hidden" name="hiddenText" class="hiddenText">
				<input type="hidden" name="action" value="filter">
				<%--Nếu nội dung đã lọc thêm thuộc tính checked --%>
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
	
	<%--Kiểm tra nếu số lượng trang lớn hơn 1 thì tạo pagination --%>
	<c:if test="${total > 1}">
		<div class="pagination">
			<%--Thay đổi biến lưu trang khi ấn nút chuyển tới trang trước --%>
			<a href="<c:url value="/search?page=${page == 1 ? 1 : page - 1}"></c:url>" class="prev">&laquo;</a>
				<%--Hiển thị danh sách trang--%>
				<c:forEach begin="1" end="${total}" step="1" varStatus="v">
					<c:choose>
						<c:when test="${page == v.index}">
							<a href="<c:url value="/search?page=${v.index}"></c:url>" class="page active">${v.index}</a>
						</c:when>
						<c:otherwise>
							<a href="<c:url value="/search?page=${v.index}"></c:url>" class="page">${v.index}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			<%--Thay đổi biến lưu trang khi ấn nút chuyển tới trang tiếp theo --%>
		  	<a href="<c:url value="/search?page=${page == total ? total : page + 1}"></c:url>" class="next">&raquo;</a>
		</div>
	</c:if>
	<script type="text/javascript" src='<c:url value="/js/home.js"></c:url>'></script>
</div>

<%-- Chèn file footer.jsp vào trang chủ --%>
<c:import url="footer.jsp"></c:import>
