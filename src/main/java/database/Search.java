package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Search {
	/**
	 * Hàm tìm kiếm sản phẩm theo tên
	 * @param text thông tin cần tìm kiếm
	 * @return dãy id các sản phẩm phù hợp với yêu cầu tìm kiếm
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List userSearch(String text) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		//Chia chuỗi thông tin tìm kiếm thành các đơn vị
		List<String> textSearch = new ArrayList<>(Arrays.asList(text.split(" ")));
		List<String> name = new ArrayList<>();
		List<Integer> id = new ArrayList<>();
		List<Integer> count = new ArrayList<>();
		String sql = "select product_id, product_name from products";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		//Lưu dữ liệu product_id và product_name của tất cả sản phẩm vào 2 list id và name
		while (rs.next()) {
			id.add(rs.getInt(1));
			name.add(rs.getString(2));
		}
		
		con.close();
		
		//Kiểm tra tên các sản phẩm có chứa thông tin tìm kiếm hay không
		for (int i = 0; i < name.size(); i++) {
			//Tạo biến đếm cho sản phẩm đầu tiên
			count.add(0);
			int x = 0;
			//Nếu trong tên sản phẩm có chứa 1 đơn vị thông tin cần tìm tưng biến đếm của sản phẩm đó 1 đơn vị
			for (String j : textSearch) {
				if ((name.get(i)).toLowerCase().contains(j.toLowerCase())) {
					x += 1;
					count.set(i,x);
				}
			}
		}
		
		//Kiểm tra sản phẩm nào có chứa nhiều thông tin cần tìm nhất thì giữ nguyên, còn lại thì đưa product_id tương ứng của sản phẩm về 0
		for (int k = 0; k < count.size(); k++) {
			if (count.get(k) < Collections.max(count) || Collections.max(count) == 0) {
				id.set(k, 0);
			}
		}
		
		//Xóa các sản phẩm có product_id = 0
		for (int k = 0; k < id.size();) {
			if (id.get(k) == 0) {
				id.remove(k);
			} else {
				k += 1;
			}
		}
		return id;
	}
	
	public List priceFilter(List<String> key, List<String> value) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		List<Double> price = new ArrayList<>();
		List<Integer> id = new ArrayList<>();
		List<Integer> count = new ArrayList<>();
		int[] priceLevel = {2,4,7,13,20};
		
		for (int i = 0; i < key.size();) {
			if (key.get(i).equals("action") || key.get(i).equals("type") || key.get(i).equals("url")) {
				key.remove(i);
				value.remove(i);
				continue;
			}
			i++;
		}
		
		if (key.size() == 0) {
			return new ArrayList<>();
		}
		
		String sql = "select product_id, product_price from products where ";
		if (value.size() == 1) {
			int v = Integer.parseInt(value.get(0));
			if (v == 0) {
				sql += "product_price <= " + priceLevel[0];
			} else if (v == priceLevel.length) {
				sql += "product_price >= " + priceLevel[v-1];
			} else {
				sql += "(product_price > " + priceLevel[v-1] + " and product_price <= " + 
						priceLevel[v] +")";
			}
		} else {
			for (int i = 0; i < value.size();i++) {
				int v = Integer.parseInt(value.get(i));
				if (i > 0) {sql += " or ";}
				if (v != priceLevel.length) {
					if (v == 0) {
						sql += "product_price <= " + priceLevel[0];
					} else {
						sql += "(product_price > " + priceLevel[v-1] + " and product_price <= " + 
								priceLevel[v] +")";
					}
				} else {
					sql += "product_price > " + priceLevel[v-1];
				}
			}	
		}
		sql += ";";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		//Lưu dữ liệu product_id và product_name của tất cả sản phẩm vào 2 list id và name
		while (rs.next()) {
			id.add(rs.getInt(1));
			price.add(rs.getDouble(2));
		}
		
		con.close();
		
		return id;
	}
	
	public List brandFilter(List<String> key, List<String> value) throws ClassNotFoundException, SQLException {
		Connection con = new ConnectDB().getConnection();
		List<String> brand = new ArrayList<>();
		List<Integer> id = new ArrayList<>();
		List<Integer> count = new ArrayList<>();
		List<String> copyValue = new ArrayList<>();
		
		for (int i = 0; i < key.size();) {
			if (key.get(i).equals("action") || key.get(i).equals("type") || key.get(i).equals("url")) {
				key.remove(i);
				value.remove(i);
				continue;
			}
			i++;
		}
		
		if (key.size() == 0) {
			return new ArrayList<>();
		}
		
		String sql = "select product_id, product_brand from products where ";
	
		for (int i = 0; i < value.size();i++) {
			if (i > 0) {sql += " or ";}
			sql += "product_brand = '" + value.get(i) + "'";
		}	
		
		sql += ";";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		ResultSet rs = stmt.executeQuery();
		//Lưu dữ liệu product_id và product_name của tất cả sản phẩm vào 2 list id và name
		while (rs.next()) {
			id.add(rs.getInt(1));
			brand.add(rs.getString(2));
		}
		
		con.close();
		
		
		
		return id;
	}
}
