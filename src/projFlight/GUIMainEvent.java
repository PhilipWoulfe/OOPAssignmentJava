/**
 * 
 */
package projFlight;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import projFlight.gui.*;


/**
 * @author Phil
 *
 */
public class GUIMainEvent implements ActionListener {

	List<String> airportList = new ArrayList<String>();;
	// components
	JFrame mainGUI;
	
	GUIMain gui;
	GUIConfirmScreen confirmGUI;
	private GUILoginScreen login;
	private GUICustomerScreen customer;
	
	private String usernameInput;
	private char[] passwordInput;
	
	private String fName = "Philip";
	private String lName = "Woulfe";


	// confirm screen stuff
	static JButton btnConfirmConfirm;
	static JButton btnConfirmPrint;
	static JButton btnConfirmExit;

	static JTextField txtConfirmName;
	static JTextField txtConfirmSeat;
	static JTextField txtConfirmLeg1;
	static JTextField txtConfirmLeg2;
	static JTextField txtConfirmInsurance;
	static JTextField txtConfirmBookRef;

	// maintenence screen stuff
	GUIMaintenanceScreen maintenanceGUI;

	static int bookingRef;
	static int userID;

	// sql stuff
	private static String dbURL = "jdbc:derby:C:\\Apache\\db-derby-10.12.1.1-bin\\bin\\FlightDatabase;create=true";
	/* private static String tableUsers = "users"; */
	private static String tablePasswords = "passwords";
	/*
	 * private static String tableAirports = "airports"; private static String
	 * tableFlights = "flights";
	 */
	// jdbc Connection
	private static Connection conn = null;
	private static Statement stmt = null;

