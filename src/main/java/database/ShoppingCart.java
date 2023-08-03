package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	private String email;
	private String page;
	private boolean check;
	private List<Product> productList;
	private List<Order> orderList;
	
	public ShoppingCart(String email) {
		productList = new ArrayList<>();
		this.email = email;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<Product> getProductList() {
		return productList;
	}
	
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	public List<Order> getOrderList() {
		return orderList;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * Hàm lấy các sản phẩm trong giỏ hàng đã lưu từ database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void getCart() throws ClassNotFoundException, SQLException  {
		Connection con = new ConnectDB().getConnection();
		String sql = "select x.product_id, product_name, product_des, product_price, product_img_source, "
				+ "product_type, product_brand, quantity from (select * from carts where user_mail = ? ) as x "
				+ "join products p on x.product_id = p.product_id order by date desc";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, email);
		
		ResultSet rs = stmt.executeQuery();
		
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			Product pr = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), false);
			list.add(pr);
		}
		if (productList.size() != 0) {
			for (Product x : list) {
				for (Product y : productList) {
					if (x.getId() == y.getId()) {
						x.setChecked(y.isChecked());
						break;
					}
				}
			}
		}
		productList = list;
		con.close();
	}
	
	/**
	 * Hàm thêm sản phẩm vào ShoppingCart và database
	 * @param id
	 * @param quantity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addCart(int id, int quantity) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		Product pr = new Product();
		pr = pr.getProduct(id, quantity);
		if (pr.getId() != 0 && quantity >= 1) {
			pr.setChecked(true);
			for (Product x : productList) {
				if (pr.getId() == x.getId()) {
					x.setQuantity(x.getQuantity() + pr.getQuantity());
					x.setChecked(true);
					String sql = "update carts set quantity = ?, date = now() where user_mail = ? and product_id = ?";
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setInt(1, x.getQuantity());
					stmt.setString(2, email);
					stmt.setInt(3, id);
					stmt.executeUpdate();
					con.close();
					return;
				}
			}
			productList.add(pr);
			String sql = "insert into carts (user_mail, product_id, quantity) value (?, ?, ?);";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setInt(2, id);
			stmt.setInt(3, quantity);
			stmt.executeUpdate();
			con.close();
		}
	}
	
	/**
	 * Hàm thay đổi số lượng sản phẩm trong database và ShoppingCart
	 * @param id
	 * @param quantity
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void changeCart(int id, int quantity) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		for (Product x : productList) {
			if (x.getId() == id && quantity >= 1) {
				x.setQuantity(quantity);
				x.setChecked(true);
				String sql = "update carts set quantity = ? where user_mail = ? and product_id = ?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setInt(1, x.getQuantity());
				stmt.setString(2, email);
				stmt.setInt(3, id);
				stmt.executeUpdate();
				con.close();
			}
		}
	}
	
	/**
	 * Hàm xóa sản phẩn trong database và ShoppingCart
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteCart(int id) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).getId() == id) {
				productList.remove(i);
				String sql = "delete from carts where user_mail = ? and product_id = ?";
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setString(1, email);
				stmt.setInt(2, id);
				stmt.executeUpdate();
				con.close();
				return;
			}
		}
	}
	
	/**
	 * Hàm tính tổng số sản phẩm đã lựa chọn trong giỏ hàng
	 * @return quantity
	 */
	public double totalCart() {
		double sum = 0;
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).isChecked()) {
				sum += productList.get(i).getPrice() * productList.get(i).getQuantity();
			}
		}
		return sum;
	}
	
	/**
	 * Hàm tính tổng tiền các sản phẩm đã lựa chọn
	 * @return price
	 */
	public int totalProduct() {
		int sum = 0;
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).isChecked()) {
				sum += 1;
			}
		}
		return sum;
	}
	
	/**
	 * Hàm thay đổi trạng thái check/uncheck khi người dùng chọn và bỏ chọn
	 * @param id
	 * @param checkNumber
	 */
	public void checked(int id, int checkNumber){
		for (Product x : productList) {
			if (x.getId() == id ) {
				if (checkNumber == 0) {
					x.setChecked(false);
				} else if (checkNumber == 1) {
					x.setChecked(true);
				}
			}
		}
	}
	
	/**
	 * Hàm lấy các đơn đã đặt từ database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void creatOrderList() throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		String sql = "select * from orders where user_mail = ? order by order_date desc";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, email);
		
		ResultSet rs = stmt.executeQuery();
		
		orderList = new ArrayList<>();
		while (rs.next()) {
			Order order = new Order(rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
			order.getOrderDetail();
			orderList.add(order);
		}
		con.close();
	}

}
