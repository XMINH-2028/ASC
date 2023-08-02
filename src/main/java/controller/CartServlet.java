package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import database.Function;
import database.Order;
import database.Product;
import database.ShoppingCart;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Function ft = new Function();
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		
		try {
			int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
			int quantity = request.getParameter("quantity") == null ? 0 : Integer.parseInt(request.getParameter("quantity"));
			ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
			if (action.equals("addcart")) {
				//Khi người dùng thêm sản phẩm vào giỏ hàng
				cart.addCart(id, quantity);
				session.setAttribute("cart", cart);
				out.print(cart.getProductList().size());
			} else if (action.equals("changecart")) {
				//Khi người dùng thay đổi số lượng sản phẩm trong giỏ hàng
				cart.changeCart(id, quantity);
				session.setAttribute("cart", cart);
			} else if (action.equals("deletecart")) {
				//Khi người dùng xoá sản phẩm trong giỏ hàng
				cart.deleteCart(id);
				session.setAttribute("cart", cart);
			}  else if (action.equals("checked")) {
				//Khi người dùng chọn sản phẩm trong giỏ hàng
				cart.checked(id,quantity);
				session.setAttribute("cart", cart);
			} else if (action.equals("cart")) {
				String page = request.getParameter("page");
				if (page.equals("ordered")) {
					cart.creatOrderList();
					String text = "";
					for (Order or : cart.getOrderList()) {
						text += "<h3>Order No: " + or.getOrderId() + "<span>(" + or.getOrderDate() + ")</h3>";
						text += "<h4>Total: " + ft.vnd(or.totalPay() * 1000000) + "đ</h4>";
						for (int i = 0; i < or.getProductId().size(); i++) {
							Product pr = new Product();
							pr = pr.getProduct(or.getProductId().get(i), 0);
							text += "<div class='cart_product'>"
								+ "<p><img src='"+ pr.getImg() +"' class='link'></p>"
								+ "<div class='product_info'>"
									+ "<p class='name'>" + pr.getName() + "</p>"
									+ "<p class='price'>" + ft.vnd(pr.getPrice() * 1000000) + "đ</p>"
								+ "</div>"
								+ "<div class='quantity'>"
									+ "<p>Quantity: " + or.getAmountProduct().get(i) +"</p>"
								+ "</div>"
							+ "</div>";
						}
					}
					
					out.print(text);
				} else {
					cart.getCart();
					cart.setPage("selected");
					cart.setCheck(true);
					session.setAttribute("cart", cart);
					response.sendRedirect(response.encodeRedirectURL("cart"));
				}
			} else if (action.equals("buynow")) {
				cart.addCart(id, 1);
				cart.getCart();
				cart.setPage("selected");
				cart.setCheck(true);
				session.setAttribute("cart", cart);
				response.sendRedirect(response.encodeRedirectURL("cart"));
			}
		} catch (Exception e) {
			response.sendRedirect(response.encodeRedirectURL("login"));
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