	public GUIMainEvent(GUIMain in) {
		gui = in;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();

		// gui stuff
		mainGUI = gui.frame;


		confirmGUI = gui.confirmScreen;
		maintenanceGUI = gui.maintenanceScreen;

		// login screen stuff
		
		
		// customer screen stuff


		// confirm screen stuff
		btnConfirmConfirm = gui.confirmScreen.btnConfirm;
		btnConfirmPrint = gui.confirmScreen.btnPrint;
		btnConfirmExit = gui.confirmScreen.btnExit;

		txtConfirmName = gui.confirmScreen.txtName;
		txtConfirmSeat = gui.confirmScreen.txtSeat;
		txtConfirmLeg1 = gui.confirmScreen.txtLeg1;
		txtConfirmLeg2 = gui.confirmScreen.txtLeg2;
		txtConfirmInsurance = gui.confirmScreen.txtInsurance;
		txtConfirmBookRef = gui.confirmScreen.txtBookRef;

		// maintenance screen stuff

		// set OK to be main button

		if (login.btnLoginIsSource(source)) {
			String usernameInput = login.getUsername();
			char[] passwordInput = login.getPassword();

			if (usernameInput.equals("")) {
				login.setUsernameError("User name cannot be blank");
			}

			if (passwordInput.length < 1) {
				login.setPasswordError();
			} else {

				if (validatePassword(usernameInput, passwordInput)) {
					
					populateAirportList();
					
					if (isAdmin(usernameInput)) {

						// go to admin screen
						login.clearPassword();
						gui.changeScreens(mainGUI, login, maintenanceGUI);
						populateAirportRemoveBox();
						populateBookingTable();
						
						

					} else {
						login.createCustomerGUI(this, fName, lName);
						login.clearPassword();
						//System.out.println(airportList.get(0));
						customer.setCboDeptLeg1(airportList);
						customer.setCboDestLeg1(airportList);
						gui.changeScreens(mainGUI, login, customer);
						gui.addLogo(customer);
						
						getUserID(usernameInput);
						

					}
					
					
					login.clearUsernameError();
					login.clearPasswordError();
					login.clearPassword();

				} else {
					// warn bad username password
					login.setUsernameError("Invalid username or password");
				}
				
			}

		} else if (login.btnCloseIsSource(source)) {
			System.exit(0); 
		} else if (customer.isSourceRdbtnOneWay(source)) {
			customer.enableLeg2(false);	
		}else if (customer.isSourceRdbtnReturnSecondLeg(source)){
			customer.enableLeg2(true);	
			customer.setCboDeptLeg2(airportList);
			customer.setCboDestLeg2(airportList);
		} else if (customer.isSourceCboDeptLeg1(source)) {
			customer.setCboDestLeg1(airportList);
		} else if (customer.isSourceCboDestLeg1(source)) {
			if (!customer.getRdbtnOneWaySelected()) {
				customer.setCboDeptLeg2(airportList);
			}
		} else if (customer.isSourceCboDeptLeg2(source)) {
			if (!customer.getRdbtnOneWaySelected()) {
				customer.setCboDestLeg2(airportList);
			}
		} else if (customer.isSourceBtnBookFlights(source)) {
			bookFlights(usernameInput);
		} else if (customer.isSourceBtnClear(source)) {
			customer.clearAllCustomer();
		} else if (customer.isSourceBtnLogout(source)) {
			logout();
		} else if (source == gui.confirmScreen.btnConfirm) { //TODO from here
			gui.confirmScreen.btnConfirm.setEnabled(false);
			writeToDB();
		} else if (source == gui.confirmScreen.btnPrint) {
			// print
		} else if (source == gui.confirmScreen.btnExit) {

			gui.confirmScreen.btnConfirm.setEnabled(true);
			gui.changeScreens(mainGUI, confirmGUI, customer);
			gui.addLogo(customer);
			customer.clearAllCustomer();

		} else if (source == maintenanceGUI.btnAddAirport) {
			String code = maintenanceGUI.cboCode1.getSelectedItem().toString() + ""
					+ maintenanceGUI.cboCode2.getSelectedItem().toString() + ""
					+ maintenanceGUI.cboCode3.getSelectedItem().toString();
			String name = maintenanceGUI.txtAddAirport.getText();

			addAirportToDB(code, name);

			Collections.sort(airportList);
		} else if (source == maintenanceGUI.btnRemove) {
			// remove airport from db
			removeAirportFromDB(maintenanceGUI.cboAirportRemove.getSelectedItem().toString());
		} else if (source == maintenanceGUI.btnLogout) {
			gui.changeScreens(mainGUI, maintenanceGUI, login);
			gui.addLogo(login);
			login.clearPassword();
		}
	}

	private void logout() {
		customer.clearAllCustomer();
		gui.changeScreens(mainGUI, customer, login);
		gui.addLogo(login);
	}


