package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private int role;
	private String repass;
	private String fnamealert;
	private String lnamealert;
	private String repalert;
	private String mailalert;
	private String passalert;
	private String action;
	private String code;
	
	public Account() {
		
	}
	
	public Account(String action) {
		this.action = action;
	}
	
	public Account(String email, String action, String code) {
		this.email = email;
		this.action = action;
		this.code = code;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFnamealert() {
		return fnamealert;
	}

	public void setFnamealert(String fnamealert) {
		this.fnamealert = fnamealert;
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLnamealert() {
		return lnamealert;
	}

	public void setLnamealert(String lnamealert) {
		this.lnamealert = lnamealert;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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

	 public String getRepass() {
		return repass;
	}

	public String getRepalert() {
		return repalert;
	}

	public String getMailalert() {
		return mailalert;
	}

	public String getPassalert() {
		return passalert;
	}
	
	/**
	  * Hàm xóa các tham số 
	  */
	public void setErrorCheckAccount() {
		repalert = "";
		passalert = "";
	}
	
	/**
	 * Hàm đặt tham số khi email và password người dùng nhập vào không khớp
	 * @param error thông báo lỗi
	 */
	public void setErrorCheckAccount(String error) {
		mailalert = error;
		passalert = error;
	}

	/**
     * Hàm kiểm tra thông tin đăng nhập của người dùng có khớp với dữ liệu trong cơ sở dữ liệu không
     * @param con kết nối tới database
     * @param email địa chỉ mail hợp lệ người dùng nhập
     * @param password mật khẩu hợp lệ người dùng đã nhập
     * @return true nếu khớp với database, false nếu không khớp
     * @throws SQLException
     */
	public int getRole(Connection con, String email, String password) throws SQLException {
		String sql = "select account_role from account where user_mail=? and password=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		int result = 0;
		stmt.setString(1, email);
		stmt.setString(2, password);
		
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			result = rs.getInt(1);
			role = result;
		}
		con.close();
		return result;
	}
	
	/**
	 * Hàm kiểm tra email đã được đăng kí hay chưa
	 * @param con kết nối tới database
	 * @param email địa chỉ mail người dùng nhập
	 * @return true nếu email đã đăng kí, false nếu chưa đăng kí
	 * @throws SQLException
	 */
	public boolean exist(Connection con, String email) throws SQLException {
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
	 * Hàm kiểm tra thông tin người dùng nhập vào khi đăng nhập
	 * @param mail địa chỉ mail người dùng nhập 
	 * @param pass mật khẩu người dùng nhập 
	 * @return true nếu đúng cú pháp, false nếu sai
	 */
	public boolean vlogin(String mail, String pass) {
		email = mail;
		password = pass;
		
		//Regex kiểm tra password và email
		String regex="[a-z0-9_-]{6,12}+";
		String regexmail="^[\\w]+@[\\w]+\\.?[\\w]+\\.[A-Za-z]{2,6}$";
		
		//Kiểm tra email có để trống không
		if (mail.equals("")) {
			mailalert = "please input email";
			return false;		
		}
		//Kiểm tra email có khớp với điều kiện không
		if (!mail.matches(regexmail)) {
			mailalert = "invalid syntax";
			return false;		
		}
		//Kiểm tra password có để trống không
		if (pass.equals("")) {
			passalert = "please input password";
			return false;		
		}
		//Kiểm tra password có khớp với điều kiện không
		if (!pass.matches(regex)) {
			passalert = "invalid syntax";
			return false;		
		} 
		return true;
	}
	
	/**
	 * Hàm kiểm tra mật khẩu người dùng đặt lại
	 * @param pass mật khẩu người dùng nhập 
	 * @param repass mật khẩu người dùng nhập lại
	 * @return true nếu 2 tham số đúng cú pháp và giống nhau, false khi sai cú pháp hoặc không giống nhau
	 */
	public boolean vreset(String pass, String repass) {
		password = pass;
		this.repass = repass;
		
		//Regex kiểm tra password và repass
		String regex="[a-z0-9_-]{6,12}+";
		
		//Kiểm tra password có để trống không
		if (pass.equals("")) {
			passalert = "please input password";
			return false;		
		} else if (!pass.matches(regex)) {
			//Kiểm tra password có khớp với điều kiện không
			passalert = "invalid syntax";
			return false;		
		} else if (repass.equals("")) {
			//Kiểm tra repass có để trống không
			repalert = "please input repassword";
			return false;		
		} else if (!repass.matches(pass)) {
			//Kiểm tra repass có khớp với password không
			repalert = "repassword is not the same as password";
			return false;		
		} 
		return true;
	}
	
	/**
	 * Hàm đặt lại mật khẩu khi người dùng yêu cầu
	 * @param con kết nối tới database
	 * @param pass mật khẩu mới
	 * @throws SQLException
	 */
	
	public void setPass(Connection con, String pass) throws SQLException {
		String sql = "update account set password = ? where user_mail = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, pass);
		stmt.setString(2, email);
		stmt.executeUpdate();
		con.close();
	}
	
	/**
	 * Hàm kiểm tra thông tin người dùng nhập vào khi đăng kí
	 * @param con kết nối tới database
	 * @param fname tên người dùng nhập
	 * @param lname họ người dùng nhập
	 * @param mail địa chỉ mail người dùng nhập 
	 * @param pass mật khẩu người dùng nhập 
	 * @param repass mật khẩu người dùng nhập lại
	 * @return true nếu đúng cú pháp, false nếu sai
	 * @throws SQLException 
	 */
	public boolean vregister(Connection con, String fname, String lname, String mail, String pass, String repass) throws SQLException  {
		firstname = fname;
		lastname = lname;
		email = mail;
		password = pass;
		this.repass = repass;
		//Regex kiểm tra password và email
		String regex="[a-z0-9_-]{6,12}+";
		String regexmail="^[\\w_]+@[\\w\\.]+\\.[A-Za-z]{2,6}$";
		
		//Kiểm tra firstname có để trống không
		if (fname.equals("")) {
			fnamealert = "please input firstname";
			return false;		
		}
		//Kiểm tra lastname có để trống không
		if (lname.equals("")) {
			lnamealert = "please input lastname";
			return false;		
		}
		//Kiểm tra email có để trống không
		if (mail.equals("")) {
			mailalert = "please input email";
			return false;		
		}
		//Kiểm tra email có khớp với điều kiện không
		if (!mail.matches(regexmail)) {
			mailalert = "invalid syntax";
			return false;		
		}
		//Kiểm tra nếu email đã đăng kí thì báo lỗi
		if (exist(con,mail)) {
			this.mailalert = "email address already exists";
			return false;	
		}
		//Kiểm tra password có để trống không
		if (pass.equals("")) {
			passalert = "please input password";
			return false;		
		}
		//Kiểm tra password có khớp với điều kiện không
		if (!pass.matches(regex)) {
			passalert = "invalid syntax";
			return false;		
		} else if (repass.equals("")) {
			//Kiểm tra repass có để trống không
			repalert = "please input repassword";
			return false;		
		} else if (!repass.matches(pass)) {
			//Kiểm tra repass có khớp với password không
			repalert = "doesn't match the password";
			return false;		
		} 
		return true;
	}
	
	
	/**
	 * Hàm tạo tài khoản mới trong database
	 * @param con kết nối tới database
	 * @throws SQLException
	 */
	
	public void creatAccount(Connection con) throws SQLException {
		String sql = "insert into account (user_mail, password, account_role, user_name) values(?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, email);
		stmt.setString(2, password);
		stmt.setInt(3, 2);
		stmt.setString(4, firstname + " " + lastname);
		
		stmt.executeUpdate();
		
		con.close();
	}
}
