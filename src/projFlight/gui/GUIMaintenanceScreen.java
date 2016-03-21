package projFlight.gui;


import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;

import javax.swing.JSeparator;
import javax.swing.JTextField;

import projFlight.Event.GUIMainEvent;
import projFlight.models.Airport;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUIMaintenanceScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtAddAirport;
	private JComboBox<Airport> cboAirportRemove;
	private JButton btnRemove;
	private JButton btnAddAirport;
	private JComboBox<String> cboCode1;
	private JComboBox<String> cboCode2;
	private JComboBox<String> cboCode3;
	private JButton btnLogout;
	private JTable bookingTable;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public GUIMaintenanceScreen(GUIMainEvent event, List<Airport> airportList) {
		super(new BorderLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		JComponent airportPanel = makeTextPanel(event);
		tabbedPane.addTab("Airports", null, airportPanel, "holder");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel bookingPanel = new JPanel();
		tabbedPane.addTab("Bookings", null, bookingPanel, null);
		bookingPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 494, 361);
		bookingPanel.add(scrollPane);

		scrollPane.setViewportView(bookingTable);

		/*JPanel userPanel = new JPanel();
		tabbedPane.addTab("Users", null, userPanel, null);*/

		JPanel pnlButtons = new JPanel();
		pnlButtons.setBounds(0, 0, 494, 395);
		add(pnlButtons, BorderLayout.SOUTH);
		
		btnLogout = new JButton("Logout");
		pnlButtons.add(btnLogout);
		btnLogout.addActionListener(event);

	}

	protected JComponent makeTextPanel(GUIMainEvent event) {
		JPanel panel = new JPanel(false);
		panel.setLayout(null);

		JLabel lblRemove = new JLabel("Select airport to remove");
		lblRemove.setBounds(60, 56, 177, 14);
		panel.add(lblRemove);

		cboAirportRemove = new JComboBox<Airport>();
		cboAirportRemove.setBounds(60, 81, 221, 33);
		panel.add(cboAirportRemove);

		btnRemove = new JButton("Remove");
		btnRemove.setBounds(344, 81, 89, 33);
		btnRemove.addActionListener(event);
		panel.add(btnRemove);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 150, 475, 2);
		panel.add(separator);

		JLabel lblAirportCode = new JLabel("Airport Code");
		lblAirportCode.setToolTipText("Select Three Letter Airport Code");
		lblAirportCode.setBounds(60, 177, 145, 14);
		panel.add(lblAirportCode);

		txtAddAirport = new JTextField();
		txtAddAirport.setBounds(60, 284, 221, 33);
		panel.add(txtAddAirport);
		txtAddAirport.setColumns(10);

		JLabel lblAirportName = new JLabel("Airport Name");
		lblAirportName.setBounds(60, 258, 66, 14);
		panel.add(lblAirportName);

		btnAddAirport = new JButton("Add Airport");
		btnAddAirport.setBounds(344, 284, 89, 33);
		btnAddAirport.addActionListener(event);
		panel.add(btnAddAirport);

		cboCode1 = new JComboBox<String>();
		cboCode1.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
		cboCode1.setBounds(60, 202, 47, 33);
		panel.add(cboCode1);

		cboCode2 = new JComboBox<String>();
		cboCode2.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
		cboCode2.setBounds(117, 202, 47, 33);
		panel.add(cboCode2);

		cboCode3 = new JComboBox<String>();
		cboCode3.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
		;
		cboCode3.setBounds(174, 202, 53, 33);
		panel.add(cboCode3);

		return panel;
	}
	
	public void populateAirportRemoveBox(List<Airport> airportList) {
		// TODO Auto-generated method stub
		JComboBox<Airport> to = cboAirportRemove;

		if (to != null) {
			to.removeAllItems();
			//to.addItem("--Select airport to remove--");

			for (int i = 0; i < airportList.size(); i++) {
				to.addItem(airportList.get(i));
			}
		}

	}

	

	public String getTxtAddAirport() {
		return txtAddAirport.getText();
	}
	
	public void setTxtAddAirport(String str) {
		txtAddAirport.setText(str);
	}

	public String getCboAirportRemove() {
		return cboAirportRemove.getSelectedItem().toString();
	}

	public String getCboCode1() {
		return cboCode1.getSelectedItem().toString();
	}

	public void setCboCode1(int i) {
		this.cboCode1.setSelectedIndex(i);;
	}

	public String getCboCode2() {
		return cboCode2.getSelectedItem().toString();
	}

	public void setCboCode2(int i) {
		this.cboCode2.setSelectedIndex(i);
	}

	public String getCboCode3() {
		return cboCode3.getSelectedItem().toString();
	}

	public void setCboCode3(int i) {
		this.cboCode3.setSelectedIndex(i);
	}

	public JTable getBookingTable() {
		return bookingTable;
	}

	public void setBookingTable(JTable bookingTable) {
		this.bookingTable = bookingTable;
	}
	
	public boolean isSourceBtnRemove(Object source) {
		return btnRemove == source;
	}
	public boolean isSourceBtnAddAirport(Object source) {
		return btnAddAirport == source;
	}
	public boolean isSourceBtnLogout(Object source) {
		return btnLogout == source;
	}
	
	public void setScrollPane(JTable table) {
		scrollPane.setViewportView(table);
	}
}
