/**
 * 
 */
package projFlight.Event;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;




import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;

import projFlight.IO.ReadWriteDB;
import projFlight.gui.*;
import projFlight.models.Flight;
import projFlight.models.User;


/**
 * @author Phil
 *
 */
public class GUIMainEvent implements ActionListener {

	List<String> airportList = new ArrayList<String>();

	
	private GUIMain gui;
	private GUILoginScreen login;
	private GUICustomerScreen customer;
	private GUIConfirmScreen confirm;
	private GUIMaintenanceScreen maintain;
	
	



	User user = new User();
	Flight f;

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
			ReadWriteDB.addFlightToDB(user, confirm.getFlight());
		} else if (confirm != null && confirm.isSourceBtnPrint(source)) {
			// print
		} else if (confirm != null && confirm.isSourceBtnExit(source)) {

			confirm.enableBtnConfirm(true);
			gui.changeScreens(gui.getFrame(), confirm, customer);
			gui.addLogo(customer);
			customer.clearAllCustomer();
			confirm = null;

		} else if (maintain != null && maintain.isSourceBtnAddAirport(source)) {
			String aircode = maintain.getCboCode1() + "" + maintain.getCboCode2() + "" + maintain.getCboCode3();
			String name = maintain.getTxtAddAirport();
			
			ReadWriteDB.addAirportToDB(aircode, name);
			ReadWriteDB.populateAirportList(airportList);
			maintain.populateAirportRemoveBox(airportList);
		} else if (maintain != null && maintain.isSourceBtnRemove(source)) {
			// remove airport from db
			ReadWriteDB.removeAirportFromDB(maintain.getCboAirportRemove().toString());
			ReadWriteDB.populateAirportList(airportList);
			maintain.populateAirportRemoveBox(airportList);
		}  else if (maintain != null && maintain.isSourceBtnLogout(source)) {
			gui.changeScreens(gui.getFrame(), maintain, login);
			gui.addLogo(login);
			login.clearPassword();
			maintain = null;
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
		int customerSeatTypeIndex = customer.getCboDeptLeg1Index();
		
		if (customer.getRdbtnOneWaySelected()) {
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			} else {
				confirm = new GUIConfirmScreen(this, user, createFlight());
				confirm.enableLeg2(false);


			}
		} else {
			if (customerDeptLeg1Index == 0 || customerDestLeg1Index == 0 || customerDeptLeg2Index == 0
					|| customerDestLeg2Index == 0 || customerSeatTypeIndex == 0) {
				JOptionPane.showMessageDialog(null, "Please select all airports and seat type");
			} else {
				confirm = new GUIConfirmScreen(this, user, createFlight());
				confirm.enableLeg2(true);
				
			}
		}
		
		gui.changeScreens(gui.getFrame(), customer, confirm);
		gui.addLogo(confirm);

	}
	
	private Flight createFlight() {
		Flight f = new Flight();
		
		f.setBookingRef(ReadWriteDB.getNextBookingRef());
		f.setUserID(user.getUserID());
		f.setDeptLeg1Aircode(customer.getCboDeptLeg1Selected());
		f.setDestLeg1AirCode(customer.getCboDestLeg1Selected());
		if (customer.getCboDeptLeg1Index() != 0) {
			f.setDeptLeg2AirCode(customer.getCboDeptLeg2Selected());
			f.setDestLeg2AirCode(customer.getCboDestLeg2Selected());
		} else {
			f.setDeptLeg2AirCode("");
			f.setDestLeg2AirCode("");
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

