package Assign3;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Product {

	private static int id;
	private String pNum, pdescription;
	static Random randomNumber = new Random();
	private ArrayList<ProductObserver> observers;
	private ArrayList<ProductPartDetail> pdetail;
	private ArrayList<Part> parts;
	private static int ver;


	public Product(String description, String n) {
		pNum = n;
		pdescription = description;

		if (pNum == null || pNum.length() < 1 || pNum.length() > 20
				|| !pNum.startsWith("A"))
			throw new IllegalArgumentException(
					"Product # cannot be blank, must begin with an \"A\", and must be less than 20 characters");
		if (pdescription == null || pdescription.length() > 255)
			throw new IllegalArgumentException("Product description too long");

		observers = new ArrayList<ProductObserver>();
	}

	public ArrayList<Part> getParts() {
		return parts;
	}
	
	public int getNumParts() {
		return parts.size();
	}
	
	public boolean partNameExists(String pName, Part part) {
		for(Part p : parts) {
			if(pName.equalsIgnoreCase(p.getPartName()) && (p != part || part == null))
				return true;
		}
		return false;
	}
	
	public boolean partNumberExists(int id, Part part){
		for(@SuppressWarnings("unused") Part p : parts){
			if(id != Part.getIDNumber())
			return true;
		}
		return false;
	}
	
	public Product() {
		pNum = "";
		pdescription = "";
	}

	public static int getIDNumber(){
		id = randomNumber.nextInt(1000000);
		return id;
	}

	public void setIDNumber(int idNumber){
		Product.id = idNumber;
	}
	
	public String getProductNumber() {
		return pNum;
	}

	public void setProductNumber(String pNum) {
		this.pNum = pNum;
		updateObservers();
	}
	
	public String getDescription(){
		return pdescription;
	}
	
	public void setDescription(String pdescription){
		this.pdescription = pdescription;
		updateObservers();
	}
	
	public void addPart(ProductPartDetail p){
		pdetail.add(p);
	}
	
	public void deletePart(ProductPartDetail p){
		pdetail.remove(p);
	}
	
	public static void VersionUpdate(){
		ver++;
	}
	
	public static int getVersion(){
		return ver;
	}
	
	public void setVersion(int ver) {
		Product.ver = ver;
	}	
	
	public Part addProduct(Part part, String pNum, String pName, String v, int q, int id, String ex, String loc) throws IllegalArgumentException, SQLException {
		if(partNameExists(pName, part))
			throw new IllegalArgumentException("Part Name already exists!");
		//if(partNumberExists(id, part))
		//	throw new IllegalArgumentException("Part Number already exists!");
		Part p = new Part(pNum, pName, v, q, id, ex, loc);
		parts.add(p);
		//db.addPart(p);
		updateObservers();
		//updateDB(p);
		return p;
	}
	

	public void registerObserver(ProductObserver o) {
		observers.add(o);
	}

	private void updateObservers() {
		for (ProductObserver o : observers) {
			try {
				o.updateObserver(this);
			} catch (Exception e) {
				;
			}
		}
	}

}
