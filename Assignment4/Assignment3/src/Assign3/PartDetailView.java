package Assign3;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PartDetailView extends JFrame {

	private Part part;

	private JTextField Num;
	private JTextField Name;
	private JTextField Vend;
	private JTextField Qty;
	private JTextField ID;
	private JTextField ExNum;
	private JTextField Loc;

	public PartDetailView(Part p) {
		this.part = p;

		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2));

		panel.add(new JLabel("Part #"));
		Num = new JTextField();
		panel.add(Num);

		panel.add(new JLabel("Part Name"));
		Name = new JTextField();
		panel.add(Name);

		panel.add(new JLabel("Vendor"));
		Vend = new JTextField();
		panel.add(Vend);

		panel.add(new JLabel("Quantity"));
		Qty = new JTextField();
		panel.add(Qty);

		panel.add(new JLabel("ID"));
		ID = new JTextField();
		panel.add(ID);

		panel.add(new JLabel("ExPart #"));
		ExNum = new JTextField();
		panel.add(ExNum);

		panel.add(new JLabel("Location"));
		Loc = new JTextField();
		panel.add(Loc);

		this.add(panel, BorderLayout.CENTER);

		if (p != null) {
			Num.setText(part.getPartNumber());
			Name.setText(part.getPartName());
			Vend.setText(part.getVendor());
			Qty.setText(Integer.toString(part.getQuantity()) + " "
					+ part.getQuantityUnit());
			ID.setText(Integer.toString(Part.getIDNumber()));
			ExNum.setText(part.getExPartNumber());
			Loc.setText(part.getLocation());
			this.setTitle("Detail View " + p.getPartName());
		} else
			this.setTitle(part.getPartName());
	}

}

