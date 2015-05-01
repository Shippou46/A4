package Assign3;

import java.util.ArrayList;

public class ProductPartDetail {

	private int productID;
	private int partID;
	private int pdQty;
	private ArrayList<ProductPartDetailObserver> observers;

	public ProductPartDetail(int productID, int partID, int pdQty) {
		if (pdQty < 1)
			throw new IllegalArgumentException(
					"Quantity must be greater than 0");

		this.productID = productID;
		this.setPartID(partID);
		this.setPDQty(pdQty);
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
		updateObservers();
	}

	public int getPartID() {
		return partID;
	}

	public void setPartID(int partID) {
		this.partID = partID;
	}

	public int getPDQty() {
		return pdQty;
	}

	public void setPDQty(int pdQty) {
		this.pdQty = pdQty;
	}

	private void updateObservers() {
		for (ProductPartDetailObserver o : observers) {
			try {
				o.updateObserver(this);
			} catch (Exception e) {
				;
			}
		}
	}
}
