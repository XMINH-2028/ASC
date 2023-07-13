package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

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
				cart.checked(id);
				session.setAttribute("cart", cart);
			} else if (action.equals("cart")) {
				String page = request.getParameter("page");
				if (page.equals("selected")) {
					cart.getCart();
					cart.setPage("selected");
					cart.setCheck(true);
					session.setAttribute("cart", cart);
					response.sendRedirect(response.encodeRedirectURL("cart"));
				}
			}
		} catch (NullPointerException e) {
			response.sendRedirect(response.encodeRedirectURL("login"));
		} catch (Exception e) {
			// TODO: handle exception
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
