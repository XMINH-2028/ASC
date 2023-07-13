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
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Product getProduct(int id, int quality) throws ClassNotFoundException, SQLException  {
		Connection con = new ConnectDB().getConnection();
		String sql = "select * from products where product_id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		
		Product pr = new Product();
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			pr = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), quality, false);
		}
		con.close();
		return pr;
	}

	public void getCart() throws ClassNotFoundException, SQLException  {
		Connection con = new ConnectDB().getConnection();
		String sql = "select x.product_id, product_name, product_des, product_price, product_img_source, "
				+ "product_type, product_brand, quantity from (select * from carts where user_mail = ? ) as x "
				+ "left join products p on x.product_id = p.product_id";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, email);
		
		ResultSet rs = stmt.executeQuery();
		
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			Product pr = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), false);
			list.add(pr);
		}
		if (productList.size() != 0) {
			Function ft = new Function();
			ft.mergeListP(productList, list);
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
	
	public void addCart(int id, int quantity) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		Product pr = getProduct(id, quantity);
		for (Product x : productList) {
			if (pr.getId() == x.getId()) {
				x.setQuantity(x.getQuantity() + pr.getQuantity());
				String sql = "update carts set quantity = ? where user_mail = ? and product_id = ?";
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
		String sql = "insert into carts value (?, ?, ?);";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setInt(2, id);
		stmt.setInt(3, quantity);
		stmt.executeUpdate();
		con.close();
	}
	
	public void changeCart(int id, int quantity) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		for (Product x : productList) {
			if (x.getId() == id) {
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
	
	public double totalCart() {
		double sum = 0;
		for (int i = 0; i < productList.size(); i++) {
			if (productList.get(i).isChecked()) {
				sum += productList.get(i).getPrice() * productList.get(i).getQuantity();
			}
		}
		return sum;
	}
	
	public void checked(int id){
		for (Product x : productList) {
			if (x.getId() == id) {
				x.setChecked(x.isChecked() == true ? false : true);
			}
		}
	}

}