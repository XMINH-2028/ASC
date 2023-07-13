package database;

public class Product {
	private int id;
	private String name;
	private String des;
	private double price;
	private String img;
	private String type;
	private String brand;
	private int quantity;
	private boolean checked = false;
	
	public Product() {
		super();
	}

	public Product(int id, String name, String des, double price, String img, String type, String brand, int quantity, boolean checked) {
		super();
		this.id = id;
		this.name = name;
		this.des = des;
		this.price = price;
		this.img = img;
		this.type = type;
		this.brand = brand;
		this.quantity = quantity;
		this.checked = checked;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
