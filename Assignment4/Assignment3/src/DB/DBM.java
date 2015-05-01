package DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Assign3.Part;
public class DBM {

	public static void main(String[] args) throws SQLException, IOException{
		@SuppressWarnings("unused")
		Part part = new Part("1", "Needle", "Target", 123, 456, "abc123", "W1" );
        MyDB db = new MyDB();
        db.getParts();
        
        
        //test list
        //db.getPart();
        //ArrayList<Part> list = db.getParts();
        //System.out.println("List: " + list.toString());
        
        db.closeDB();
	}
}
