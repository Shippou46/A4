package Assign3;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductPartDetailView extends JFrame {

	private Part part;

	private JTextField Qty;
	private JTextField prodID;
	private JTextField partID;

	public ProductPartDetailView(Part p) {
		this.part = p;

		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		panel.add(new JLabel("Quantity"));
		Qty = new JTextField();
		panel.add(Qty);

		panel.add(new JLabel("PartID"));
		partID = new JTextField();
		panel.add(partID);

		panel.add(new JLabel("ProdID"));
		prodID = new JTextField();
		panel.add(prodID);

		this.add(panel, BorderLayout.CENTER);

		if (p != null) {
			Qty.setText(Integer.toString(part.getQuantity()) + " "
					+ part.getQuantityUnit());
			partID.setText(Integer.toString(Part.getIDNumber()));
			prodID.setText(Integer.toString(Product.getIDNumber()));
			this.setTitle("Detail View " + p.getPartName());
		} else
			this.setTitle(part.getPartName());
	}

}

