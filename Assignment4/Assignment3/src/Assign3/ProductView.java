package Assign3;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;

public class ProductView extends JFrame implements ProductObserver {

	private JList<String>list;
	private DefaultListModel<String> listModel;
	//make Product Controller
	private InventoryController invC;
	
	public ProductView(InventoryController invC, Inventory inv){
		this.invC = invC;
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4,4));
	
		JButton button = new JButton("Add Product");
		buttonPanel.add(button);
		
		button = new JButton("Delete Product");
		buttonPanel.add(button);
		
		button = new JButton("Edit Product");
		buttonPanel.add(button);
		
		button = new JButton("View Products");
		buttonPanel.add(button);
		
		this.add(buttonPanel,BorderLayout.CENTER);

		}
	
	@Override
	public void modelDeleted() {
		this.setVisible(false);
	}

	@Override
	public void updateObserver(Product product) {
		if(product != null){		
			;
		}
	}

}
