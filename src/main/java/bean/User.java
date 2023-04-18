package bean;

public class User {
	private String username;
	private String password;
	private String usermess;
	private String passmess;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getUsermess() {
		return usermess;
	}

	public void setUsermess(String usermess) {
		this.usermess = usermess;
	}

	public String getPassmess() {
		return passmess;
	}

	public void setPassmess(String passmess) {
		this.passmess = passmess;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validate(String user, String pass) {
		if (username.trim().equals("")) {
			this.usermess = "Please input Username!";
			this.setPassmess("");
			return false;
		} 
		if (password.trim().equals("")) {
			this.passmess = "Please input Password!";
			this.setUsermess("");
			return false;
		} 
		if (!(username.trim().equalsIgnoreCase(user) && password.trim().equals(pass))) {
			this.passmess = "Please input Password!";
			this.setUsermess("ABCCCCCCCCCCCC");
			this.setPassmess("ABCCCCCCCCCCCC");
			return false;
		} 
		return true;
	}

}
