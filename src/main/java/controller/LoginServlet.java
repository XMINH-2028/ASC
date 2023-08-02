package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import database.Account;
import database.ShoppingCart;
import database.Validate;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(response.encodeRedirectURL("home"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			HttpSession session = request.getSession();
			//Lấy dữ liệu từ form login
			String action = request. getParameter("action");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Validate v = new Validate();
			Account account = new Account();
			
			if (!v.vlogin(email, password)) {
				//Kiểm tra email hoặc password không hợp lệ thì chuyển hướng về trang login và thông báo lỗi
				session.setAttribute("login", v);
				response.sendRedirect(response.encodeRedirectURL("login"));
			} else {
				//Nếu email và password hợp lệ thì kiểm tra xem có khớp với dữ liệu đã lưu hay không
				account.getUser(email, password);
				if (account.getRole() != 0) {
					//Xóa session lưu thông tin khi kiểm tra thông tin đăng nhập
					session.removeAttribute("login");
					//Nếu đúng thì lưu thông tin vào session và cookie nếu người dùng chọn remember
					session.setAttribute("user", account);
					ShoppingCart cart = new ShoppingCart(account.getEmail());
					cart.getCart();
					session.setAttribute("cart", cart);
					String remember = request.getParameter("remember");
					if (remember != null) {
						Cookie mailCookie = new Cookie("email",email);
						mailCookie.setMaxAge(60 * 60 * 24);
						response.addCookie(mailCookie);
					}
					if (account.getRole() == 1) {
						//Nếu người dùng là quản trị viên thì chuyển qua trang admin
						response.sendRedirect(response.encodeRedirectURL("manager/admin"));
					} else {
						//Nếu người dùng không là quản trị viên thì chuyển qua trang home
						response.sendRedirect(response.encodeRedirectURL(session.getAttribute("currentPage").toString()));
					}
				} else {
					//Nếu sai thì chuyển hướng về trang login và thông báo lỗi
					v.setCheckInfo("email or password is invalid");
					session.setAttribute("login", v);
					response.sendRedirect(response.encodeRedirectURL("login"));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.sendRedirect(response.encodeRedirectURL("home"));
		}
	}

}
