package Assign3;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JFrame;
import DB.MyDB;

@SuppressWarnings("unused")
public class InventoryMain {

	public static void main(String[] args) throws SQLException{
		//database
		MyDB db = new MyDB();
		
		//model
		Inventory inv = new Inventory(db);
		
		//populate with a few test Parts
		inv.addPart(null, "01011", "1/2\" Bolt", "Sears", 100, 1,"1", "x" );
		inv.addPart(null, "X3599", "1\" Laminate Edging (Hot Pink)", "Plastics R Us", 50, 2, "2", "y");
		inv.addPart(null, "02949", "1/4\" Plywood 10x10", "", 10, 3, "3", "z");

		
		
		//controller
		InventoryController invC = new InventoryController(inv);
		
		//views
		PartsListView pView = new PartsListView(invC, inv);
		pView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pView.setSize(400, 300);
		pView.setVisible(true);
		
		inv.registerObserver(pView);
	}

}
