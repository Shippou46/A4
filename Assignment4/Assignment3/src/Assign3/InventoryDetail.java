package Assign3;

import javax.swing.JFrame;

public class InventoryDetail extends JFrame {
	private static final long serialVersionUID = 1L;
	private Part part;
	private int nParts;
	private int x;
	private int y;

	public InventoryDetail(Inventory inv, InventoryController invC) {

		nParts = inv.getNumParts();
		for (int i = 0; i < nParts; i++) {
			part = invC.getPartByIndex(i);
			y = i * 10;
			x = i * 25;

			PartDetailView PD = new PartDetailView(part);
			PD.setSize(400, 200);
			PD.setLocation(600 + x, 200 + y);
			PD.setVisible(true);

		}

	}

}
