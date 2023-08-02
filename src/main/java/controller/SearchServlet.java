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
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import database.Function;
import database.Product;
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
		Function ft = new Function();
		
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		
		//Biến lưu kết quả tìm kiếm
		List<Product> listSearch = new ArrayList<>(); 
		
		//Instance class Search chứa các hàm tìm kiếm, lọc thông tin
		Search search = new Search();
		
		Product pr = new Product();
		
		try {
			if (action.equals("search")) {
				//Lấy thông tin người dùng tìm kiếm từ form
				String text = request.getParameter("text");
				List<Integer> list = search.userSearch(text);
				for (int i = 0; i < list.size(); i++) {
					listSearch.add(pr.getProduct(list.get(i),0));
				}
				//Lưu kết quả tìm kiếm
				session.setAttribute("search", listSearch);
				//Lưu nội dung tìm kiếm vào session
				session.setAttribute("text", text);
				//Xóa tất các các nội dung filter
				session.removeAttribute("filterMap");
				//Chuyển tới trang hiển thị kết quả tìm kiếm
				session.setAttribute("currentPage", "search");
				response.sendRedirect(response.encodeRedirectURL("search"));
			} else if (action.equals("filter")) {
				//Biến lưu các nội dung filter
				HashMap<String, HashMap<String, String>> filterMap = new HashMap<>();
				filterMap.put("price", new HashMap<>());
				filterMap.put("brand", new HashMap<>());
				
				//List lưu các kết kết quả lọc và tìm kiếm
				List<List<Integer>> list = new ArrayList<>();
				
				//Biến chứa tên tất cả các tham số từ form
				Enumeration paramNames = request.getParameterNames();
				
				//List lưu tên tham số
				List<String> key = new ArrayList<>();
				
				//List lưu giá trị tham số
				List<String> value = new ArrayList<>();
				
				//Lấy tên tất cả các tham số từ form
				while (paramNames.hasMoreElements()) {
					key.add((String) paramNames.nextElement());
				}
				
				//Lấy giá trị tất cả các tham số từ form
				for (String j : key) {
					value.add(request.getParameterValues(j)[0]);
				}
				
				//Biến đếm số mục tìm kiếm(search, filter price, filter branch)
				int count = 0;
				
				//Lấy thông tin người dùng tìm kiếm từ form
				String text = (String) session.getAttribute("text");
				
				if (text != null && !text.trim().equals("")) {
					count += 1;
					//Lưu kết quả tìm kiếm
					list.add(search.userSearch(text));
				}
				
				//Tạo bản sao key và value
				List<String> copyKey = new ArrayList<>();
				List<String> copyValue = new ArrayList<>();
				for (int i = 0; i < key.size(); i++) {
					if (key.get(i).contains("type")) {
						count++;
						//Lọc theo giá
						if (value.get(i).equals("1")) {
							copyKey = ft.dCopy(key);
							copyValue = ft.dCopy(value);
							//Lưu kết quả lọc
							list.add(search.priceFilter(copyKey, copyValue));
							//Lưu các mục filter
							for (int j = 0; j < copyKey.size(); j++) {
								filterMap.get("price").put(copyKey.get(j), copyValue.get(j));
							}
						} 
						//Lọc theo thương hiệu
						if (value.get(i).equals("2")) {
							copyKey = ft.dCopy(key);
							copyValue = ft.dCopy(value);
							//Lưu kết quả lọc
							list.add(search.brandFilter(copyKey, copyValue));
							//Lưu các mục filter
							for (int j = 0; j < copyKey.size(); j++) {
								filterMap.get("brand").put(copyKey.get(j), value.get(j));
							}
						}
						
					} 
				}
				
				
				if (count == 0) {
					//Nếu không có nội dung tìm kiếm và không lọc sản phẩm
					response.sendRedirect(response.encodeRedirectURL("home"));
				} else {
					//Dãy lưu kết quả lọc và tìm kiếm
					List<Integer>[] arr = new List[list.size()];
					
					for (int i = 0; i < list.size(); i++) {
						arr[i] = list.get(i);
					}
					
					ft.mergeList(arr);
					
					for (int i = 0; i < arr[0].size(); i++) {
						listSearch.add(pr.getProduct(arr[0].get(i),0));
					}
					
					//Nếu có nội dung tìm kiếm hoặc lọc sản phẩm
					session.setAttribute("search", listSearch);
					session.setAttribute("filterMap",filterMap);
					session.setAttribute("currentPage", "search");
					response.sendRedirect(response.encodeRedirectURL("search"));
				}
			} else {
				response.sendRedirect(response.encodeRedirectURL("home"));
			}
		} catch (Exception e) {
			response.sendRedirect(response.encodeRedirectURL("home"));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
