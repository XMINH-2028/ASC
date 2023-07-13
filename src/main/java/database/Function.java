package database;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Function {
	public static boolean checkInt(String text) {
		String regex = "[0-9]+";
		if (text.matches(regex)) return true;
		else return false;
	}
	
	public static String vnd(double price) { 
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
	public static List<Integer> mergeList(List<Integer>...x) {
		List<Integer> mergeList = new ArrayList<>();
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
				System.out.print(mergeList);
			}
		}
		return mergeList;
	}
	
	public static List<Product> mergeListP(List<Product>...x) {
		List<Product> mergeList = new ArrayList<>();
		mergeList = x[0];
		for (int i = 1; i < x.length; i++) {
			for (int j = 0; j < mergeList.size();) {
				int count = 0;
				for (Product k : x[i]) {
					if (mergeList.get(j).getId() == k.getId()) {
						count += 1;
					}
				}
				if (count == 0) {
					mergeList.remove(j);
				} else {
					 j++;
				}
				System.out.print(mergeList);
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

}
