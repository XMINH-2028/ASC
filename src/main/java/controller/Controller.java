package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import database.Account;
import database.Function;
import database.SendEmail;
import database.ShoppingCart;
import database.Validate;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    public void init() throws ServletException {
    	ServletContext context = getServletContext();
		Function ft = new Function();
		context.setAttribute("ft", ft);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		try {
			if (action.equals("login")) {
				//Khi người dùng chọn đăng nhập chuyển qua trang login
				response.sendRedirect(response.encodeRedirectURL("login"));
			} else if (action.equals("register")) {
				//Xóa session lưu thông tin  khi người dùng quên mật khẩu
				session.removeAttribute("forget");
				//Khi người dùng chọn đăng kí chuyển qua trang register
				response.sendRedirect(response.encodeRedirectURL("register"));	
			} else if (action.equals("logout")) {
				//Khi người dùng chọn đăng xuất chuyển yêu cầu qua LogoutServlet 
				request.getRequestDispatcher("LogoutServlet").forward(request, response);
			} else if (action.equals("closeform")) {
				//Xóa session lưu thông tin khi kiểm tra thông tin đăng nhập, khi người dùng quên mật khẩu, khi đăng kí
				session.removeAttribute("login");
				session.removeAttribute("forget");
				session.removeAttribute("register");
				//Khi người dùng chọn thoát trình đăng nhập, đăng kí
				response.sendRedirect(response.encodeRedirectURL(session.getAttribute("currentPage").toString()));
			} else if (action.equals("loginreset")) {
				//Xóa session lưu thông tin khi kiểm tra thông tin đăng nhập
				session.removeAttribute("login");
				response.sendRedirect(response.encodeRedirectURL("login"));
			} else if (action.equals("registerreset")) {
				//Xóa session lưu thông tin khi kiểm tra thông tin đăng nhập
				session.removeAttribute("register");
				response.sendRedirect(response.encodeRedirectURL("register"));
			} else if (action.equals("forget")) {
				//Xóa session lưu thông tin  khi người dùng đăng kí
				session.removeAttribute("register");
				//Khi người dùng quên mật khẩu đăng nhập tạo session forget với action = getcode và chuyển tới trang lấy mã xác thực
				Validate v = new Validate("getcode");
				session.setAttribute("forget",v);
				response.sendRedirect(response.encodeRedirectURL("getcode"));
			} else if (action.equals("search") || action.equals("filter")) {
				//Khi người dùng submit nội dung tìm kiếm
				request.getRequestDispatcher("SearchServlet").forward(request, response);
			} else if (action.equals("product")) {
				//Khi người dùng click vào navbar hoặc sản phẩm
				session.setAttribute("currentPage", "product");
				response.sendRedirect(response.encodeRedirectURL("product" + 
				(request.getParameter("id") == null ? "" : "?id=" + request.getParameter("id"))));
			} else if (action.equals("home")) {
				//Khi người dùng click vào navlink
				session.setAttribute("currentPage", "home");
				response.sendRedirect(response.encodeRedirectURL("home"));
			} else if (action.equals("addcart") || action.equals("changecart") || action.equals("deletecart")
				|| action.equals("checked") || action.equals("cart") || action.equals("buynow")) {
				request.getRequestDispatcher("CartServlet").forward(request, response);
			} else if (action.equals("pay") || action.equals("address")) {
				request.getRequestDispatcher("PayServlet").forward(request, response);
			} else if (action.equals("order")) {
				request.getRequestDispatcher("OrderServlet").forward(request, response);
			} 
		} catch (NullPointerException e) {
			session.setAttribute("currentPage", "home");
			response.sendRedirect(response.encodeRedirectURL("home"));
		} catch (Exception e) {
			// TODO: handle exception
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		
		try {
			if (action.equals("dologin")) {
				//Khi người dùng submit form đăng nhập chuyển yêu cầu qua LoginServlet 
				request.getRequestDispatcher("LoginServlet").forward(request, response);
			} else if (action.equals("doregister")) {
				//Khi người dùng submit form tạo session register với action = register và chuyển tới RegisterServlet
				Validate v = new Validate("register");
				session.setAttribute("register",v); 
				request.getRequestDispatcher("RegisterServlet").forward(request, response);
			} else if ("getcodeforgetverifyreset".contains(action)) {
				//Khi người dùng quên mật khẩu cần đặt lại chuyển qua ResetServlet
				request.getRequestDispatcher("ResetServlet").forward(request, response);
			} else if (action.equals("registerverify")) {
				//Khi người dùng xác thực email đăng kí chuyển qua RegisterServlet
				request.getRequestDispatcher("RegisterServlet").forward(request, response);
			}
		} catch (NullPointerException e) {
			out.print(e);
		} 
		catch (Exception e) {
			// TODO: handle exception
			out.print(e);
		}
	}

}
