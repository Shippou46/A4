package DB;

import java.sql.*;
import java.util.ArrayList;

import Assign3.Part;

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
		String message = "INSERT into INVENTORY (pNum, pName, v, q, id, ex, loc)"
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


	/*public void update(Part part) throws SQLException {
		Statement s = connect.createStatement();
		Part last = this.getPartID(Part.getIDNumber());
		boolean newer = true;
		String message = "UPDATE Inventory set";

		if (!last.getPartNumber().equals(part.getPartNumber())) {
			if (newer) {
				newer = false;
			} else {
				message += ",";
			}
			message += "pNum =\"" + part.getPartNumber() + "\"";
		}

		if (!last.getPartName().equals(part.getPartName())) {
			message += "pName = \"" + part.getPartName() + "\"";
			if (newer) {
				newer = false;
			}
		}
		if (!last.getVendor().equals(part.getVendor())) {
			if (newer) {
				newer = false;
			} else {
				message += ",";
			}
			message += "v= \"" + part.getVendor() + "\"";
		}
		
		// id
		message += "WHERE id = " + Part.getIDNumber();

		if (!last.getExPartNumber().equals(part.getExPartNumber())) {
			if (newer) {
				newer = false;
			} else {
				message += ",";
			}
			message += "ex =\"" + part.getExPartNumber() + "\"";
		}

		if (!last.getLocation().equals(part.getLocation())) {
			if (newer) {
				newer = false;
			} else {
				message += ",";
			}
			message += "ex =\"" + part.getLocation() + "\"";
		}
		s.executeUpdate(message);

	}
	*/
	public void closeDB() throws SQLException {
		this.connect.close();
	}
}
