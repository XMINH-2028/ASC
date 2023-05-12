<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%--
	Chèn file header.jsp vào trang chủ
	 <%@ include file="header.jsp"%>
	<jsp:include page="header.jsp"></jsp:include>
	--%>
	
	<c:import url="header.jsp">
		<c:param name="title" value="Home"></c:param>
		<c:param name="style" value="home.css"></c:param>
	</c:import>
	
	<%-- Nội dung trang chủ --%>
	<div class="grid-container content">
		<div class="grid-item main">
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
			<div class="home_wrap">
				<img alt="iphone14"
					src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
				<div class="info">
					<p class="name">iPhone 14 Pro Max 128GB</p>
					<p class="price">
						10.000₫<span class="discount">-50%</span>
					</p>
					<p class="more">
						5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
					</p>
				</div>
				<button>Add to Cart</button>
			</div>
		</div>
		<div class="grid-item side">
			<div class="shopping">
				<h1><a href="#"><i class='fas fa-cart-plus cart'></i><span>Shopping Cart</span></a></h1>
				<hr>
				<div class="summary">
					No product
				</div>
			</div>
			<div class="popular">
				<h1>Popular products</h1>
				<hr>
				<ul>
					<li><span><i class='fas fa-angle-right control'></i> Samsung</span><li>
					<li class="home_wrap">
						<img alt="iphone14" src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
						<div class="info">
							<p class="name">iPhone 14 Pro Max 128GB</p>
							<p class="price">
								10.000₫<span class="discount">-50%</span>
							</p>
							<p class="more">
								5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
							</p>
						</div>
						<button>Add to Cart</button>
					</li>
					<li class="home_wrap">
						<img alt="iphone14" src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
						<div class="info">
							<p class="name">iPhone 14 Pro Max 128GB</p>
							<p class="price">
								10.000₫<span class="discount">-50%</span>
							</p>
							<p class="more">
								5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
							</p>
						</div>
						<button>Add to Cart</button>
					</li>
				</ul>
				<ul>
					<li><span><i class='fas fa-angle-right control'></i> Iphone</span></li>
					<li class="home_wrap">
						<img alt="iphone14" src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
						<div class="info">
							<p class="name">iPhone 14 Pro Max 128GB</p>
							<p class="price">
								10.000₫<span class="discount">-50%</span>
							</p>
							<p class="more">
								5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
							</p>
						</div>
						<button>Add to Cart</button>
					</li>
					<li class="home_wrap">
						<img alt="iphone14" src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
						<div class="info">
							<p class="name">iPhone 14 Pro Max 128GB</p>
							<p class="price">
								10.000₫<span class="discount">-50%</span>
							</p>
							<p class="more">
								5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
							</p>
						</div>
						<button>Add to Cart</button>
					</li>
					<li class="home_wrap">
						<img alt="iphone14" src="images/iphone-14-pro-max-den-thumb-600x600.jpg">
						<div class="info">
							<p class="name">iPhone 14 Pro Max 128GB</p>
							<p class="price">
								10.000₫<span class="discount">-50%</span>
							</p>
							<p class="more">
								5 <i class='fas fa-star star'></i><span class="sold">(1000)</span>
							</p>
						</div>
						<button>Add to Cart</button>
					</li>
				</ul>
				
			</div>
		</div>
		<div class="pagination">
			<a href="#">&laquo;</a>
		  	<a href="#">1</a>
		  	<a href="#" class="active">2</a>
		  	<a href="#">3</a>
		  	<a href="#">4</a>
		  	<a href="#">5</a>
		  	<a href="#">6</a>
		  	<a href="#">&raquo;</a>
		</div>
		<script type="text/javascript" src="js/home.js"></script>
	</div>
	
	<%-- Chèn file footer.jsp vào trang chủ --%>
	
	<c:import url="footer.jsp"></c:import>
	
	
</body>
</html>