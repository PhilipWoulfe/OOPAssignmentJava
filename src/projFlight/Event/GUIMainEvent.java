/**
* <h1>GUIMainEvent</h1>
* <p>GUIMainEvent handles all teh events for the flight project</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/
package projFlight.Event;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




import javax.swing.JOptionPane;

import java.awt.Desktop;
import java.awt.event.ActionEvent;

import projFlight.IO.ReadWriteDB;
import projFlight.gui.*;
import projFlight.models.Airport;
import projFlight.models.Flight;
import projFlight.models.User;


/**
 * @author Phil
 *
 */
public class GUIMainEvent implements ActionListener {
	
	/**
	 * Declare variables
	 */
	List<Airport> airportList = new ArrayList<Airport>();
	
	private GUIMain gui;
	private GUILoginScreen login;
	private GUICustomerScreen customer;
	private GUIConfirmScreen confirm;
	private GUIMaintenanceScreen maintain;
	
	private Flight f;
	
	private User user = new User();
	
	/**
	 * Constructor, takes in a GUIMain parameter
	 * @param in Accepts GUI main for constructor
	 */
	public GUIMainEvent(GUIMain in) {
		gui = in;

	}

	/**
	 * Controls action events - maybe this could be better
	 * @param event accepts event for checking actions
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();
		
		// if the event is the login button
		if (login.btnLoginIsSource(source)) {
			
			String usernameInput = login.getUsername();
			char[] passwordInput = login.getPassword();
			
			// If no input
			if (usernameInput.equals("")) {
				
				// Set error message 
				login.setUsernameError("User name cannot be blank");
			}
			
			// If no password
			if (passwordInput.length < 1) {
				
				// Set error message
				login.setPasswordError();
			} else {
				
				// If password is valid
				if (ReadWriteDB.validatePassword(usernameInput, passwordInput)) {
										
					user = ReadWriteDB.createUser(usernameInput);
					ReadWriteDB.populateAirportList(airportList);
					
					if (user.isAdmin()) {

						// go to admin screen
						maintain = new GUIMaintenanceScreen(this, airportList);
						gui.changeScreens(gui.getFrame(), login, maintain);
						maintain.populateAirportRemoveBox(airportList);
						maintain.setScrollPane(ReadWriteDB.populateBookingTable());
					} else {
						
						// Go to customer screen
						customer = new GUICustomerScreen(this, user);
						customer.setCboDeptLeg1(airportList);
						customer.setCboDestLeg1(airportList);
						gui.changeScreens(gui.getFrame(), login, customer);
						gui.addLogo(customer);
					}
					
					// Clear stuff
					login.clearPassword();
					login.clearUsernameError();
					login.clearPasswordError();
					login.clearPassword();

				} else {
					// warn bad username password
					login.setUsernameError("Invalid username or password");
					login.clearPassword();
				}
				
			}
		
		// if the event is the close button
		} else if (login.btnCloseIsSource(source)) {
		
			System.exit(0); 
		
		// if customer screen exists
		} else if (customer != null) {
			
			// if ine way is selected
			if (customer.isSourceRdbtnOneWay(source)) {		
				
				customer.enableLeg2(false);	
			
			// else if two way is selected
			} else if (customer.isSourceRdbtnReturnSecondLeg(source)){
			

				customer.enableLeg2(true);	
				customer.setCboDeptLeg2(airportList);
				customer.setCboDestLeg2(airportList);
			
			// else if Dept leg 1 changes
			} else if (customer.isSourceCboDeptLeg1(source)) {
			
				customer.setCboDestLeg1(airportList);
			
			// else is dest leg 1 changes	
			} else if (customer.isSourceCboDestLeg1(source)) {
				
				// if one way is selected
				if (!customer.getRdbtnOneWaySelected()) {
					customer.setCboDeptLeg2(airportList);
				}
			
			// else if dept leg 2 changes
			} else if (customer.isSourceCboDeptLeg2(source)) {
				
				// if two way selected (not one way)
				if (!customer.getRdbtnOneWaySelected()) {
					customer.setCboDestLeg2(airportList);
				}
			
			// else if book flights is clicked	
			} else if (customer.isSourceBtnBookFlights(source)) {
				bookFlights();
			
			// else if clear is clicked
			} else if (customer.isSourceBtnClear(source)) {
				customer.clearAllCustomer();
				
			// else if logout is clicked	
			} else if (customer.isSourceBtnLogout(source)) {
				logout();
			}
			
		// else if confirm screen exists	
		} else if (confirm != null) {
			
			// if confirm clicked
			if (confirm.isSourceBtnConfirm(source)) { 
				confirm.enableBtnConfirm(false);
				ReadWriteDB.addFlightToDB(user, f);
			
			// else if print is clicked
			} else if (confirm.isSourceBtnPrint(source)) {
				// TODO add print
			
			// exit is clicked
			} else if (confirm.isSourceBtnExit(source)) {
	
				confirm.enableBtnConfirm(true);
				gui.changeScreens(gui.getFrame(), confirm, customer);
				gui.addLogo(customer);
				customer.clearAllCustomer();
				confirm = null;
			}

		// if maintain screen exists
		} else if (maintain != null) {
			
			// if add airport is clicked
			if (maintain.isSourceBtnAddAirport(source)) {
				
				// if the length of the new airport isn't too long
				if (maintain.getTxtAddAirport().length() < 50) {
					
					// if the airport is blank
					if (maintain.getTxtAddAirport().equals("")) {
						JOptionPane.showMessageDialog(null, "Airport must have a name");
					
					// else add to db	
					} else {
						String aircode = maintain.getCboCode1() + "" + maintain.getCboCode2() + "" + maintain.getCboCode3();
						String name = maintain.getTxtAddAirport().trim();
						
						ReadWriteDB.addAirportToDB(aircode, name);
						ReadWriteDB.populateAirportList(airportList);
						maintain.populateAirportRemoveBox(airportList);
					}
				
				// else if new airport name is too long	
				} else {
					JOptionPane.showMessageDialog(null,  "Airport name must be less than 50 characters.");
					maintain.setTxtAddAirport("");
				}
			
			// else if remove button clicked	
			} else if (maintain.isSourceBtnRemove(source)) {
				
				// if default item selected
				if (maintain.getCboAirportRemoveIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Cannot remove default option");
				
				// else write to DB	
				} else {
					ReadWriteDB.removeAirportFromDB(maintain.getCboAirportRemove().toString());
					ReadWriteDB.populateAirportList(airportList);
					maintain.populateAirportRemoveBox(airportList);
				}
				
			// if logout clicked	
			}  else if (maintain.isSourceBtnLogout(source)) {
				
				gui.changeScreens(gui.getFrame(), maintain, login);
				gui.addLogo(login);
				login.clearPassword();
				maintain = null;
			}
		
		// else if any help button is clicked
		} else if ((login != null && login.isSourceBtnHelp(source)) || (customer != null && customer.isSourceBtnHelp(source)) || (confirm != null && confirm.isSourceBtnHelp(source)) || (maintain != null && maintain.isSourceBtnHelp(source))) {
			// if pdf display is supported
			if (Desktop.isDesktopSupported()) {
			    
				// open pdf with program
				try {
			    	String filePath = ".\\images\\HelpFile.pdf";
			        File myFile = new File(filePath);
			        Desktop.getDesktop().open(myFile);
			    } catch (IOException ex) {
			        // no application registered for PDFs
			    	JOptionPane.showMessageDialog(null,  "No application registered for reading PDF files");
			    }
			}
		}
	}

	/**
	 * method to logout
	 */
	private void logout() {
		customer.clearAllCustomer();
		gui.changeScreens(gui.getFrame(), customer, login);
		customer = null;
		gui.addLogo(login);
	}
	
