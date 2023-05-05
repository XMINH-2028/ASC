package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession session = request.getSession();
			//Lấy dữ liệu từ form login
			String action = request. getParameter("action");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String mailalert = "";
			String passalert = "";
			if (action != null) {
				//Regex kiểm tra password và email
				String regex="[a-z0-9_-]{6,12}+";
				String regexmail="^[\\w_]+@[\\w\\.]+\\.[A-Za-z]{2,6}$";
				//Lấy dữ liệu lưu trong file web.xml
				String mail = request.getServletContext().getInitParameter("mail");
				String pass = request.getServletContext().getInitParameter("pass");
				//Kiểm tra dữ liệu nhận từ form và trả về thông báo nếu có lỗi
				if (email.equals("")) {
					mailalert = "please input email";
					response.sendRedirect("login?email="+email+"&password="+password+"&mailalert="+mailalert);				
				} else if (!email.matches(regexmail)) {
					mailalert = "invalid syntax";
					response.sendRedirect("login?email="+email+"&password="+password+"&mailalert="+mailalert);	
				} else if (password.equals("")) {
					passalert = "please input password";
					response.sendRedirect("login?email="+email+"&password="+password+"&passalert="+passalert);	
				} else if (!password.matches(regex)) {
					passalert = "invalid syntax";
					response.sendRedirect("login?email="+email+"&password="+password+"&passalert="+passalert);	
				} else {
					//Kiểm tra thông tin nhận từ form login có khớp với dữ liệu đã lưu hay không
					if (email.equalsIgnoreCase(mail) && password.equals(pass)) {
						//Nếu đúng thì lưu email vào session và cookie nếu người dùng chọn remember
						session.setAttribute("mail", email);
						String remember = request.getParameter("remember");
						if (remember != null) {
							Cookie mailCookie = new Cookie("email",email);
							Cookie passCookie = new Cookie("password",password);
							mailCookie.setMaxAge(60 * 60 * 24);
							passCookie.setMaxAge(60 * 60 * 24);
							response.addCookie(mailCookie);
					        response.addCookie(passCookie);
						}
						response.sendRedirect("admin/index");
					} else {
						//Nếu sai thì thông báo lỗi
						passalert = "email or password is invalid";
						mailalert = "email or password is invalid";
						response.sendRedirect("login?email="+email+"&password="+password+"&passalert="+passalert+"&mailalert="+mailalert);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
