package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.Account;
import database.ConnectDB;
import database.Function;
import database.ShoppingCart;

/**
 * Servlet implementation class PayServlet
 */
public class PayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayServlet() {
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
		//Lấy yêu cầu từ người dùng
		String action = request.getParameter("action");
		Function ft = new Function();
		Account user = (Account) session.getAttribute("user");
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		try {
			if (cart != null && cart.totalCart() > 0) {
				if (action.equals("pay")) {
					//Refresh session user khi tải trang
					user.getUser(user.getEmail(), user.getPassword());
					//Thay đổi biến điều kiện để truy cập trang
					user.setCheckPay(true);
					session.setAttribute("user", user);
					response.sendRedirect(response.encodeRedirectURL("pay"));
				} else if (action.equals("address")) {
					//Khi người dùng cập nhật địa chỉ
					String type = request.getParameter("type");
					String province = "";
					String district = "";
					String wards = "";
					String house = "";
					String[] address = new String[3];
					
					if (type.equals("get")) {
						//Lấy địa chỉ từ database nếu mới tải trang
						province = ft.getId(user.getAddress(), 4);
						district = ft.getId(user.getAddress(), 3);
						wards = ft.getId(user.getAddress(), 2);
						house = ft.getId(user.getAddress(), 1);
					} else if (type.equals("change") || type.equals("save")) {
						//Lấy địa chỉ từ param khi người dùng thay đổi hoặc lưu địa chỉ mới
						province = request.getParameter("province");
						district = request.getParameter("district");
						wards = request.getParameter("wards");
						house = request.getParameter("house");
					}
					
					if (province == null || province.equals("")) {
						province = "0";
					}
					if (district == null || district.equals("")) {
						district = "0";
					}
					if (wards == null || wards.equals("")) {
						wards = "0";
					}
					if (house == null || house.equals("")) {
						house = "";
					}
					Connection con = new ConnectDB().getConnection();
					//Tạo form với dữ liệu lấy từ database với thông tin các tỉnh, quận huyện, phường xã và nhà ở
					//Select province
					String text = "<div class='selectform'>"
									+ "<div class='main'>"
										+ "<div class = 'select'>"
											+ "<p>Select province</p>"
											+ "<select id = 'province'>";
					String sql = "select * from province";
					ResultSet rs = con.prepareStatement(sql).executeQuery();
					int count = 0;
					String save = "";
					while (rs.next()) {
						count ++;
						if (count == 1) {
							save =  String.valueOf(rs.getInt(1));
							address[2] = rs.getString(2);
						}
						if (String.valueOf(rs.getInt(1)).equals(province)) {
							text += "<option value = '" + rs.getInt(1) + "' selected>" + rs.getString(2) + "</option>";
							save = String.valueOf(rs.getInt(1));
							address[2] = rs.getString(2);
						} else {
							text += "<option value = " + rs.getInt(1) + ">" + rs.getString(2) + "</option>";
						}
					}
					province = save;
					text += "</select>"
								+ "</div>";
					
					//Select district
					text += "<div class = 'select'>"
							+ "<p>Select district</p>"
								+ "<select id = 'district'>";
					sql = "select * from district where province_id = ?";
					PreparedStatement stmt  = con.prepareStatement(sql);
					stmt.setInt(1, Integer.parseInt(province));
					rs = stmt.executeQuery();
					count = 0;
					save = "";
					while (rs.next()) {
						count ++;
						if (count == 1) {
							save =  String.valueOf(rs.getInt(1));
							address[1] = rs.getString(3);
						}
						if (String.valueOf(rs.getInt(1)).equals(district)) {
							text += "<option value = '" + rs.getInt(1) + "' selected>" + rs.getString(3) + "</option>";
							save =  String.valueOf(rs.getInt(1));
							address[1] = rs.getString(3);
						} else {
							text += "<option value = " + rs.getInt(1) + ">" + rs.getString(3) + "</option>";
						}
					}
					district = save;
					text += "</select>"
									+ "</div>";		
					
					//Select wards
					text += "<div class = 'select'>"
							+ "<p>Select wards</p>"
								+ "<select id = 'wards'>";
					sql = "select * from wards where district_id = ?";
					stmt  = con.prepareStatement(sql);
					stmt.setInt(1, Integer.parseInt(district));
					rs = stmt.executeQuery();
					count = 0;
					while (rs.next()) {
						count ++;
						if (count == 1) {
							address[0] = rs.getString(3);
						}
						if (String.valueOf(rs.getInt(1)).equals(wards)) {
							text += "<option value = '" + rs.getInt(1) + "' selected>" + rs.getString(3) + "</option>";
							address[0] = rs.getString(3);
						} else {
							text += "<option value = " + rs.getInt(1) + ">" + rs.getString(3) + "</option>";
						}
					}
					text += "</select>"
									+ "</div>";		
					
					//Input  detail address
					text += "<div class = 'select'>"
								+ "<p>Input  detail address</p>"
								+ "<p><input id = 'detail' value = '" + house + "'><span class='checkDetail'></span></p>"
								+ "<button>Submit</button>"
							+ "</div>";
											
					text += "<span class='close'>+</span>"
							+ "</div>"
								+ "</div>";
					
					if (type.equals("change") || type.equals("get")) {
						out.print(text);
					} else if (type.equals("save")) {
						if (house != null && !house.trim().equals("")) {
							user.setAddress(house + ", " + String.join(", ", address) + ", " +
									wards + ", " + district + ", " + province);
						}
					}
				} else {
					response.sendRedirect(response.encodeRedirectURL("home"));
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
		doGet(request, response);
	}

}