	/**
	 * method to book flights
	 */
	private void bookFlights() {
		// TODO Auto-generated method stub
		int customerDeptLeg1Index = customer.getCboDeptLeg1Index();
		int customerDestLeg1Index = customer.getCboDeptLeg1Index();
		int customerDeptLeg2Index = customer.getCboDeptLeg1Index();
		int customerDestLeg2Index = customer.getCboDeptLeg1Index();
		int customerSeatTypeIndex = customer.getCboSeatTypeIndex();
		
		// if one way selected
		if (customer.getRdbtnOneWaySelected()) {
			
			// if any combo is on default value
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			
			// else switch to confirm screen 
			} else {
				confirm = new GUIConfirmScreen(this, user, createFlight());
				confirm.enableLeg2(false);
				gui.changeScreens(gui.getFrame(), customer, confirm);
				gui.addLogo(confirm);
			}
		
		// else if two way selected 	
		} else {
			
			// if any combo is on default value
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerDeptLeg2Index == 0
					|| customerDestLeg2Index == 0 || customerSeatTypeIndex == 0) {
				
				// Display warning
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			
			// else open confirm screen
			} else {
				confirm = new GUIConfirmScreen(this, user, createFlight());
				confirm.enableLeg2(true);
				gui.changeScreens(gui.getFrame(), customer, confirm);
				gui.addLogo(confirm);
			}
		}
	}
	
	/**
	 * Creates a new flight object from the details selected on this screen and returns it
	 * @return Returns populated flight
	 */
	private Flight createFlight() {
		f = new Flight();
		
		f.setBookingRef(ReadWriteDB.getNextBookingRef());
		f.setUserID(user.getUserID());
		f.setDeptLeg1Airport(customer.getCboDeptLeg1Selected());
		f.setDestLeg1Airport(customer.getCboDestLeg1Selected());
		
		// if flight has second leg - include the airports
		if (customer.getCboDeptLeg2Index() != -1) {
			f.setDeptLeg2Airport(customer.getCboDeptLeg2Selected());
			f.setDestLeg2Airport(customer.getCboDestLeg2Selected());
		
		// else set to blank
		} else {
			f.setDeptLeg2Airport("");
			f.setDestLeg2Airport("");
		}
		
		f.setLeg1SeatType(customer.getCboSeatType());
		f.setHasInsurance(customer.getChkbxInsurance());
		
		return f;
	}
	
	/**
	 * Get the login screen and return it
	 * @return login Returns Login screen
	 */
	public GUILoginScreen getLogin() {
		return login;
	}

	/**
	 * set the local login screen 
	 * @param login Sets login screen
	 */
	public void setLogin(GUILoginScreen login) {
		this.login = login;
	}
}

