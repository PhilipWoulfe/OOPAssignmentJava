/**
 * 
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

	List<Airport> airportList = new ArrayList<Airport>();

	
	private GUIMain gui;
	private GUILoginScreen login;
	private GUICustomerScreen customer;
	private GUIConfirmScreen confirm;
	private GUIMaintenanceScreen maintain;
	
	private Flight f;
	
	



	User user = new User();


	static int bookingRef;

	public GUIMainEvent(GUIMain in) {
		gui = in;

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();
		
		if (login.btnLoginIsSource(source)) {
			String usernameInput = login.getUsername();
			char[] passwordInput = login.getPassword();
			

			if (usernameInput.equals("")) {
				login.setUsernameError("User name cannot be blank");
			}

			if (passwordInput.length < 1) {
				login.setPasswordError();
			} else {

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
						customer = new GUICustomerScreen(this, user);
						//System.out.println(airportList.get(0));
						customer.setCboDeptLeg1(airportList);
						customer.setCboDestLeg1(airportList);
						gui.changeScreens(gui.getFrame(), login, customer);
						gui.addLogo(customer);
						
					}
					
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

		} else if (login.btnCloseIsSource(source)) {
			System.exit(0); 
		} else if (customer != null && customer.isSourceRdbtnOneWay(source)) {
			customer.enableLeg2(false);	
		}else if (customer != null && customer.isSourceRdbtnReturnSecondLeg(source)){
			customer.enableLeg2(true);	
			customer.setCboDeptLeg2(airportList);
			customer.setCboDestLeg2(airportList);
		} else if (customer != null && customer.isSourceCboDeptLeg1(source)) {
			customer.setCboDestLeg1(airportList);
		} else if (customer != null && customer.isSourceCboDestLeg1(source)) {
			if (!customer.getRdbtnOneWaySelected()) {
				customer.setCboDeptLeg2(airportList);
			}
		} else if (customer != null && customer.isSourceCboDeptLeg2(source)) {
			if (!customer.getRdbtnOneWaySelected()) {
				customer.setCboDestLeg2(airportList);
			}
		} else if (customer != null && customer.isSourceBtnBookFlights(source)) {
			bookFlights();
		} else if (customer != null && customer.isSourceBtnClear(source)) {
			customer.clearAllCustomer();
		} else if (customer != null && customer.isSourceBtnLogout(source)) {
			logout();
		} else if (confirm != null && confirm.isSourceBtnConfirm(source)) { //TODO from here
			confirm.enableBtnConfirm(false);
			ReadWriteDB.addFlightToDB(user, f);
		} else if (confirm != null && confirm.isSourceBtnPrint(source)) {
			// print
		} else if (confirm != null && confirm.isSourceBtnExit(source)) {

			confirm.enableBtnConfirm(true);
			gui.changeScreens(gui.getFrame(), confirm, customer);
			gui.addLogo(customer);
			customer.clearAllCustomer();
			confirm = null;

		} else if (maintain != null && maintain.isSourceBtnAddAirport(source)) {
			if (maintain.getTxtAddAirport().length() < 50) {
				if (maintain.getTxtAddAirport().equals("")) {
					JOptionPane.showMessageDialog(null, "Airport must have a name");
				} else {
					String aircode = maintain.getCboCode1() + "" + maintain.getCboCode2() + "" + maintain.getCboCode3();
					String name = maintain.getTxtAddAirport().trim();
					
					ReadWriteDB.addAirportToDB(aircode, name);
					ReadWriteDB.populateAirportList(airportList);
					maintain.populateAirportRemoveBox(airportList);
				}
				
			} else {
				JOptionPane.showMessageDialog(null,  "Airport name must be less than 50 characters.");
				maintain.setTxtAddAirport("");
			}
		} else if (maintain != null && maintain.isSourceBtnRemove(source)) {
			// remove airport from db
			if (maintain.getCboAirportRemoveIndex() == 0) {
				JOptionPane.showMessageDialog(null, "Cannot remove default option");
			} else {
				ReadWriteDB.removeAirportFromDB(maintain.getCboAirportRemove().toString());
				ReadWriteDB.populateAirportList(airportList);
				maintain.populateAirportRemoveBox(airportList);
			}
		}  else if (maintain != null && maintain.isSourceBtnLogout(source)) {
			gui.changeScreens(gui.getFrame(), maintain, login);
			gui.addLogo(login);
			login.clearPassword();
			maintain = null;
		} else if ((login != null && login.isSourceBtnHelp(source)) || (customer != null && customer.isSourceBtnHelp(source)) || (confirm != null && confirm.isSourceBtnHelp(source)) || (maintain != null && maintain.isSourceBtnHelp(source))) {
			if (Desktop.isDesktopSupported()) {
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

	private void logout() {
		customer.clearAllCustomer();
		gui.changeScreens(gui.getFrame(), customer, login);
		customer = null;
		gui.addLogo(login);
	}
	
	private void bookFlights() {
		// TODO Auto-generated method stub
		int customerDeptLeg1Index = customer.getCboDeptLeg1Index();
		int customerDestLeg1Index = customer.getCboDeptLeg1Index();
		int customerDeptLeg2Index = customer.getCboDeptLeg1Index();
		int customerDestLeg2Index = customer.getCboDeptLeg1Index();
		int customerSeatTypeIndex = customer.getCboSeatTypeIndex();
		
		if (customer.getRdbtnOneWaySelected()) {
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			} else {
				confirm = new GUIConfirmScreen(this, user, createFlight());
				confirm.enableLeg2(false);
				gui.changeScreens(gui.getFrame(), customer, confirm);
				gui.addLogo(confirm);


			}
		} else {
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerDeptLeg2Index == 0
					|| customerDestLeg2Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			} else {
				confirm = new GUIConfirmScreen(this, user, createFlight());
				confirm.enableLeg2(true);
				gui.changeScreens(gui.getFrame(), customer, confirm);
				gui.addLogo(confirm);
				
			}
		}
	}
	
	private Flight createFlight() {
		f = new Flight();
		
		f.setBookingRef(ReadWriteDB.getNextBookingRef());
		f.setUserID(user.getUserID());
		f.setDeptLeg1Airport(customer.getCboDeptLeg1Selected());
		f.setDestLeg1Airport(customer.getCboDestLeg1Selected());
		if (customer.getCboDeptLeg2Index() != -1) {
			f.setDeptLeg2Airport(customer.getCboDeptLeg2Selected());
			f.setDestLeg2Airport(customer.getCboDestLeg2Selected());
		} else {
			f.setDeptLeg2Airport("");
			f.setDestLeg2Airport("");
		}
		f.setLeg1SeatType(customer.getCboSeatType());
		f.setHasInsurance(customer.getChkbxInsurance());
		
		
		return f;
	}

	

	public GUILoginScreen getLogin() {
		return login;
	}

	public void setLogin(GUILoginScreen login) {
		this.login = login;
	}
}

