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

/**
 * Servlet implementation class Set
 */
public class Set extends HttpServlet {
	private static final long serialVersionUID = 1L;
     static int a = 0;
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
		/*
		//Request scope
		a++;
		PrintWriter out = response.getWriter();
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
		
		*/
		request.getRequestDispatcher("get.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
