package projFlight.IO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;

import projFlight.models.Airport;
import projFlight.models.AirportComparator;
import projFlight.models.Flight;
import projFlight.models.User;




public class ReadWriteDB {
	private static PreparedStatement statement;
	private static Connection conn;
	private static ResultSet results; 
	private static String dbURL = "jdbc:derby:C:\\Apache\\db-derby-10.12.1.1-bin\\bin\\FlightDatabase;create=false";
	
	
	public static boolean validatePassword(String usernameInput, char[] passwordInput) {
		createConnection();
		boolean result = false;
		String selectSQL = "select password from passwords where UPPER(userName) = UPPER(?)";

		try {
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1, usernameInput);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				String actualPassword = results.getString(1);
				result = Arrays.equals(passwordInput, actualPassword.toCharArray());
			}

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error validating password", "Warning", 0);
		} finally {
			shutdown();
		}
		return result;

	}
	
	public static User createUser(String username) {
		
		User user = null;
		String selectSQL = "select u.UserID, u.firstName, u.lastName, p.password, p.username, p.isadmin from users u join passwords p on u.userid = p.userid where username = ?";
		createConnection();
		
		try {
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1, username);
			results = statement.executeQuery();
			
			user = new User();
			
			while (results.next()) {
				user.setUserID(results.getInt("userid"));
				user.setFirstName(results.getString("firstName"));
				user.setLastName(results.getString("lastname"));
				user.setPassword(results.getString("password"));
				user.setUserName(results.getString("username"));
				user.setAdmin(results.getBoolean("IsAdmin"));
			}
			
			


		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error creating user", "Warning", 0);
		} finally {
			shutdown();
		}
		
		return user;

	}
	
	public static List<Airport> populateAirportList(List<Airport> airportList) {
		// TODO Auto-generated method stub

		airportList.clear();
		createConnection();
		
		String selectSQL = "select name from airports";

		try {
			statement = conn.prepareStatement(selectSQL);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				Airport airport = new Airport();
				airport.setName(results.getString(1));
				if (!airport.equals("")) {
					airportList.add(airport);
				}

			}
						
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error populating airport list", "Warning", 0);
		} finally {
			shutdown();
		}
		Collections.sort(airportList, new AirportComparator());
		return airportList;

		
		/*for (int i = 0; i < airportList.size(); i++) {
			System.out.println(airportList.get(i));
		}*/
	}
	
	public static void addFlightToDB(User user, Flight flight) {
		String temp = flight.getDeptLeg1Aircode();
		
		String deptLeg1Code = getCodeForAirport(temp);
		String destLeg1Code = getCodeForAirport(flight.getDestLeg1AirCode());
		String deptLeg2Code = getCodeForAirport(flight.getDeptLeg2AirCode());
		String destLeg2Code = getCodeForAirport(flight.getDestLeg2AirCode());

		createConnection();
		try {
			String insertSQL = "INSERT INTO flights (UserID, DeptLeg1AirCode, DestLeg1AirCode, DeptLeg2AirCode, DestLeg2AirCode, Insurance) values (?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(insertSQL);

			statement.setInt(1, user.getUserID());
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

			statement.setBoolean(6, flight.isHasInsurance());
			statement.execute();

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error adding flight to database", "Warning", 0);
		} finally {
			shutdown();
		}
		
	}
	
	public static void addAirportToDB(String airCode, String name) {
		createConnection();
		
		try {
			String insertSQL = "INSERT INTO airports (aircode, name) values (?, ?)";
			statement = conn.prepareStatement(insertSQL);

			statement.setString(1, airCode);
			statement.setString(2, name);
			statement.execute();
			JOptionPane.showMessageDialog(null, "Airport Succussfully Added.");
		} catch (DerbySQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Airport code already in use", "Warning", 0);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Message: " + e.getMessage(), "Warning", 0);
		} finally {
			shutdown();
		}
	}
	
	public static void removeAirportFromDB(String airport) {
		
		createConnection();
		try {
			String deleteSQL = "delete from airports where name = ?";
			statement = conn.prepareStatement(deleteSQL);

			statement.setString(1, airport);
			statement.execute();
			
			JOptionPane.showMessageDialog(null, "Airport Succussfully Removed.");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Message: " + e.getMessage(), "Warning", 0);
		} finally {
			shutdown();
		}
		
	}
	
	public static int getNextBookingRef() {
		createConnection();
		
		String selectSQL = "select count(*) from flights";
		int bookingRef = 0;
		
		try {
			statement = conn.prepareStatement(selectSQL);
			results = statement.executeQuery();

			while (results.next()) {
				bookingRef = results.getInt(1) + 1;
			}
			results.close();
			shutdown();
			

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error getting next booking reference", "Warning", 0);
		} finally {
			shutdown();
		}
		
		return bookingRef;
	}
	
	public static String getCodeForAirport(String name) {
		createConnection();
		
		
		String result = "";

		try {
			String selectSQL = "select aircode from airports where name = ?";
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1,  name);
			ResultSet results = statement.executeQuery();

			while (results.next()) {
				result = results.getString(1);
			}
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error getting code for airport", "Warning", 0);
		} finally {
			shutdown();
		}
		return result;
	}
	
	public static JTable populateBookingTable() {
		createConnection();
		JTable table = null;
		try {
			
			String selectString = "select f.BookingRef, u.firstName, u.LastName, f.deptLeg1AirCode, f.DestLeg1AirCode, f.deptLeg2Aircode, f.DestLeg2AirCode, f.insurance from flights f join users u on f.userid = u.userid order by bookingRef asc";
			statement = conn.prepareStatement(selectString);
			ResultSet results = statement.executeQuery();

			table = new JTable(buildTableModel(results));

		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error populating booking table", "Warning", 0);
		} finally {
			shutdown();
		}

		return table;

	}
	
	private static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

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

	
	private static void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			// Get a connection
			conn = DriverManager.getConnection(dbURL);
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Message: " + e.getMessage(), "Warning", 0);
		}
	}
	
	private static void shutdown() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				//DriverManager.getConnection(dbURL + ";shutdown=true");
				conn.close();
			}
			if (results != null) {
				results.close();
			}
		} catch (SQLException sqlExcept) {
			JOptionPane.showMessageDialog(null, "Error closing connection", "Warning", 0);
		}

	}
}
