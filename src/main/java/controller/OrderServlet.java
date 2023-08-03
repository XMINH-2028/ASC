package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.Account;
import database.ConnectDB;
import database.Function;
import database.Order;
import database.Product;
import database.ShoppingCart;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		Account user = (Account) session.getAttribute("user");
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		Order order = new Order();
		
		try {
			//Kiểm tra các điều kiện để order
			if (cart != null && cart.totalCart() > 0) {
				if (name != null && !name.trim().equals("") && Function.checkPhone(phone) && user.getAddress() != null
						&& !user.getAddress().trim().equals("")) {
					//Tạo order trong database nếu tạo thành công sẽ trả về id đơn hàng
					int key = order.creatOrder(user, cart.totalProduct(), "");
					//Nếu tạo thành công sẽ lưu lại các thông tin về user và chi tiết đơn hàng
					if ( key != 0) {
						//Lưu các thông tin bổ sung của user
						user.setUser(name, phone);
						//Lưu chi tiết đơn hàng
						order.creatOrderDetail(cart, key);
			            out.print(true);
			         }
					 else {
						 out.print(false);
					 }
				} 
			} else {
				response.sendRedirect(response.encodeRedirectURL("home"));
			}
		} catch (Exception e) {
			response.sendRedirect(response.encodeRedirectURL("home"));
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
