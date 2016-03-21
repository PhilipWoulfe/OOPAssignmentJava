/**
* <h1>GUIMaintenanceScreen</h1>
* <p>GUIMaintenanceScreen handles the maintenance aspect of the flight project</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/

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
	private JButton btnHelp;

	/**
	 * Create the panel
	 * @param event Used for adding event listeners
	 * @param airportList Used to store and recall airports 
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


		JPanel pnlButtons = new JPanel();
		pnlButtons.setBounds(0, 0, 494, 395);
		add(pnlButtons, BorderLayout.SOUTH);
		
		btnHelp = new JButton("Help");
		btnHelp.addActionListener(event);
		pnlButtons.add(btnHelp);
		
		btnLogout = new JButton("Logout");
		pnlButtons.add(btnLogout);
		btnLogout.addActionListener(event);

	}

	/**
	 * Create a text pane
	 * @param event For action listeners
	 * @return Returns a text panel
	 */
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
		btnRemove.setBounds(325, 81, 108, 33);
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
		btnAddAirport.setBounds(325, 284, 108, 33);
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
	
	/**
	 * populate the remove box from Airport list
	 * @param airportList For populating combo box
	 */
	public void populateAirportRemoveBox(List<Airport> airportList) {
		
		JComboBox<Airport> to = cboAirportRemove;
		
		// if to exists
		if (to != null) {
			
			to.removeAllItems();
			
			// cycle through array list
			for (int i = 0; i < airportList.size(); i++) {
				
				// add each item
				to.addItem(airportList.get(i));
			}
		}
	}

	/**
	 * Get text from add airport text box
	 * @return Returns add airport text
	 */
	public String getTxtAddAirport() {
		return txtAddAirport.getText();
	}
	
	/**
	 * Set text of add airport text box
	 * @param str set text from string
	 */
	public void setTxtAddAirport(String str) {
		txtAddAirport.setText(str);
	}

	/**
	 * Get selected item from airport remove combo box
	 * @return returns string of selected item
	 */
	public String getCboAirportRemove() {
		return cboAirportRemove.getSelectedItem().toString();
	}
	
	/**
	 * Get selected index from remove combo box
	 * @return returns selected index
	 */
	public int getCboAirportRemoveIndex() {
		return cboAirportRemove.getSelectedIndex();
	}

	/**
	 * Returns selected item from Code 1
	 * @return return single letter
	 */
	public String getCboCode1() {
		return cboCode1.getSelectedItem().toString();
	}

	/**
	 * Set selected index for combo box
	 * @param i sets index from input
	 */
	public void setCboCode1(int i) {
		this.cboCode1.setSelectedIndex(i);;
	}

	/**
	 * Returns selected item from Code 2
	 * @return return single letter
	 */
	public String getCboCode2() {
		return cboCode2.getSelectedItem().toString();
	}
	
	/**
	 * Set selected index for combo box
	 * @param i sets index from input
	 */
	public void setCboCode2(int i) {
		this.cboCode2.setSelectedIndex(i);
	}

	/**
	 * Returns selected item from Code 3
	 * @return return single letter
	 */
	public String getCboCode3() {
		return cboCode3.getSelectedItem().toString();
	}

	/**
	 * Set selected index for combo box
	 * @param i sets index from input
	 */
	public void setCboCode3(int i) {
		this.cboCode3.setSelectedIndex(i);
	}

	/**
	 * Set Booking Table from input
	 * @param bookingTable sets table from input
	 */
	public void setBookingTable(JTable bookingTable) {
		this.bookingTable = bookingTable;
	}
	
	/**
	 * Is this button the source
	 * @param source compares with this button
	 * @return whether this is the source
	 */
	public boolean isSourceBtnRemove(Object source) {
		return btnRemove == source;
	}
	
	/**
	 * Is this button the source
	 * @param source compares with this button
	 * @return whether this is the source
	 */
	public boolean isSourceBtnAddAirport(Object source) {
		return btnAddAirport == source;
	}
	
	/**
	 * Is this button the source
	 * @param source compares with this button
	 * @return whether this is the source
	 */
	public boolean isSourceBtnLogout(Object source) {
		return btnLogout == source;
	}
	
	/**
	 * Is this button the source
	 * @param source compares with this button
	 * @return whether this is the source
	 */
	public boolean isSourceBtnHelp(Object source) {
		return btnHelp == source;
	}
	
	/**
	 * Sets Scroll Pane
	 * @param table Sets scroll pane with table
	 */
	public void setScrollPane(JTable table) {
		scrollPane.setViewportView(table);
	}
}
