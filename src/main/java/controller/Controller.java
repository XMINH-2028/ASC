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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		int count = 0;
		try {
			Cookie ck[] = request.getCookies();
			String user = getServletContext().getInitParameter("user");
			String pass = getServletContext().getInitParameter("pass");
			if(ck!=null & LoginServlet.login.equals("")){  
				for (Cookie x : ck) {
					//response.getWriter().println(x.getName() + ": " + x.getValue());
					if (x.getValue().equalsIgnoreCase(user) || x.getValue().equalsIgnoreCase(pass)) {
						count += 1;
					}
				}
		        if(count == 2){ 
		        	LoginServlet.login = user;
		        } else {
		        	LoginServlet.login = "";   
		        }
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
