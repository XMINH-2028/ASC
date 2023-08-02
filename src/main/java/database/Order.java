package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
	private int orderId;
	private int orderStatus;
	private String orderDate;
	private String orderDiscountCode;
	private String orderAddress;
	private List<Integer> productId;
	private List<Integer> amountProduct;
	private List<Double> productPrice;
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDiscountCode() {
		return orderDiscountCode;
	}

	public void setOrderDiscountCode(String orderDiscountCode) {
		this.orderDiscountCode = orderDiscountCode;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public List<Integer> getProductId() {
		return productId;
	}

	public void setProductId(List<Integer> productId) {
		this.productId = productId;
	}

	public List<Integer> getAmountProduct() {
		return amountProduct;
	}

	public void setAmountProduct(List<Integer> amountProduct) {
		this.amountProduct = amountProduct;
	}

	public List<Double> getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(List<Double> productPrice) {
		this.productPrice = productPrice;
	}

	public Order() {
		super();
	}

	public Order(int orderId, int orderStatus, String orderDate, String orderDiscountCode, String orderAddress) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.orderDiscountCode = orderDiscountCode;
		this.orderAddress = orderAddress;
	}

	public int creatOrder(Account user, int status, String code) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		Function ft = new Function();
		
		String sql = "INSERT INTO orders (user_mail, order_status, order_date, order_discount_code, "
				+ "order_address) VALUES (?, ?, now(), ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql, new int[] {1});
		stmt.setString(1, user.getEmail());
		stmt.setInt(2, status);
		stmt.setString(3, code);
		stmt.setString(4, ft.getAddress(user.getAddress()));
		stmt.executeUpdate();
		
		ResultSet rs = stmt.getGeneratedKeys();
		
		int rt = 0;
		if (rs.next()) {
			rt = (int) rs.getLong(1);
         }
		 else {
			 return 0;
		 }
		con.close();
		return rt;
	}
	
	public void creatOrderDetail(ShoppingCart cart, int id) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		String sql = "INSERT INTO orders_detail VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		for (int i = 0; i < cart.getProductList().size();) {
			if (cart.getProductList().get(i).isChecked()) {
				stmt.setInt(1, id);
				stmt.setInt(2, cart.getProductList().get(i).getId());
				stmt.setInt(3, cart.getProductList().get(i).getQuantity());
				stmt.setDouble(4, cart.getProductList().get(i).getPrice());
				stmt.execute();
				cart.deleteCart(cart.getProductList().get(i).getId());
			} else {
				 i++;
			}
		}
		con.close();
	}
	
	public void getOrderDetail() throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		String sql = "select * from orders_detail where order_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, orderId);
		
		ResultSet rs = stmt.executeQuery();
		
		productId = new ArrayList<>();
		amountProduct = new ArrayList<>();
		productPrice = new ArrayList<>();
		while (rs.next()) {
			productId.add(rs.getInt(2));
			amountProduct.add(rs.getInt(3));
			productPrice.add(rs.getDouble(4));
		}
		con.close();
	}
	
	public double totalPay() {
		double rs = 0;
		for (int i = 0; i < productId.size(); i++) {
			rs += amountProduct.get(i) * productPrice.get(i);
		}
		
		return rs;
	}

}
