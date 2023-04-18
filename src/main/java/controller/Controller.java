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
	
		try {
			String action = request. getParameter("action");
			if (action == null) {
				Cookie ck[] = request.getCookies();
				String user = getServletContext().getInitParameter("user");
				String pass = getServletContext().getInitParameter("pass");
				String logout = (String) getServletContext().getAttribute( "logout" );
				String login = request.getParameter("login");
				if (login != null && login.equals("on")) {
					response.sendRedirect("home");
				}
				if (logout != null && logout.equals("on")) {
					response.sendRedirect("setuserbean.jsp?username=&password=");
				}
				if(ck!=null){  
					for (int i = 0; i < ck.length-1; i++) {
						if (ck[i].getValue().equalsIgnoreCase(user) & ck[i+1].getValue().equalsIgnoreCase(pass)) {
							request.getRequestDispatcher("LoginServlet?username=" + user + "&password=" + pass).forward(request, response);
						}
					}
		      	} else {
		      		response.sendRedirect("setuserbean.jsp");
		      	}
			} else if (action.equals("login")) {
				response.sendRedirect("login?username=&password=&usermess=&passmess=");
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
		String action = request. getParameter("action");
		if (action == null) {
			response.sendRedirect("setuserbean.jsp?username=&password=");
		} else if (action.equals("submit")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String user = getServletContext().getInitParameter("user");
			String pass = getServletContext().getInitParameter("pass");
			User admin = new User(username,password);
			if (admin.validate(user, pass)) {
				request.getRequestDispatcher("LoginServlet").forward(request, response);
			} else {
				response.sendRedirect("login?username="+username+"&password="+password+"&usermess="+admin.getUsermess()+
						"&passmess="+admin.getPassmess());
			}
		}
	}

}
