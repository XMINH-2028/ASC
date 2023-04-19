package controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import bean.User;

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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int count = 0;
		try {
			String action = request.getParameter("action");
			if (action == null) {
				Cookie ck[] = request.getCookies();
				String user = getServletContext().getInitParameter("user");
				String pass = getServletContext().getInitParameter("pass");
				if(ck!=null){  
					for (int i = 0; i < ck.length-1; i++) {
						if (ck[i].getValue().equalsIgnoreCase(user) & ck[i+1].getValue().equals(pass)) {
							count += 1;
							response.sendRedirect("setuserbean.jsp?start=0&username=" + user + "&password=" + pass);
						}
					}
					if (count == 0) {
						response.sendRedirect("setuserbean.jsp?start=1");
					}
					
		      	} else {
		      		response.sendRedirect("setuserbean.jsp?start=2");
		      	}
			} else if (action.equals("login")) {
				response.sendRedirect("login");
			} else if (action.equals("logout")) {
				response.sendRedirect("LogoutServlet");
			}
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
		String action = request.getParameter("action");
		String user = getServletContext().getInitParameter("user");
		String pass = getServletContext().getInitParameter("pass");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (action != null && action.equals("submit")) {
			User admin = new User(username,password);
			if (admin.validate(user, pass)) {
				request.getRequestDispatcher("LoginServlet?username=" + user + "&password=" + pass).forward(request, response);
			} else {
				request.getRequestDispatcher("login?username=" + username + "&password=" + password + 
						"&usermess=" + admin.getUsermess() + "&passmess=" + admin.getPassmess()).forward(request, response);
			}
		}
	}

}
