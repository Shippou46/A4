package Assign3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PartsListView extends JFrame implements InventoryObserver {
	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	private InventoryController invC;
	
	public PartsListView(InventoryController invC, Inventory inv) {
		//buttons on top (north)
		//list view center
		this.invC = invC;
		
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 5));
		JButton button = new JButton("Add Part");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PartView v = new PartView(PartsListView.this.invC, null);
				v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				v.setSize(400, 200);
				v.setLocation(500, 100);
				v.setVisible(true);
			}
		});
		buttonPanel.add(button);
		
		button = new JButton("Delete Part");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index >= 0 && index < PartsListView.this.invC.getNumParts()) {
					Part p = PartsListView.this.invC.getPartByIndex(index);
					PartsListView.this.invC.deletePart(p);
				}
			}
		});
		buttonPanel.add(button);
		
		button = new JButton("Part Detail");
		button.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			 int index = list.getSelectedIndex();
				if(index >= 0 && index < PartsListView.this.invC.getNumParts()) {
					Part p = PartsListView.this.invC.getPartByIndex(index);
					PartDetailView pd = new PartDetailView(p);
					pd.setSize(400, 200);
					pd.setLocation(600, 200);
					pd.setVisible(true);
				}
		}
		});
		buttonPanel.add(button);
		
		button = new JButton("List View");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new InventoryDetail(inv, PartsListView.this.invC);
			}
		});
		buttonPanel.add(button);
		
		button = new JButton("Product Template");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			   ArrayList<Product> product = null;
			ProductView pView = new ProductView(invC, product);
				pView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pView.setSize(400, 300);
				pView.setVisible(true);
			}
		});
		buttonPanel.add(button);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setFixedCellWidth(100);
		//list.setSelectedIndex(0);//init list selected -> first item
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//can also use
				//foxColor = LIST_LABELS[((JList<String>) e.getSource()).getSelectedIndex()];
				//foxColor = (String) ((JList<String>) e.getSource()).getSelectedValue();
				//updateStatusBar();
			}
		});
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("rawtypes")
				JList list = (JList)evt.getSource();
		        if (evt.getClickCount() >= 2) {
		            int index = list.locationToIndex(evt.getPoint());
		            Part p = PartsListView.this.invC.getPartByIndex(index);
					PartView v = new PartView(PartsListView.this.invC, p);
					p.registerObserver(v);
					v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					v.setSize(400, 200);
					v.setLocation(500, 100);
					v.setVisible(true);
		        }
		    }
		});
		this.add(new JScrollPane(list), BorderLayout.CENTER);

		//update the list with the inventory model
		updateObserver(inv);

	}

	@Override
	public void updateObserver(Inventory inv) {
		listModel.clear();
		for(Part p : inv.getParts())
			listModel.addElement(p.getPartName());
		//int i = list.getSelectedIndex();
		//if(inv.getNumParts() > 0)
			//list.setSelectedIndex(0);
	}
}
