package database;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Function {
	
	/**
	 * Hàm kiểm tra số nguyên
	 * @param text đầu vào kiểm tra
	 * @return true nếu là số nguyên
	 */
	public boolean checkInt(String text) {
		String regex = "[0-9]+";
		if (text.matches(regex)) return true;
		else return false;
	}
	
	/**
	 * Hàm format giá tiền
	 * @param price
	 * @return price đã format
	 */
	public String vnd(double price) { 
		 NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);  
		 String currency = formatter.format(price);  
		 currency = currency.substring(1, currency.length() - 3);
		 return currency;
	}
	

	/**
	 * Hàm lấy các giá trị giống nhau giữa các ArrayList đầu vào
	 * @param x dãy các ArrayList đầu vào
	 * @return ArrayList chứa các giá trị cùng xuất hiện ở tất cả các dãy đầu vào
	 */
	public List<Integer> mergeList(List<Integer>...x) {
		List<Integer> mergeList = new ArrayList<>();
		if (x.length == 0) return mergeList;
		mergeList = x[0];
		for (int i = 1; i < x.length; i++) {
			for (int j = 0; j < mergeList.size();) {
				int count = 0;
				for (int k : x[i]) {
					if (mergeList.get(j) == k) {
						count += 1;
					}
				}
				if (count == 0) {
					mergeList.remove(j);
				} else {
					 j++;
				}
			}
		}
		return mergeList;
	}
	
	/**
	 * Hàm tạo bản sao một ArrayList
	 * @param list ArrayList gốc
	 * @return ArrayList bản sao
	 */
	public List<String> dCopy(List<String> list) {
		List<String> copyList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			copyList.add(list.get(i));
		}
		return copyList;
	}
	
	/**
	 * Hàm lấy tên địa chỉ từ chuỗi nhà, xã-phường, quận-huyện, tỉnh-thành phố, id xã-phường, id quận-huyện, id tỉnh-thành phố
	 * @param text chuổi chứa địa chỉ
	 * @return tên địa chỉ
	 */
	public String getAddress(String text) {
		if (text == null || text.equals("")) return "";
		List<String> arr = Arrays.asList(text.split(","));
		String rs = "";
		for (int i = 0 ; i < arr.size() - 3; i++) {
			rs += arr.get(i);
			if (i < arr.size() - 4) rs += ",";
		}
		return rs;
	}
	
	/**
	 * Hàm lấy id địa chỉ từ chuỗi nhà, xã-phường, quận-huyện, tỉnh-thành phố, id xã-phường, id quận-huyện, id tỉnh-thành phố
	 * @param text chuổi chứa id địa chỉ
	 * @return id địa chỉ
	 */
	public String getAddressId(String text) {
		if (text == null || text.equals("")) return "";
		List<String> arr = Arrays.asList(text.split(","));
		String rs = arr.get(0) + ",";
		for (int i = arr.size() - 1 ; i > arr.size() - 4 ; i--) {
			rs += arr.get(i); 
			if (i > arr.size() - 3) rs += ",";
		}
		return rs;
	}
	
	/**
	 * Hàm lấy id từng thành phần trong chuỗi nhà, xã-phường, quận-huyện, tỉnh-thành phố, id xã-phường, id quận-huyện, id tỉnh-thành phố
	 * @param text chuỗi địa chỉ
	 * @param number số thứ tự thành phần muốn lấy
	 * @return id thành phần muốn lấy
	 */
	public String getId(String text, int number) {
		String rs = "";
		if (text != null && !text.equals("")) {
			String[] arr = text.split(",");
			
			if (number == 1) {
				for (int i = 0; i < arr.length - 6; i++) {
					rs += arr[i].trim();
					if (i < arr.length - 7) {rs += ", ";}
				}
			} else if (number == 2) {
				rs += arr[arr.length - 3].trim();
			} else if (number == 3) {
				rs += arr[arr.length - 2].trim();
			} else if (number == 4) {
				rs += arr[arr.length - 1].trim();
			}
		}
		return rs;
	}
	
	/**
	 * Hàm kiểm tra số điện thoại hợp lệ
	 * @param text chuỗi đầu vào kiểm tra
	 * @return true nếu hợp lệ
	 */
	public static boolean checkPhone(String text) {
		if (text == null) return false;
		String[] arr = {"032","033","034","035","036","037","038","039","086","096","097","098",
						"088","091","094","083","084","085","081","082",
						"089","090","093","070","079","077","076","078",
						"092","056","058","099","059"};
		String regex= "";
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				regex += "(^" + arr[i] + "|";
				
			} else if (i == arr.length - 1) {
				regex += "^" + arr[i] + ")";
				
			} else {
				regex += "^" + arr[i] + "|";
				
			}
		}
		regex += "[0-9]{7}+";
		return text.matches(regex);
	}
}
