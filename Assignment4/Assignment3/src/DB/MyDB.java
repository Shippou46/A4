package DB;

import java.sql.*;
import java.util.ArrayList;

import Assign3.Part;
import Assign3.Product;
import Assign3.ProductPartDetail;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MyDB {

	public Connection connect;
	public String url = "jdbc:mysql://devcloud.fulgentcorp.com:3306/soq971";
	public String user = "soq971";
	public String pw = "KzZSQolR5vFF69sboLh4";

	public MyDB() throws SQLException {

		MysqlDataSource src = new MysqlDataSource();
		src.setURL(url);
		src.setUser(user);
		src.setPassword(pw);
		connect = src.getConnection();
	}

	public ArrayList<Part> getParts() {
		ArrayList<Part> list = null;
		Statement s;
		ResultSet r;
		String message = "SELECT * FROM Inventory";

		try {
			s = connect.createStatement();
			r = s.executeQuery(message);
			list = new ArrayList<Part>();

			while (r.next()) {
				Part test = new Part(r.getString("pNum"), r.getString("pName"),
						r.getString("v"), r.getInt("q"), r.getInt("id"),
						r.getString("ex"), r.getString("loc"));
				test.setVersion(r.getInt("version"));
				list.add(test);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getLocation() {
		ArrayList<String> list = null;
		Statement s;
		ResultSet r;
		String message = "SELECT loc FROM Inventory";

		try {
			s = connect.createStatement();
			r = s.executeQuery(message);
			list = new ArrayList<String>();

			while (r.next()) {
				list.add(r.getString("loc"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Part getPartID(int id) {
		Statement s;
		ResultSet r;
		String message = "SELECT * FROM Inventory where id = " + id;
		Part part = null;
		try {
			s = connect.createStatement();
			r = s.executeQuery(message);
			if (r.next()) {
				part = new Part(r.getString("pNum"), r.getString("pName"),
						r.getString("v"), r.getInt("q"), r.getInt("id"),
						r.getString("ex"), r.getString("loc"));
				part.setVersion(r.getInt("version"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return part;
	}

	public int getLocationID(String loc) {
		int lID = 0;
		Statement s;
		ResultSet r;
		String message = "SELECT loc FROM Inventory where loc = \"" + loc
				+ "\"";
		try {
			s = connect.createStatement();
			r = s.executeQuery(message);
			if (r.next()) {
				lID = r.getInt("loc");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lID;
	}

	public void addPart(Part part) {
		Statement s;
		ResultSet r;
		String message = "INSERT into INVENTORY (pNum, pName, v, q, id, ex, loc, version)"
				+ "values ("
				+ part.getPartNumber()
				+ ", "
				+ part.getPartName()
				+ "\", "
				+ part.getVendor()
				+ "\", "
				+ part.getQuantity()
				+ ","
				+ Part.getIDNumber()
				+ ","
				+ part.getExPartNumber()
				+ "\", "
				+ part.getLocation() + "\")";

		try {
			s = connect.createStatement();
			s.executeUpdate(message, s.RETURN_GENERATED_KEYS);
			r = s.getGeneratedKeys();
			if (r.next()) {
				int id = r.getInt("id");
				part.setIDNumber(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deletePart(Part part) {
		Statement s;
		String message = "DELETE from INVENTORY where pName = "
				+ part.getPartName();

		try {
			s = connect.createStatement();
			s.executeUpdate(message);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Product getProductID(int id) {
		Statement s;
		ResultSet r;
		String message = "SELECT * FROM Product where id = " + id;
		Product prod = null;
		try {
			s = connect.createStatement();
			r = s.executeQuery(message);
			if (r.next()) {
				prod = new Product(r.getString("prodNum"),
						r.getString("pDescription"));
				prod.setIDNumber(id);
				prod.setVersion(r.getInt("version"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prod;
	}

	public ProductPartDetail getProductPart(int prodID, int partID) {
		String message = "SELECT * FROM ProductParts where partid = " + partID
				+ " and productid = " + prodID;
		ProductPartDetail prod = null;
		try {
			Statement s = connect.createStatement();
			ResultSet r = s.executeQuery(message);
			if (r.next()) {
				prod = new ProductPartDetail(r.getInt("prodID"),
						r.getInt("partID"), r.getInt("pQuantity"));
				prod.setVersion(r.getInt("version"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prod;
	}

	public ArrayList<Product> getProducts() {
		ArrayList<Product> list = null;
		String message = "SELECT * from Product";
		try {
			Statement s = connect.createStatement();
			ResultSet r = s.executeQuery(message);
			list = new ArrayList<Product>();
			// take everything from the resultSet and put it in a model class
			while (r.next()) {
				Product prod = new Product(r.getString("prodNum"),
						r.getString("pDescription"));
				prod.setIDNumber(r.getInt("id"));
				prod.setVersion(r.getInt("version"));
				list.add(prod);
			}
			// close that stuff in case it got opened.
			r.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<ProductPartDetail> getProductParts() {
		ArrayList<ProductPartDetail> list = null;
		String query = "SELECT * FROM ProductParts";
		try {
			Statement s = connect.createStatement();
			ResultSet r = s.executeQuery(query);
			list = new ArrayList<ProductPartDetail>();
			// take everything from the resultSet and put it in a model class
			while (r.next()) {
				ProductPartDetail prod = new ProductPartDetail(
						r.getInt("prodID"), r.getInt("partID"),
						r.getInt("pQuantity"));
				prod.setVersion(r.getInt("version"));

				list.add(prod);
			}
			// close that stuff in case it got opened.
			r.close();
			s.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void updateProduct(Product prod) {

		String message = "UPDATE IGNORE Product set description = \""
				+ prod.getDescription() + "\", productno = \""
				+ prod.getProductNumber() + " where id = " + Product.getIDNumber()
				+ ", version = " + prod.getVersion();
		System.out.println(message);
		try {
			Statement s = connect.createStatement();
			s.executeUpdate(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateProductPart(ProductPartDetail prod) {

		String message = "UPDATE IGNORE ProductParts set quantity = "
				+ prod.getPDQty() + " where partid = " + prod.getPartID()
				+ " and productid = " + prod.getProductID() + ", version = "
				+ prod.getVersion();
		System.out.println(message);
		try {
			Statement s = connect.createStatement();
			s.executeUpdate(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addProduct(Product prod) {
		int id = Product.getIDNumber();
		String pNum = prod.getProductNumber();
		String pDes = prod.getDescription();
		String message = "INSERT into Product (prodNum, pDescription, id, version)"
				+ "values (\"" + pNum + "\", \"" + pDes + "\", \"" + id + "\")";
		try {
			Statement s = connect.createStatement();
			int x = s.executeUpdate(message, Statement.RETURN_GENERATED_KEYS);
			prod.setIDNumber(x);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addProductPart(ProductPartDetail prod) {
		String query = "INSERT into ProductParts (prodID, partID, pQuantity) "
				+ "values (" + prod.getProductID() + "," + prod.getPartID()
				+ ", " + prod.getPDQty() + ", version = 0" + +prod.getVersion();
		try {
			Statement s = connect.createStatement();
			int x = s.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteProductPart(ProductPartDetail prod) {
		String message = "DELETE FROM ProductParts where partID = "
				+ prod.getPartID() + " and prodID = " + prod.getProductID();
		try {
			Statement s = connect.createStatement();
			s.executeUpdate(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void deleteProduct(Product prod) {
		String message = "delete from product where id = "
				+ Product.getIDNumber();
		try {
			Statement s = connect.createStatement();
			s.executeUpdate(message);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public void update(Part part) throws SQLException { Statement s =
	 * connect.createStatement(); Part last =
	 * this.getPartID(Part.getIDNumber()); boolean newer = true; String message
	 * = "UPDATE Inventory set";
	 * 
	 * if (!last.getPartNumber().equals(part.getPartNumber())) { if (newer) {
	 * newer = false; } else { message += ","; } message += "pNum =\"" +
	 * part.getPartNumber() + "\""; }
	 * 
	 * if (!last.getPartName().equals(part.getPartName())) { message +=
	 * "pName = \"" + part.getPartName() + "\""; if (newer) { newer = false; } }
	 * if (!last.getVendor().equals(part.getVendor())) { if (newer) { newer =
	 * false; } else { message += ","; } message += "v= \"" + part.getVendor() +
	 * "\""; }
	 * 
	 * // id message += "WHERE id = " + Part.getIDNumber();
	 * 
	 * if (!last.getExPartNumber().equals(part.getExPartNumber())) { if (newer)
	 * { newer = false; } else { message += ","; } message += "ex =\"" +
	 * part.getExPartNumber() + "\""; }
	 * 
	 * if (!last.getLocation().equals(part.getLocation())) { if (newer) { newer
	 * = false; } else { message += ","; } message += "ex =\"" +
	 * part.getLocation() + "\""; } s.executeUpdate(message);
	 * 
	 * }
	 */

	public void closeDB() throws SQLException {
		this.connect.close();
	}
}
