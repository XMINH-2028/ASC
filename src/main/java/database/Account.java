package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	private String email;
	private String password;
	private int role;
	private String name;
	private String address;
	private String phone;
	private boolean checkPay;

	public Account() {
		
	}
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getRole() {
		return role;
	}


	public void setRole(int role) {
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public boolean isCheckPay() {
		return checkPay;
	}

	public void setCheckPay(boolean checkPay) {
		this.checkPay = checkPay;
	}
	
	/**
	 * Hàm lấy thông tin user từ database
	 * @param mail địa chỉ mail của user
	 * @param pass mật khẩu
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void getUser(String mail, String pass) throws SQLException, ClassNotFoundException {
		Connection con = new ConnectDB().getConnection();
		String sql = "select * from account where user_mail=? and password=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, mail);
		stmt.setString(2, pass);
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			email = rs.getString(1);
			password = rs.getString(2);
			role = rs.getInt(3);
			name = rs.getString(4);
			address = rs.getString(5);
			phone = rs.getString(6);
		}
		con.close();
	}
	
	/**
	 * Hàm cập nhật thông tin user vào database
	 * @param user_name tên
	 * @param user_phone số điện thoại
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void setUser(String user_name, String user_phone) throws SQLException, ClassNotFoundException {
		Connection con = new ConnectDB().getConnection();
		String sql = "update account set user_name = ?, user_phone = ?, user_address = ? where user_mail = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, user_name);
		stmt.setString(2, user_phone);
		stmt.setString(3, address);
		stmt.setString(4, email);
		name = user_name;
		phone = user_phone;
		stmt.executeUpdate();
		con.close();
	}


	/**
	 * Hàm kiểm tra email đã được đăng kí hay chưa
	 * @param con kết nối tới database
	 * @param email địa chỉ mail người dùng nhập
	 * @return true nếu email đã đăng kí, false nếu chưa đăng kí
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public boolean exist(String email) throws SQLException, ClassNotFoundException {
		Connection con = new ConnectDB().getConnection();
		String sql = "select count(*) from account where user_mail=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, email);
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			if (rs.getInt(1) == 0) return false;
		}
		
		con.close();
		return true;
		
	}
	
	/**
	 * Hàm đặt lại mật khẩu khi người dùng yêu cầu
	 * @param con kết nối tới database
	 * @param pass mật khẩu mới
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	
	public void setPass(String pass, String email) throws SQLException, ClassNotFoundException {
		Connection con = new ConnectDB().getConnection();
		String sql = "update account set password = ? where user_mail = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, pass);
		stmt.setString(2, email);
		stmt.executeUpdate();
		con.close();
	}
	
	/**
	 * Hàm tạo user mới khi đăng kí thành công
	 * @param mail địa chỉ mail
	 * @param pass mật khẩu
	 * @param firstname tên
	 * @param lastname họ
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void creatAccount(String mail, String pass, String firstname, String lastname) throws SQLException, ClassNotFoundException {
		Connection con = new ConnectDB().getConnection();
		String sql = "insert into account (user_mail, password, account_role, user_name) values(?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, mail);
		stmt.setString(2, pass);
		stmt.setInt(3, 2);
		stmt.setString(4, firstname + " " + lastname);
		
		stmt.executeUpdate();
		
		con.close();
	}
}