	private void bookFlights(String username) {
		// TODO Auto-generated method stub
		int customerDeptLeg1Index = customer.getCboDeptLeg1Index();
		int customerDestLeg1Index = customer.getCboDeptLeg1Index();
		int customerDeptLeg2Index = customer.getCboDeptLeg1Index();
		int customerDestLeg2Index = customer.getCboDeptLeg1Index();
		int customerSeatTypeIndex = customer.getCboDeptLeg1Index();

		@SuppressWarnings("unused")
		String deptLeg1Airport = "";
		@SuppressWarnings("unused")
		String destLeg1Airport = "";
		@SuppressWarnings("unused")
		String deptLeg2Airport = "";
		@SuppressWarnings("unused")
		String destLeg2Airport = "";
		@SuppressWarnings("unused")
		String seatType = "";

		if (customer.getRdbtnOneWaySelected()) {
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			} else {

				gui.changeScreens(mainGUI, customer, confirmGUI);
				gui.addLogo(confirmGUI);
				confirmGUI.lblLeg2.setEnabled(false);
				confirmGUI.txtLeg2.setEnabled(false);
				fillInConfirm(username);

			}
		} else {
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerDeptLeg2Index == 0
					|| customerDestLeg2Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			} else {
				deptLeg1Airport = customer.getCboDeptLeg1Selected();
				destLeg1Airport = customer.getCboDestLeg2Selected();
				deptLeg2Airport = customer.getCboDeptLeg1Selected();
				destLeg2Airport = customer.getCboDestLeg2Selected();
				seatType = customer.getCboSeatType();

				gui.changeScreens(mainGUI, customer, confirmGUI);
				gui.addLogo(confirmGUI);
				confirmGUI.lblLeg2.setEnabled(true);
				confirmGUI.txtLeg2.setEnabled(true);
				fillInConfirm(username);
			}
		}

	}

	

	private void populateAirportRemoveBox() {
		// TODO Auto-generated method stub
		JComboBox<String> to = maintenanceGUI.cboAirportRemove;

		if (to != null) {
			to.removeAllItems();
			to.addItem("--Select airport to remove--");

			for (int i = 0; i < airportList.size(); i++) {
				to.addItem(airportList.get(i));
			}
		}

	}


	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(dbURL);
			System.gc();
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

	private static boolean validatePassword(String usernameInput, char[] passwordInput) {
		createConnection();
		boolean result = false;

		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select password from " + tablePasswords
					+ " where UPPER(userName) = UPPER('" + usernameInput + "')");

			while (results.next()) {
				String actualPassword = results.getString(1);
				result = Arrays.equals(passwordInput, actualPassword.toCharArray());

			}
			results.close();
			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		shutdown();
		return result;

	}

	private void populateAirportList() {
		// TODO Auto-generated method stub

		airportList.clear();
		createConnection();

		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select name from airports");

			while (results.next()) {
				String airport = results.getString(1);
				if (!airport.equals("")) {
					airportList.add(airport);
				}

			}

			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
		Collections.sort(airportList);
		
		/*for (int i = 0; i < airportList.size(); i++) {
			System.out.println(airportList.get(i));
		}*/
	}

	private void writeToDB() {
				
		String deptLeg1Code = getCodeForAirport(customer.getCboDeptLeg1Selected());
		String destLeg1Code = getCodeForAirport(customer.getCboDestLeg2Selected());
		String deptLeg2Code = getCodeForAirport(customer.getCboDeptLeg1Selected());
		String destLeg2Code = getCodeForAirport(customer.getCboDestLeg2Selected());

		createConnection();
		try {
			stmt = conn.createStatement();

			PreparedStatement statement = conn.prepareStatement(
					"INSERT INTO flights (UserID, DeptLeg1AirCode, DestLeg1AirCode, DeptLeg2AirCode, DestLeg2AirCode, Insurance) values (?, ?, ?, ?, ?, ?)");

			statement.setInt(1, userID);
			statement.setString(2, deptLeg1Code);
			statement.setString(3, destLeg1Code);

			if (deptLeg2Code != null) {
				statement.setString(4, deptLeg2Code);
			} else {
				statement.setString(4, "");
			}

			if (destLeg2Code != null) {
				statement.setString(5, destLeg2Code);
			} else {
				statement.setString(5, "");
			}

			statement.setBoolean(6, customer.getChkbxInsurance());
			statement.execute();

			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	private void fillInConfirm(String username) {
		createConnection();
		getNames(username);
		if (customer.getRdbtnOneWaySelected()) {
			getLeg1Flights();
			txtConfirmLeg2.setEnabled(false);

		} else {
			getLeg1Flights();
			getLeg2Flights();
			txtConfirmLeg2.setEnabled(true);
		}

		getSeat();
		getInsurance();
		getRef();

		shutdown();

	}

	private void removeAirportFromDB(String airport) {
		createConnection();
		try {
			PreparedStatement statement = conn.prepareStatement("delete from airports where name = ?");
			statement.setString(1, airport);
			statement.execute();

			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		shutdown();
		populateAirportList();
		populateAirportRemoveBox();
		customer.setCboDeptLeg1(airportList);

	}

	private void addAirportToDB(String code, String name) {
		createConnection();
		try {

			PreparedStatement statement = conn.prepareStatement("insert into airports values ('" + code.charAt(0) + ""
					+ code.charAt(1) + "" + code.charAt(2) + "', ?)");
			statement.setString(1, name);
			statement.execute();

			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		shutdown();
		populateAirportList();
		populateAirportRemoveBox();
		customer.setCboDeptLeg1(airportList);

	}

	private void getUserID(String username) {
		createConnection();
		try {
			String selectSQL = "select userID from passwords where username = ?";
			
			PreparedStatement statement = conn.prepareStatement(selectSQL);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();
			
			while (results.next()) {

				userID = results.getInt(1);
			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		shutdown();
	}

	private static void getNextBookingRef() {
		createConnection();

		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select count(*) from flights");

			while (results.next()) {
				bookingRef = results.getInt(1) + 1;
			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	private void populateBookingTable() {
		createConnection();

		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(
					"select f.BookingRef, u.firstName, u.LastName, f.deptLeg1AirCode, f.DestLeg1AirCode, f.deptLeg2Aircode, f.DestLeg2AirCode, f.insurance from flights f join users u on f.userid = u.userid order by bookingRef asc");

			maintenanceGUI.bookingTable = new JTable(buildTableModel(results));
			maintenanceGUI.scrollPane.setViewportView(maintenanceGUI.bookingTable);

			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();

	}

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);

	}

	private void getRef() {
		getNextBookingRef();
		txtConfirmBookRef.setText(bookingRef + "");
	}

	private void getInsurance() {
		if (customer.getChkbxInsurance()) {
			txtConfirmInsurance.setText("Yes");
		} else {
			txtConfirmInsurance.setText("No");
		}
	}

	private void getSeat() {
		txtConfirmSeat.setText(customer.getCboSeatType());
	}

	private void getLeg2Flights() {
		String deptLeg2 = customer.getCboDeptLeg2Selected();
		String destLeg2 = customer.getCboDestLeg2Selected();

		txtConfirmLeg2.setText(getCodeForAirport(deptLeg2) + " to " + getCodeForAirport(destLeg2));
	}

	private void getLeg1Flights() {

		String deptLeg1 = customer.getCboDeptLeg1Selected();
		String destLeg1 = customer.getCboDestLeg1Selected();

		txtConfirmLeg1.setText(getCodeForAirport(deptLeg1) + " to " + getCodeForAirport(destLeg1));

	}

	private static String getCodeForAirport(String name) {
		createConnection();

		String result = "";

		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select aircode from airports where name = '" + name + "'");

			while (results.next()) {

				result = results.getString(1);
			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		shutdown();
		return result;
	}

	private static void getNames(String username) {

		createConnection();

		try {
			String selectSQL = "select firstName, lastName from users u join passwords p on u.userid = p.userid where username = ?";
			
			PreparedStatement statement = conn.prepareStatement(selectSQL);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();

			while (results.next()) {

				txtConfirmName.setText(results.getString(1) + " " + results.getString(2));

			}
			results.close();
			stmt.close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		shutdown();
	}

	private static boolean isAdmin(String username) {
		createConnection();

		boolean result = false;

		try {
			String selectSQL = "select isAdmin from " + tablePasswords + " where userName = ?";
			
			PreparedStatement statement = conn.prepareStatement(selectSQL);
			statement.setString(1, username);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				result = results.getBoolean(1);
			}
			results.close();
			stmt.close();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		shutdown();
		return result;
	}

	private static void shutdown() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(dbURL + ";shutdown=true");
				conn.close();
			}
		} catch (SQLException sqlExcept) {

		}

	}

	public GUILoginScreen getLogin() {
		return login;
	}

	public void setLogin(GUILoginScreen login) {
		this.login = login;
	}
	
	public GUICustomerScreen getCustomer() {
		return customer;
	}
	
	public void setCustomer(GUICustomerScreen customer) {
		this.customer = customer;
	}
}
