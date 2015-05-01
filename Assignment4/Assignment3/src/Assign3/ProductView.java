package Assign3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProductView extends JFrame implements InventoryObserver {

	private JList<String> list;
	private DefaultListModel<String> listModel;
	private InventoryController invC;
	public ArrayList<Product> products;
	public Inventory inv;

	public ProductView(InventoryController invC, ArrayList<Product> p) {
		this.invC = invC;
		this.products = p;
		this.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 4));

		JButton button = new JButton("Add Product");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductPartView v = new ProductPartView(ProductView.this.invC,
						null);
				v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				v.setSize(400, 200);
				v.setLocation(500, 100);
				v.setVisible(true);

			}
		});
		buttonPanel.add(button);

		button = new JButton("Delete Product");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index >= 0 && index < ProductView.this.invC.getNumParts()) {
					Part p = ProductView.this.invC.getPartByIndex(index);
					ProductView.this.invC.deletePart(p);
				}
			}
		});
		buttonPanel.add(button);

		//button = new JButton("Edit Product");
		//buttonPanel.add(button);

		button = new JButton("View Products");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				InventoryDetail InvD = new InventoryDetail(inv, ProductView.this.invC);
			}
		});
		buttonPanel.add(button);

		this.add(buttonPanel, BorderLayout.SOUTH);

		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setFixedCellWidth(100);
		// list.setSelectedIndex(0);//init list selected -> first item
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// can also use
				// foxColor = LIST_LABELS[((JList<String>)
				// e.getSource()).getSelectedIndex()];
				// foxColor = (String) ((JList<String>)
				// e.getSource()).getSelectedValue();
				// updateStatusBar();
			}
		});
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("rawtypes")
				JList list = (JList)evt.getSource();
		        if (evt.getClickCount() >= 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            Part p = ProductView.this.invC.getPartByIndex(index);
					PartView v = new PartView(ProductView.this.invC, p);
					p.registerObserver(v);
					v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					v.setSize(400, 200);
					v.setLocation(500, 100);
					v.setVisible(true);
		        }
		    }
		});
		this.add(new JScrollPane(list), BorderLayout.CENTER);

		updateObserver(inv);

	}

	@Override
	public void updateObserver(Inventory inv) {
		// TODO Auto-generated method stub
		
	}

}
