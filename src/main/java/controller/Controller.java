package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.User;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
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
    
    public void init(ServletConfig config) throws ServletException {
    	//Khởi tạo kết nối tới Database 
		try {
			InitialContext initContext = new InitialContext();
			Context env = (Context) initContext.lookup("java:comp/env");
			ds = (DataSource)env.lookup("jdbc/shoppingdb");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		try {
			if (action.equals("logout")) {
				//Khi người dùng chọn đăng xuất chuyển yêu cầu qua LogoutServlet 
				request.getRequestDispatcher("LogoutServlet").forward(request, response);
			} else if (action.equals("closelogin")) {
				//Khi người dùng chọn thoát trình đăng nhập
				response.sendRedirect("home");
			}
		} catch (NullPointerException e) {
			response.sendRedirect("index");
		} 
		catch (Exception e) {
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
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		//Kết nối tới database
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.print("Can't connect to database");
			return;
		}
		request.setAttribute("con", con);
		
		try {
			if (action.equals("login")) {
				//Khi người dùng chọn đăng nhập chuyển yêu cầu qua LoginServlet 
				request.getRequestDispatcher("LoginServlet").forward(request, response);
			}
		} catch (NullPointerException e) {
			response.sendRedirect("index");
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}

}
