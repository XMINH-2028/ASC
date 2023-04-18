package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Cookie ck[] = request.getCookies();
			String user = getServletContext().getInitParameter("user");
			String pass = getServletContext().getInitParameter("pass");
			String login =  request.getParameter("login");
			String logout =  request.getParameter("logout");
			if (login != null && login.equals("on")) {
				response.sendRedirect("home?user=" + user);
			}
			if (logout != null && logout.equals("on")) {
				response.sendRedirect("home");
			}
			if(ck!=null){  
				for (int i = 0; i < ck.length - 1; i++) {
					if (ck[i].getValue().equalsIgnoreCase(user) && ck[i+1].getValue().equals(pass)) {
						response.sendRedirect("home?user=" + user);
					}
				}
				response.sendRedirect("home");
	      	} 
			response.sendRedirect("home");
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().print(e);
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
