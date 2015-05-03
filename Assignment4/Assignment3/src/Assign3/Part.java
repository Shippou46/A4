package Assign3;

import java.util.ArrayList;
import java.util.Random;

public class Part {
	private String partNumber;
	private String partName;
	private String vendor;
	private int quantity;
	private static int idNumber;
	private String exPartNum;
	private String location = "Unknown";
	private static String qUnit = "Unknown";
	
	Product product;
	
	static Random randomNumber = new Random();
	
	
	private ArrayList<PartObserver> observers;
	private static int ver;
	
	public Part(String pNum, String pName, int q, int id, String ex, String loc) {
		this(pNum, pName, "", q, id, ex, loc);
	}
	
	public Part(String pNum, String pName, String v, int q, int id, String ex, String loc) {
		if(pNum == null || pNum.length() < 1)
			throw new IllegalArgumentException("Part # cannot be blank");
		if(pName == null || pName.length() < 1)
			throw new IllegalArgumentException("Part Name cannot be blank");
		if(q < 1)
			throw new IllegalArgumentException("Quantity cannot be < 1");
		if(ex == null || ex.length() > 50)
			throw new IllegalArgumentException("External Part Number too long");
		
		partNumber = pNum;
		partName = pName;
		vendor = v;
		quantity = q;
		location = loc;
		idNumber = 0;
		exPartNum = ex;
		ver = 0;
		
		observers = new ArrayList<PartObserver>();
	}

		public Product getProduct(){
			return product;
		}
		
		public void setProduct(Product product){
			this.product = product;
		}
		
		public String getExPartNumber(){
			return exPartNum;
		}

		public void setExPartNumber(String exPartNum){
			this.exPartNum = exPartNum;
		}	
		
		public String getLocation(){
			return location;
		}

		public void setLocation(String location){
			this.location = location;
		}

		public static int getIDNumber(){
			idNumber = randomNumber.nextInt(1000000);
			return idNumber;
		}

		public void setIDNumber(int idNumber){
			Part.idNumber = idNumber;
		}
		
		public String getQuantityUnit(){
			return qUnit;
		}

		public static void setQuantityUnit(String unit){
			qUnit = unit;
		}
		
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
		updateObservers();
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
		updateObservers();
	}	
	
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
		updateObservers();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		updateObservers();
	}
	
	public static void VersionUpdate(){
		ver++;
	}
	
	public int getVersion(){
		return ver;
	}
	
	public void setVersion(int ver) {
		Part.ver = ver;
	}	
	
	public void registerObserver(PartObserver o) {
		observers.add(o);
	}
	
	public void setFields(String pNum, String pName, String v, int q) {
		setPartNumber(pNum);
		setPartName(pName);
		setVendor(v);
		setQuantity(q);
		updateObservers();
	}
	
	public void updateDeleted() {
		for(PartObserver o : observers) {
			try {
				o.modelDeleted();
			} catch(Exception e) {
				//ignore
			}
		}
	}
	
	private void updateObservers() {
		for(PartObserver o : observers) {
			try {
				o.updateObserver(this);
			} catch(Exception e) {
				//ignore
			}
		}
	}


}
