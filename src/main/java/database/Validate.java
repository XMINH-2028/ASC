package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class Validate {
	private HashMap<String,String> info = new HashMap<>();
	private HashMap<String,String> checkInfo = new HashMap<>();
	
	public Validate() {
		
	}
	
	public Validate(String action) {
		info.put("action", action);
	}
	
	public Validate(String email, String action, String code) {
		info.put("email", email);
		info.put("action", action);
		info.put("code", code);
	}
	
	public HashMap<String, String> getInfo() {
		return info;
	}

	public void setInfo(HashMap<String, String> info) {
		this.info = info;
	}

	public HashMap<String, String> getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(HashMap<String, String> checkInfo) {
		this.checkInfo = checkInfo;
	}

	/**
	  * Hàm xóa các tham số 
	  */
	public void setCheckInfo() {
		checkInfo.remove("email");
		checkInfo.remove("password");
	}
	
	/**
	 * Hàm đặt tham số khi email và password người dùng nhập vào không khớp
	 * @param error thông báo lỗi
	 */
	public void setCheckInfo(String text) {
		checkInfo.put("email", text);
		checkInfo.put("password", text);
	}
	
	/**
	 * Hàm kiểm tra thông tin người dùng nhập vào khi đăng nhập
	 * @param mail địa chỉ mail người dùng nhập 
	 * @param pass mật khẩu người dùng nhập 
	 * @return true nếu đúng cú pháp, false nếu sai
	 */
	public boolean vlogin(String mail, String pass) {
		info.put("email", mail);
		info.put("password", pass);
		
		//Regex kiểm tra password và email
		String regex="[a-z0-9_-]{6,12}+";
		String regexmail="^[\\w]+@[\\w]+\\.?[\\w]+\\.[A-Za-z]{2,6}$";
		
		//Kiểm tra email có để trống không
		if (mail.equals("")) {
			checkInfo.put("email", "please input email");
			return false;		
		}
		//Kiểm tra email có khớp với điều kiện không
		if (!mail.matches(regexmail)) {
			checkInfo.put("email", "invalid syntax");
			return false;		
		}
		//Kiểm tra password có để trống không
		if (pass.equals("")) {
			checkInfo.put("password", "please input password");
			return false;		
		}
		//Kiểm tra password có khớp với điều kiện không
		if (!pass.matches(regex)) {
			checkInfo.put("password", "invalid syntax");
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
		info.put("password", pass);
		info.put("repass", repass);
		
		//Regex kiểm tra password và repass
		String regex="[a-z0-9_-]{6,12}+";
		
		//Kiểm tra password có để trống không
		if (pass.equals("")) {
			checkInfo.put("password", "please input password");
			return false;		
		} else if (!pass.matches(regex)) {
			//Kiểm tra password có khớp với điều kiện không
			checkInfo.put("password", "invalid syntax");
			return false;		
		} else if (repass.equals("")) {
			//Kiểm tra repass có để trống không
			checkInfo.put("repass", "please input repassword");
			return false;		
		} else if (!repass.matches(pass)) {
			//Kiểm tra repass có khớp với password không
			checkInfo.put("repass", "repassword is not the same as password");
			return false;		
		} 
		return true;
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
	 * @throws ClassNotFoundException 
	 */
	public boolean vregister(String fname, String lname, String mail, String pass, String repass) throws SQLException, ClassNotFoundException  {
		info.put("email", mail);
		info.put("password", pass);
		info.put("repass", repass);
		info.put("firstname", fname);
		info.put("lastname", lname);
		Account user = new Account();
		//Regex kiểm tra password và email
		String regex="[a-z0-9_-]{6,12}";
		String regexmail="^[\\w_]+@[\\w\\.]+\\.[A-Za-z]{2,6}$";
		
		//Kiểm tra firstname có để trống không
		if (fname.trim().equals("")) {
			checkInfo.put("firstname", "please input firstname");
			return false;		
		}
		//Kiểm tra lastname có để trống không
		if (lname.trim().equals("")) {
			checkInfo.put("lastname", "please input lastname");
			return false;		
		}
		//Kiểm tra email có để trống không
		if (mail.equals("")) {
			checkInfo.put("email", "please input email");
			return false;		
		}
		//Kiểm tra email có khớp với điều kiện không
		if (!mail.matches(regexmail)) {
			checkInfo.put("email", "invalid syntax");
			return false;		
		}
		//Kiểm tra nếu email đã đăng kí thì báo lỗi
		if (user.exist(mail)) {
			checkInfo.put("email", "email address already exists");
			return false;	
		}
		//Kiểm tra password có để trống không
		if (pass.equals("")) {
			checkInfo.put("password", "please input password");
			return false;		
		}
		//Kiểm tra password có khớp với điều kiện không
		if (!pass.matches(regex)) {
			checkInfo.put("password", "invalid syntax");
			return false;		
		} else if (repass.equals("")) {
			//Kiểm tra repass có để trống không
			checkInfo.put("repass", "please input repassword");
			return false;		
		} else if (!repass.matches(pass)) {
			//Kiểm tra repass có khớp với password không
			checkInfo.put("repass", "doesn't match the password");
			return false;		
		} 
		return true;
	}
	
}
