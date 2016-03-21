package projFlight.gui;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JRadioButton;

import projFlight.Event.GUIMainEvent;
import projFlight.models.Airport;
import projFlight.models.User;

import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class GUICustomerScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */


	private JRadioButton rdbtnOneWay;
	private JRadioButton rdbtnReturnsecondLeg;
	private ButtonGroup group;
	private JComboBox<Airport> cboDeptLeg1;
	private JComboBox<Airport> cboDestLeg1;
	private JComboBox<String> cboSeatType;
	private JLabel lblDeptLeg2;
	private JLabel lblDestLeg2;
	private JComboBox<Airport> cboDeptLeg2;
	private JComboBox<Airport> cboDestLeg2;
	private JCheckBox chkbxInsurance;
	private JButton btnBookFlights;
	private JButton btnClear;
	private JButton btnLogout;
	private String defaultItem = "--Select Airport--";
	private JButton btnHelp;


	public GUICustomerScreen(GUIMainEvent event, User user) {

		setLayout(null);

		rdbtnOneWay = new JRadioButton("One Way");
		rdbtnOneWay.setSelected(true);
		rdbtnOneWay.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		rdbtnOneWay.setBounds(88, 113, 109, 23);
		rdbtnOneWay.addActionListener(event);
		add(rdbtnOneWay);
		// login.btnOk.addActionListener(event);

		rdbtnReturnsecondLeg = new JRadioButton("Return/Second Leg");
		rdbtnReturnsecondLeg.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		rdbtnReturnsecondLeg.setBounds(272, 113, 143, 23);
		rdbtnReturnsecondLeg.addActionListener(event);
		add(rdbtnReturnsecondLeg);

		group = new ButtonGroup();
		group.add(rdbtnOneWay);
		group.add(rdbtnReturnsecondLeg);

		JLabel lblDeparture = new JLabel("Departure ");
		lblDeparture.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblDeparture.setBounds(40, 146, 67, 14);
		add(lblDeparture);

		JLabel lblArrival = new JLabel("Destination");
		lblArrival.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblArrival.setBounds(194, 146, 67, 14);
		add(lblArrival);

		JLabel lblSeatType = new JLabel("Seat Type");
		lblSeatType.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblSeatType.setBounds(343, 146, 67, 14);
		add(lblSeatType);

		cboDeptLeg1 = new JComboBox<Airport>();
		cboDeptLeg1.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		cboDeptLeg1.setMaximumRowCount(10);
		cboDeptLeg1.setBounds(40, 171, 132, 20);
/*		cboDeptLeg1.removeAllItems();
		cboDeptLeg1.addItem(defaultItem);
		// TODO move to event
		for (int i = 0; i < airportList.size(); i++) {
			cboDeptLeg1.addItem(airportList.get(i));
		}*/
		cboDeptLeg1.addActionListener(event);
		add(cboDeptLeg1);

		cboDestLeg1 = new JComboBox<Airport>();
		cboDestLeg1.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		cboDestLeg1.setMaximumRowCount(10);
		cboDestLeg1.setBounds(194, 171, 126, 20);
		/*cboDestLeg1.removeAllItems();
		// TODO move to event?
		cboDestLeg1.addItem(defaultItem);*/
		cboDestLeg1.addActionListener(event);
		add(cboDestLeg1);

		cboSeatType = new JComboBox<String>();
		cboSeatType.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		cboSeatType.setBounds(343, 171, 115, 20);
		// TODO move to event?
		cboSeatType.addItem(defaultItem);
		// TODO make object?
		cboSeatType.addItem("Economy");
		cboSeatType.addItem("Business");
		cboSeatType.addItem("First Class");
		cboSeatType.addActionListener(event);
		add(cboSeatType);

		lblDeptLeg2 = new JLabel("Departure ");
		lblDeptLeg2.setEnabled(false);
		lblDeptLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblDeptLeg2.setBounds(40, 202, 67, 14);
		add(lblDeptLeg2);

		lblDestLeg2 = new JLabel("Destination");
		lblDestLeg2.setEnabled(false);
		lblDestLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblDestLeg2.setBounds(194, 202, 67, 14);
		add(lblDestLeg2);

		cboDeptLeg2 = new JComboBox<Airport>();
		cboDeptLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		cboDeptLeg2.setMaximumRowCount(10);
		cboDeptLeg2.setEnabled(false);
		cboDeptLeg2.setBounds(40, 227, 132, 20);
		cboDeptLeg2.removeAllItems();
		// TODO move to event?
		//cboDeptLeg2.addItem(defaultItem);
		cboDeptLeg2.addActionListener(event);
		add(cboDeptLeg2);

		cboDestLeg2 = new JComboBox<Airport>();
		cboDestLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		cboDestLeg2.setMaximumRowCount(10);
		cboDestLeg2.setEnabled(false);
		cboDestLeg2.setBounds(194, 227, 126, 20);
		cboDestLeg2.removeAllItems();
		// TODO move to event?
		//cboDestLeg2.addItem(defaultItem);
		cboDestLeg2.addActionListener(event);
		add(cboDestLeg2);

		chkbxInsurance = new JCheckBox("Would you like to purchase insurance?");
		chkbxInsurance.setSelected(true);
		chkbxInsurance.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		chkbxInsurance.setBounds(50, 254, 420, 23);
		chkbxInsurance.addActionListener(event);
		add(chkbxInsurance);

		btnBookFlights = new JButton("Book Flight(s)");
		btnBookFlights.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		btnBookFlights.setBounds(50, 334, 126, 46);
		btnBookFlights.addActionListener(event);
		add(btnBookFlights);

		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		btnClear.setBounds(194, 334, 132, 46);
		btnClear.addActionListener(event);
		add(btnClear);

		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		btnLogout.setBounds(343, 334, 115, 46);
		btnLogout.addActionListener(event);
		add(btnLogout);
		
		String fName = user.getFirstName();
		String lName = user.getLastName();
		JLabel lblHello = new JLabel("<html>Hello,<br>" + fName + " " + lName + "</html>");
		lblHello.setBounds(380, 22, 89, 46);
		add(lblHello);
		
		JComboBox<String> cboSeatLeg2 = new JComboBox<String>();
		cboSeatLeg2.setEnabled(false);
		cboSeatLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		cboSeatLeg2.setBounds(343, 227, 115, 20);
		add(cboSeatLeg2);
		
		JLabel label = new JLabel("Seat Type");
		label.setEnabled(false);
		label.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(343, 202, 67, 14);
		add(label);
		
		btnHelp = new JButton("Help");
		btnHelp.setBounds(398, 300, 60, 23);
		btnHelp.addActionListener(event);
		add(btnHelp);

	}

	/**
	 * getter and setter methods
	 */

	// Radio button
	public boolean getRdbtnOneWaySelected() {
		return rdbtnOneWay.isSelected();
	}

	public void setRdbtnOneWaySelected() {
		rdbtnOneWay.setSelected(true);
	}
	
	// departure Leg 1 
	public void setCboDeptLeg1(List<Airport> airportList) {
		populateComboBoxDeptLeg1(airportList);
	}
	
	public String getCboDeptLeg1Selected() {
		return cboDeptLeg1.getSelectedItem().toString();
	}

	public int getCboDeptLeg1Index() {
		return cboDeptLeg1.getSelectedIndex();
	}
	
	// Destination leg 1
	public int getCboDestLeg1Index() {
		return cboDestLeg1.getSelectedIndex();
	}

	public void setCboDestLeg1(List<Airport> airportList) {
		populateComboBoxDestLeg1(airportList);
	}

	public String getCboDestLeg1Selected() {
		return cboDestLeg1.getSelectedItem().toString();
	}
	
	// seat type
	public void setCboSeatType(List<String> airportList) {
		;
	}

	public String getCboSeatType() {
		return cboSeatType.getSelectedItem().toString();
	}

	// Departure leg 2
	public void setCboDeptLeg2(List<Airport> airportList) {
		populateComboBoxDeptLeg2(airportList);
	}

	public String getCboDeptLeg2Selected() {
		return cboDeptLeg2.getSelectedItem().toString();
	}
	
	public int getCboDeptLeg2Index() {
		return cboDeptLeg2.getSelectedIndex();
	}
	
	// destination leg 2
	public void setCboDestLeg2(List<Airport> airportList) {
		populateComboBoxDestLeg2(airportList);
	}

	public String getCboDestLeg2Selected() {
		return cboDestLeg2.getSelectedItem().toString();
	}
	
	public int getCboDestLeg2Index() {
		return cboDestLeg2.getSelectedIndex();
	}
	
	// insurance
	public void setChkbxInsurance(boolean b) {
		chkbxInsurance.setSelected(b);
	}

	public boolean getChkbxInsurance() {
		return chkbxInsurance.isSelected();
	}

	/**
	 * Methods to check if object is source
	 */
	public boolean isSourceRdbtnOneWay(Object source) {
		return rdbtnOneWay == source;
	}

	public boolean isSourceRdbtnReturnSecondLeg(Object source) {
		return rdbtnReturnsecondLeg == source;
	}

	public boolean isSourceCboDeptLeg1(Object source) {
		return cboDeptLeg1 == source;
	}

	public boolean isSourceCboDestLeg1(Object source) {
		return cboDestLeg1 == source;
	}

	public boolean isSourceCboSeatType(Object source) {
		return cboSeatType == source;
	}

	public boolean isSourceCboDeptLeg2(Object source) {
		return cboDeptLeg2 == source;
	}
	
	public boolean isSourceCboDestLeg2(Object source) {
		return cboDestLeg2 == source;
	}

	public boolean isSourceChkbxInsurance(Object source) {
		return chkbxInsurance == source;
	}

	public boolean isSourceBtnBookFlights(Object source) {
		return btnBookFlights == source;
	}

	public boolean isSourceBtnClear(Object source) {
		return btnClear == source;
	}

	public boolean isSourceBtnLogout(Object source) {
		return btnLogout == source;
	}
	
	public boolean isSourceBtnHelp(Object source) {
		return btnHelp == source;
	}

	/**
	 * Enable/disable leg 2 combo boxes
	 * 
	 * @param enable
	 */

	public void enableLeg2(boolean enable) {
		cboDeptLeg2.setEnabled(enable);
		cboDestLeg2.setEnabled(enable);
		
		cboDeptLeg2.setSelectedIndex(-1);
		cboDestLeg2.setSelectedIndex(-1);
		
	}

	public void clearAllCustomer() {

		setRdbtnOneWaySelected();
		setChkbxInsurance(true);
		resetComboBoxes();
		enableLeg2(false);

	}

	/**
	 * reset all combo boxes
	 */
	public void resetComboBoxes() {
		cboDeptLeg1.setSelectedIndex(0);
		cboDestLeg1.setSelectedIndex(0);
		cboSeatType.setSelectedIndex(0);
				
		enableLeg2(false);
	}

	// TODO copy code from save, replace these methods with updateCBO methods
	// from GUI mainevent
	/**
	 * populate airport combo boxes
	 * 
	 * @param airportList
	 */
	private void populateComboBoxDeptLeg1(List<Airport> airportList) {

		if (cboDeptLeg1 != null) {
			//cboDeptLeg1.removeAllItems();
			//cboDeptLeg1.addItem(defaultItem);
			for (int i = 0; i < airportList.size(); i++) {
				cboDeptLeg1.addItem(airportList.get(i));
			}
		}

	}

	private void populateComboBoxDestLeg1(List<Airport> airportList) {

		if (cboDestLeg1 != null && cboDeptLeg1.getSelectedItem() != null) {
			cboDestLeg1.removeAllItems();
			//cboDestLeg1.addItem(defaultItem);
			//System.out.println(airportList.get(0));
			for (int i = 0; i < airportList.size(); i++) {
				if (airportList.get(i).equals(cboDeptLeg1.getSelectedItem())) {
					continue;
				} else {
					cboDestLeg1.addItem(airportList.get(i));
				}
			}

		}
	}

	private void populateComboBoxDeptLeg2(List<Airport> airportList) {

		if (cboDeptLeg2 != null) {
			cboDeptLeg2.removeAllItems();
			//cboDeptLeg2.addItem(defaultItem);
			//boolean one = !getRdbtnOneWaySelected();
			//boolean two = cboDeptLeg1 != null;
			//boolean three = cboDestLeg1.getSelectedItem() != null;
			//boolean four = cboDestLeg1.getSelectedItem().toString().equals(defaultItem);
			
			if (!getRdbtnOneWaySelected() && cboDeptLeg1 != null && cboDestLeg1.getSelectedItem() != null) {
				cboDeptLeg2.addItem((Airport)cboDestLeg1.getSelectedItem());
			} 
		}
	}

	private void populateComboBoxDestLeg2(List<Airport> airportList) {

		if (cboDestLeg2 != null) {
			cboDestLeg2.removeAllItems();
			//cboDestLeg2.addItem(defaultItem);			

			for (int i = 0; i < airportList.size(); i++) {
				
				if (!rdbtnOneWay.isSelected() && cboDeptLeg2.getSelectedItem() != null && !airportList.get(i).equals(cboDeptLeg2.getSelectedItem())) {
					cboDestLeg2.addItem(airportList.get(i));
				}
			}
		}
	}
}
