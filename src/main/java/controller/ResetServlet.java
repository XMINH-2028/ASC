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
import java.sql.SQLException;

import javax.sql.DataSource;

import database.Account;
import database.SendEmail;
import database.Validate;

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
		Account account = new Account();
		try {
			//Lấy thuộc tính forget khi người dùng quên mật khẩu từ session
			Validate v = (Validate) session.getAttribute("forget");
			//Lấy yêu cầu từ người dùng
			String action = request.getParameter("action");
			//Nếu hết phiên làm việc đặt lại và thông báo tới người dùng 
			if (v == null) {
				v = new Validate("getcode");
				session.setAttribute("forget", v);
				response.sendRedirect(response.encodeRedirectURL("getcode?alert=session is over, re-enter email"));
			} else {
				if (action.equals("getcode")) {
				// Lấy code xác thực khi người dùng quên mật khẩu nhập
					String email = request.getParameter("email");
					
					if (account.exist(email)) {
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
						v = new Validate(email, "verify", randomNum + "");
						session.setAttribute("forget", v);
						String text = "We have sent the verification code to " + email;
						response.sendRedirect(response.encodeRedirectURL("verify?alert=" + text));
					} else {
						//Nếu email người dùng nhập chưa đăng kí tài khoản
						v.getInfo().put("action","getcode");
						session.setAttribute("forget", v);
						response.sendRedirect(response.encodeRedirectURL("getcode?email=" + email + "&error=Email does not exist"));
					}
				} else if (action.equals("forgetverify")) {
					// Kiểm tra code xác thực người dùng đã nhận
					if (v.getInfo().get("action").equals("verify")) {
						//Nếu chưa kiểm tra code xác thực
						//Lấy mã xác thực người dùng nhập
						String code = request.getParameter("code");
						if (code != null && code.equals(v.getInfo().get("code"))) {
							//Nếu code người dùng nhập bằng code đã gửi
							v.getInfo().put("action","reset");
							session.setAttribute("forget", v);
							response.sendRedirect(response.encodeRedirectURL("reset"));
						} else {
							//Nếu code người dùng nhập không đúng
							v.getInfo().put("action","verify");
							session.setAttribute("forget", v);
							response.sendRedirect(response.encodeRedirectURL("verify?error=Wrong code"));
						}
					} else {
						//Nếu đã kiểm tra hợp lệ và chuyển tới bước tiếp theo
						response.sendRedirect(response.encodeRedirectURL(v.getInfo().get("action")));
					}
				} else if (action.equals("reset")) {
					// Kiểm tra mật khẩu người dùng đặt lại
					if (v.getInfo().get("action").equals("reset")) {
						//Nếu chưa đổi mật khẩu
						//Lấy tham số người dùng nhập
						String pass = request.getParameter("password");
						String repass = request.getParameter("repass");
						//Xóa thông báo cũ
						v.setCheckInfo();
						if (!v.vreset(pass, repass)) {
							//Nếu mật khẩu người dùng sai thông báo tới người dùng
							v.getInfo().put("action","reset");
							session.setAttribute("forget", v);
							response.sendRedirect(response.encodeRedirectURL("reset"));
						} else {
							//Đặt lại mật khẩu xóa session forget, tạo session vlogin và quay về trang login
							account.setPass(pass, v.getInfo().get("email"));
							Validate login = new Validate();
							login.getInfo().put("email",v.getInfo().get("email"));
							session.setAttribute("login",login);
							session.removeAttribute("forget");
							response.sendRedirect(response.encodeRedirectURL("login"));
						}
					} else {
						//Nếu đã đặt lại mật khẩu
						response.sendRedirect(response.encodeRedirectURL(v.getInfo().get("action")));
					}
				} else {
					response.sendRedirect(response.encodeRedirectURL("home"));
				}
			}
		} catch (Exception e) {
			response.sendRedirect(response.encodeRedirectURL("home"));
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
