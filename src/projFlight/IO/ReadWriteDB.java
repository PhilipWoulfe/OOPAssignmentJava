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
	
	/**
	 * Check the input password and username by connecting to the DB
	 * @param usernameInput
	 * @param passwordInput
	 * @return 
	 */
	public static boolean validatePassword(String usernameInput, char[] passwordInput) {
		
		boolean result = false;
		String selectSQL = "select password from passwords where UPPER(userName) = UPPER(?)";
		
		createConnection();
		
		// try to query DB
		try {
			
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1, usernameInput);
			ResultSet results = statement.executeQuery();

			// while there are more results
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
	
	/**
	 * Create a user object using information from the DB
	 * @param username
	 * @return
	 */
	public static User createUser(String username) {
		
		User user = null;
		String selectSQL = "select u.UserID, u.firstName, u.lastName, p.password, p.username, p.isadmin from users u join passwords p on u.userid = p.userid where username = ?";
		
		createConnection();
		
		// try to query DB
		try {
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1, username);
			results = statement.executeQuery();
			
			user = new User();
			
			// while there are more results
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
	
	/** 
	 * populate airport list from db
	 * @param airportList
	 * @return
	 */
	public static List<Airport> populateAirportList(List<Airport> airportList) {
		
		String selectSQL = "select name from airports";

		airportList.clear();
		
		createConnection();
		
		// try to query DB
		try {
			
			statement = conn.prepareStatement(selectSQL);
			ResultSet results = statement.executeQuery();

			// while there are more results
			while (results.next()) {
				
				Airport airport = new Airport();
				airport.setName(results.getString(1));
				
				// if not blank
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
		// sort airport list 
		Collections.sort(airportList, new AirportComparator());
		
		return airportList;
	}
	
	/**
	 * Adds the input flight to the DB
	 * @param user
	 * @param flight
	 */
	public static void addFlightToDB(User user, Flight flight) {
		
		String deptLeg1Code = getCodeForAirport(flight.getDeptLeg1Airport());
		String destLeg1Code = getCodeForAirport(flight.getDestLeg1Airport());
		String deptLeg2Code = getCodeForAirport(flight.getDeptLeg2Airport());
		String destLeg2Code = getCodeForAirport(flight.getDestLeg2Airport());

		createConnection();
		
		// try query DB
		try {
			
			String insertSQL = "INSERT INTO flights (BookingRef, UserID, DeptLeg1AirCode, DestLeg1AirCode, DeptLeg2AirCode, DestLeg2AirCode, Insurance) values (?, ?, ?, ?, ?, ?, ?)";
			statement = conn.prepareStatement(insertSQL);

			statement.setInt(1, flight.getBookingRef());
			statement.setInt(2, flight.getUserID());
			statement.setString(3, deptLeg1Code);
			statement.setString(4, destLeg1Code);
			
			// insert blank values if no second leg
			if (deptLeg2Code != null) {
				
				statement.setString(5, deptLeg2Code);
			} else {
				
				statement.setString(5, "");
			}
			
			// insert blank values if no second leg
			if (destLeg2Code != null) {
				
				statement.setString(6, destLeg2Code);
			} else {
				
				statement.setString(6, "");
			}
			statement.setBoolean(7, flight.isHasInsurance());
			
			statement.execute();

		} catch (SQLException sqlExcept) {
			
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error adding flight to database", "Warning", 0);
		} finally {
			
			shutdown();
		}
	}
	
	/**
	 * Add new airport to DB
	 * @param airCode
	 * @param name
	 */
	public static void addAirportToDB(String airCode, String name) {
		
		createConnection();
		
		// try query DB
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
	
	/**
	 * removes input airport from DB
	 * @param airport
	 */
	public static void removeAirportFromDB(String airport) {
		
		createConnection();
		
		// try query DB
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
	
	/** 
	 * get the next available booking reference
	 * @return
	 */
	public static int getNextBookingRef() {
		
		String selectSQL = "select count(*) from flights";
		int bookingRef = 0;
		
		createConnection();
		
		// try query DB
		try {
			statement = conn.prepareStatement(selectSQL);
			results = statement.executeQuery();

			// while there are more results
			while (results.next()) {
				
				bookingRef = results.getInt(1) + 1;
			}
		} catch (SQLException sqlExcept) {
			
			sqlExcept.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error getting next booking reference", "Warning", 0);
		} finally {
			
			shutdown();
		}
		
		return bookingRef;
	}
	
	/**
	 * Get the code for the input airport
	 * @param name
	 * @return
	 */
	public static String getCodeForAirport(String name) {
		
		String result = "";
		
		createConnection();
			
		// try query DB
		try {
			
			String selectSQL = "select aircode from airports where name = ?";
			
			statement = conn.prepareStatement(selectSQL);
			statement.setString(1,  name);
			
			ResultSet results = statement.executeQuery();
			
			// while more results
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
	
	/**
	 * populate the booking table from the DB
	 * @return
	 */
	public static JTable populateBookingTable() {
		
		JTable table = null;
		
		createConnection();
		
		// try query DB
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
	
	/**
	 * Build a table from the result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		
		// add columns
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		// while next
		while (rs.next()) {
			
			Vector<Object> vector = new Vector<Object>();
			
			// for each column
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			
			data.add(vector);
		}
		
		return new DefaultTableModel(data, columnNames);
	}

	/** 
	 * create a new connection to the DB
	 */
	private static void createConnection() {
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			
			// Get a connection
			conn = DriverManager.getConnection(dbURL);
			System.gc();
		} catch (Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to connect to Database. Please ensure database exists and there are no currently open connections", "Warning", 0);
		}
	}
	
	/** 
	 * close all connections to the DB
	 */
	private static void shutdown() {
		
		try {
			
			if (statement != null) {
				
				statement.close();
			}
			
			if (conn != null) {
				
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
