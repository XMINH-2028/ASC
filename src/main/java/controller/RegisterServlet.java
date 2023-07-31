package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			HttpSession session = request.getSession();
			//Lấy thuộc tính forget khi người dùng quên mật khẩu từ session
			Validate v = (Validate) session.getAttribute("register");
			
			if (v == null) {
				response.sendRedirect(response.encodeRedirectURL("register"));
			} else {
				if (v.getInfo().get("action").equals("register")) {
					//Lấy dữ liệu từ form login
					String firstname = request.getParameter("firstname");
					String lastname = request.getParameter("lastname");
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					String repass = request.getParameter("repass");
					if (!v.vregister(firstname, lastname, email, password,repass)) {
						//Kiểm tra email, password, repass không hợp lệ thì chuyển hướng về trang register và thông báo lỗi
						session.setAttribute("register", v);
						response.sendRedirect(response.encodeRedirectURL("register"));
					} else {
						//Nếu email, password và repass hợp lệ thì gửi mail xác nhận và chuyển tới trang verify
						//Tạo mã xác thực
						int randomNum = (int) (Math.random() * 1000000000);
						try {
							//Gửi email tới địa chỉ mail hợp lệ người dùng đã nhập
							SendEmail.send(email, "Verify code", randomNum + "");
						} catch (Exception e) {
							// TODO: handle exception
							out.print(e);
						}
						//Tạo session register lưu thông tin và chuyển tới trang xác thực
						v.getInfo().put("code", randomNum+"");
						v.getInfo().put("action", "verify");
						v.getInfo().put("email", email);
						v.getInfo().put("password", password);
						session.setAttribute("register", v);
						String text = "We have sent the verification code to " + email;
						response.sendRedirect(response.encodeRedirectURL("verify?alert=" + text));
					}
				} else if (v.getInfo().get("action").equals("verify")) {
					// Kiểm tra code xác thực người dùng đã nhận
					//Lấy mã xác thực người dùng nhập
					String code = request.getParameter("code");
					if (code != null && code.equals(v.getInfo().get("code"))) {
						//Nếu code người dùng nhập bằng code đã gửi
						Account account = new Account();
						account.creatAccount(v.getInfo().get("email"), v.getInfo().get("password"),
								v.getInfo().get("firstname"), v.getInfo().get("lastname"));
						Validate login = new Validate();
						login.getInfo().put("email",v.getInfo().get("email"));
						session.setAttribute("login",login);
						session.removeAttribute("register");
						response.sendRedirect(response.encodeRedirectURL("login"));
					} else {
						//Nếu code người dùng nhập không đúng
						response.sendRedirect(response.encodeRedirectURL("verify?error=Wrong code"));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
