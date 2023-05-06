package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	private Connection con;
	
	public Account(Connection con) {
		this.con = con;
	}
	
	 /**
     * Hàm kiểm tra thông tin đăng nhập của người dùng có khớp với dữ liệu trong cơ sở dữ liệu không
     * @param email địa chỉ mail hợp lệ người dùng nhập
     * @param password mật khẩu hợp lệ người dùng đã nhập
     * @return true nếu khớp với database, false nếu không khớp
     */
	public String login(String email, String password) throws SQLException {
		String sql = "select count(*) from account where user_mail=? and password=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		String result = "";
		stmt.setString(1, email);
		stmt.setString(2, password);
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			if (rs.getInt(1) == 0) return "0";
		}
		sql = "select account_role from account where user_mail=? and password=?";
		stmt = con.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, password);
		
		rs = stmt.executeQuery();
		
		while (rs.next()) {
			result = rs.getString(1);
		}
		return result;
	}
}
