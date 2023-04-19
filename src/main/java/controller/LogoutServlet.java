package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
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
			Cookie ck[] = request.getCookies();
			String user = getServletContext().getInitParameter("user");
			String pass = getServletContext().getInitParameter("pass");
			if(ck!=null){  
				for (int i = 0; i < ck.length-1; i++) {
					if (ck[i].getValue().equalsIgnoreCase(user) && ck[i+1].getValue().equalsIgnoreCase(pass)) {
						ck[i].setMaxAge(0);
						response.addCookie(ck[i]);
						ck[i+1].setMaxAge(0);
						response.addCookie(ck[i+1]);
					}
				}
	      	} 
			response.sendRedirect("setuserbean.jsp?start=5");
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
