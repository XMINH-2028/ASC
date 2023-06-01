package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sendemail.SendEmail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import database.Account;

/**
 * Servlet implementation class ResetServlet
 */
public class ResetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResetServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		try {
			//Lấy thuộc tính forget khi người dùng quên mật khẩu từ session
			Account forget = (Account) session.getAttribute("forget");
			//Lấy yêu cầu từ người dùng
			String action = request.getParameter("action");
			//Nếu hết phiên làm việc đặt lại và thông báo tới người dùng 
			if (forget == null) {
				forget = new Account("getcode");
				session.setAttribute("forget", forget);
				session.setMaxInactiveInterval(60*10);
				response.sendRedirect(response.encodeRedirectURL("getcode?alert=session is over, re-enter email"));
			} else {
				if (action.equals("getcode")) {
				// Lấy code xác thực khi người dùng quên mật khẩu nhập
					String email = request.getParameter("email");
					//Kết nối tới database
					Connection con = null;
					DataSource ds = (DataSource)session.getAttribute("ds");
					try {
						con = ds.getConnection();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						out.print("Can't connect to database");
						return;
					}
					if (forget.exist(con,email)) {
						//Nếu email người dùng nhập đã đăng kí tài khoản
						//Tạo mã xác thực
						int randomNum = (int) (Math.random() * 1000000000);
						try {
							//Gửi email tới địa chỉ mail hợp lệ người dùng đã nhập
							SendEmail.send(email, "Verify code", randomNum + "");
						} catch (Exception e) {
							// TODO: handle exception
							out.print(e);
						}
						//Tạo session forget với địa chỉ email hợp lê, thay đổi action, lưu mã xác thực và chuyển tới trang xác thực
						forget = new Account(email, "verify", randomNum + "");
						session.setMaxInactiveInterval(60*2);
						session.setAttribute("forget", forget);
						String text = "We have sent the verification code to " + email;
						response.sendRedirect(response.encodeRedirectURL("verify?alert=" + text));
					} else {
						//Nếu email người dùng nhập chưa đăng kí tài khoản
						forget.setAction("getcode");
						session.setAttribute("forget", forget);
						response.sendRedirect(response.encodeRedirectURL("getcode?email=" + email + "&error=Email does not exist"));
					}
				} else if (action.equals("forgetverify")) {
					// Kiểm tra code xác thực người dùng đã nhận
					if (forget.getAction().equals("verify")) {
						//Nếu chưa kiểm tra code xác thực
						//Lấy mã xác thực người dùng nhập
						String code = request.getParameter("code");
						if (code != null && code.equals(forget.getCode())) {
							//Nếu code người dùng nhập bằng code đã gửi
							forget.setAction("reset");
							session.setAttribute("forget", forget);
							response.sendRedirect(response.encodeRedirectURL("reset"));
						} else {
							//Nếu code người dùng nhập không đúng
							forget.setAction("verify");
							session.setAttribute("forget", forget);
							response.sendRedirect(response.encodeRedirectURL("verify?error=Wrong code"));
						}
					} else {
						//Nếu đã kiểm tra hợp lệ và chuyển tới bước tiếp theo
						response.sendRedirect(response.encodeRedirectURL(forget.getAction()));
					}
				} else if (action.equals("reset")) {
					// Kiểm tra mật khẩu người dùng đặt lại
					if (forget.getAction().equals("reset")) {
						//Nếu chưa đổi mật khẩu
						//Lấy tham số người dùng nhập
						String pass = request.getParameter("password");
						String repass = request.getParameter("repass");
						//Xóa thông báo cũ
						forget.setErrorCheckAccount();
						if (!forget.vreset(pass, repass)) {
							//Nếu mật khẩu người dùng sai thông báo tới người dùng
							forget.setAction("reset");
							session.setAttribute("forget", forget);
							response.sendRedirect(response.encodeRedirectURL("reset"));
						} else {
							//Kết nối tới database
							Connection con = null;
							DataSource ds = (DataSource)session.getAttribute("ds");
							try {
								con = ds.getConnection();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								out.print("Can't connect to database");
								return;
							}
							//Đặt lại mật khẩu xóa session forget, tạo session vlogin và quay về trang login
							forget.setPass(con,pass);
							Account login = new Account();
							login.setEmail(forget.getEmail());
							session.setAttribute("login",login);
							session.removeAttribute("forget");
							session.setMaxInactiveInterval(60*10);
							response.sendRedirect(response.encodeRedirectURL("login"));
						}
					} else {
						//Nếu đã đặt lại mật khẩu
						response.sendRedirect(response.encodeRedirectURL(forget.getAction()));
					}
				}
			}
		} catch (NullPointerException e) {
			out.print(e);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
