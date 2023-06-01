package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import database.Search;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		//Lấy thông tin người dùng tìm kiếm
		String text = request.getParameter("text");
		String url = request.getParameter("url");
		HashMap<String, HashMap<String, String>> filterMap = 
				(HashMap<String, HashMap<String, String>>) session.getAttribute("filterMap");
		Search search = new Search();
		try {
			if (text != null) {
				//Xóa session lưu thông tin tìm kiếm trước đó
				session.removeAttribute("search");
				session.removeAttribute("text");
				//Tìm kiếm thông tin và trả về kết quả 
				session.setAttribute("search", search.userSearch(text));
				session.setAttribute("text", text);
				response.sendRedirect(response.encodeRedirectURL("search"));
			} else {
				
				Enumeration paramNames = request.getParameterNames();
				List<String> key = new ArrayList<>();
				List<String> value = new ArrayList<>();
				while (paramNames.hasMoreElements()) {
					key.add((String) paramNames.nextElement());
				}
				for (String j : key) {
					value.add(request.getParameterValues(j)[0]);
				}
				if (filterMap == null) {
					filterMap = new HashMap<>();
				}
				
				for (int i = 0; i < key.size(); i++) {
					if (key.get(i).equals("type")) {
						if (value.get(i).equals("1")) {
							session.setAttribute("search", search.priceFilter(key, value));
							filterMap.put("price", new HashMap<>());
							for (int j = 0; j < key.size(); j++) {
								filterMap.get("price").put(key.get(j), value.get(j));
							}
						} else {
							session.setAttribute("search", search.brandFilter(key, value));
							filterMap.put("brand", new HashMap<>());
							for (int j = 0; j < key.size(); j++) {
								filterMap.get("brand").put(key.get(j), value.get(j));
							}
						}
					}
				}
				session.setAttribute("filterMap", filterMap);
				response.sendRedirect(response.encodeRedirectURL("search"));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
