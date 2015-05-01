package Assign3;

import java.util.ArrayList;
import java.util.Random;

public class Product {

	private static int id;
	private String pNum, pdescription;
	static Random randomNumber = new Random();
	private ArrayList<ProductObserver> observers;
	private ArrayList<ProductPartDetail> pdetail;

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
