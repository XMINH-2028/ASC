package test;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import database.SendEmail;

/**
 * Servlet implementation class Set
 */
public class Set extends HttpServlet {
	private static final long serialVersionUID = 1L;
     static int a = 0;
     private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Set() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		/*
	 	InitialContext initContext = new InitialContext();
		Context env = (Context) initContext.lookup("java:comp/env");
		ds = (DataSource)env.lookup("jdbc/shoppingdb");
		Kết nối tới database
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.print("Can't connect to database");
			return;
		}
		 
		 
		//Request scope
		a++;
		
		out.print(a);
		out.print("<p>I am </p>");
		request.setAttribute("name", "Minh");
		request.getRequestDispatcher("Get").include(request, response);
		
		//Session scope
		HttpSession session = request.getSession();
		session.setAttribute("name", "Nam");
		String name = (String)session.getAttribute("name");
		out.print("\n");
		out.print(name);
		
		//App scope
		ServletContext context = getServletContext();
		Integer hits = (Integer)context.getAttribute("hits");
		if (hits == null) {
			hits = 0;
		} else {
			hits++;
		}
		context.setAttribute("hits", hits);
		out.print("\n");
		out.print(hits);
		
		
		SendEmail.send("minhnguyenx28@gmail.com", "Verify code", "1234");
		out.print("success");
		*/
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("home?abc=5");
	}

}
