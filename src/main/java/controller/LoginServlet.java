package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String user = getServletContext().getInitParameter("user");
			String pass = getServletContext().getInitParameter("pass");
			if (username.equalsIgnoreCase(user) && password.equals(pass)) {
				String remember = request.getParameter("remember");
				if (remember != null) {
					Cookie userCookie = new Cookie("username",username);
					Cookie passCookie = new Cookie("password",password);
					userCookie.setMaxAge(60 * 60 * 24);
					passCookie.setMaxAge(60 * 60 * 24);
					response.addCookie(userCookie);
			        response.addCookie(passCookie);
				}
		        response.sendRedirect("setuserbean.jsp?username="+ user + "&password=" + password);
			} else {
				response.sendRedirect("home.jsp");
			}
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
		doGet(request, response);
	}

}
