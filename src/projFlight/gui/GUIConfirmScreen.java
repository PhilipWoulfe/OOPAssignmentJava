package projFlight.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import projFlight.Event.GUIMainEvent;
import projFlight.IO.ReadWriteDB;
import projFlight.models.Flight;
import projFlight.models.User;

public class GUIConfirmScreen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName;


	private JTextField txtSeat;
	private JTextField txtLeg1;
	private JTextField txtLeg2;
	private JTextField txtInsurance;
	private JTextField txtBookRef;

	private JButton btnConfirm;
	private JButton btnPrint;
	private JButton btnExit;
	private JLabel lblName;
	private JLabel lblSeatType;
	private JLabel lblLeg1;
	private JLabel lblLeg2;
	private JLabel lblInsurance;
	private JLabel lblBookingReference;


	/**
	 * Create the panel.
	 */
	public GUIConfirmScreen(GUIMainEvent event, User u, Flight flight) {
		setLayout(null);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		btnConfirm.setBounds(66, 360, 104, 23);
		btnConfirm.addActionListener(event);;
		add(btnConfirm);

		btnPrint = new JButton("Print");
		btnPrint.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		btnPrint.setEnabled(false);
		btnPrint.setBounds(194, 360, 104, 23);
		btnPrint.addActionListener(event);
		add(btnPrint);

		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		btnExit.setBounds(321, 360, 104, 23);
		btnExit.addActionListener(event);
		add(btnExit);

		lblName = new JLabel("Name");
		lblName.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 17));
		lblName.setBounds(66, 135, 113, 23);
		add(lblName);

		lblSeatType = new JLabel("Seat Type");
		lblSeatType.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 17));
		lblSeatType.setBounds(66, 171, 113, 23);
		add(lblSeatType);

		lblLeg1 = new JLabel("Leg 1");
		lblLeg1.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 17));
		lblLeg1.setBounds(66, 207, 113, 23);
		add(lblLeg1);

		lblLeg2 = new JLabel("Leg 2");
		lblLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 17));
		lblLeg2.setBounds(66, 241, 113, 23);
		add(lblLeg2);

		lblInsurance = new JLabel("Insurance");
		lblInsurance.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 17));
		lblInsurance.setBounds(66, 277, 113, 23);
		add(lblInsurance);

		lblBookingReference = new JLabel("Booking Ref");
		lblBookingReference.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 17));
		lblBookingReference.setBounds(66, 313, 113, 23);
		add(lblBookingReference);

		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.TRAILING);
		txtName.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 14));
		txtName.setEditable(false);
		txtName.setBounds(312, 135, 113, 25);
		add(txtName);
		txtName.setColumns(10);

		txtSeat = new JTextField();
		txtSeat.setHorizontalAlignment(SwingConstants.TRAILING);
		txtSeat.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 14));
		txtSeat.setEditable(false);
		txtSeat.setColumns(10);
		txtSeat.setBounds(312, 171, 113, 25);
		add(txtSeat);

		txtLeg1 = new JTextField();
		txtLeg1.setHorizontalAlignment(SwingConstants.TRAILING);
		txtLeg1.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 14));
		txtLeg1.setEditable(false);
		txtLeg1.setColumns(10);
		txtLeg1.setBounds(312, 205, 113, 25);
		add(txtLeg1);

		txtLeg2 = new JTextField();
		txtLeg2.setHorizontalAlignment(SwingConstants.TRAILING);
		txtLeg2.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 14));
		txtLeg2.setEditable(false);
		txtLeg2.setColumns(10);
		txtLeg2.setBounds(312, 241, 113, 25);
		add(txtLeg2);

		txtInsurance = new JTextField();
		txtInsurance.setHorizontalAlignment(SwingConstants.TRAILING);
		txtInsurance.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 14));
		txtInsurance.setEditable(false);
		txtInsurance.setColumns(10);
		txtInsurance.setBounds(312, 277, 113, 25);
		add(txtInsurance);

		txtBookRef = new JTextField();
		txtBookRef.setHorizontalAlignment(SwingConstants.TRAILING);
		txtBookRef.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 14));
		txtBookRef.setEditable(false);
		txtBookRef.setColumns(10);
		txtBookRef.setBounds(312, 313, 113, 23);
		add(txtBookRef);
		
		setTxtName(u.getFirstName() + " " + u.getLastName());
		String temp = flight.getLeg1SeatType();
		setTxtLeg1(ReadWriteDB.getCodeForAirport(flight.getDeptLeg1Aircode()) + " To " + ReadWriteDB.getCodeForAirport(flight.getDestLeg1AirCode()));
		if (!flight.getDeptLeg2AirCode().equals("")) {
			setTxtLeg2(flight.getDeptLeg2AirCode() + "To " + flight.getDestLeg2AirCode());
		}
		
		setTxtSeat(flight.getLeg1SeatType());
		setTxtBookRef(flight.getBookingRef() + "");
		
		if (flight.isHasInsurance()) {
			setTxtInsurance("Yes");
		} else {
			setTxtInsurance("No");
		}
		

	}
	
	public JTextField getTxtName() {
		return txtName;
	}

	public void setTxtName(String name) {
		txtName.setText(name);
	}

	public String getTxtSeat() {
		return txtSeat.getText();
	}

	public void setTxtSeat(String seatType) {
		this.txtSeat.setText(seatType);;
	}

	public String getTxtLeg1() {
		return txtLeg1.getText();
	}

	public void setTxtLeg1(String leg1) {
		this.txtLeg1.setText(leg1);
	}

	public String getTxtLeg2() {
		return txtLeg2.getText();
	}

	public void setTxtLeg2(String leg2) {
		this.txtLeg2.setText(leg2);;
	}

	public String getTxtInsurance() {
		return txtInsurance.getText();
	}

	public void setTxtInsurance(String insurance) {
		this.txtInsurance.setText(insurance);
	}

	public String getTxtBookRef() {
		return txtBookRef.getText();
	}

	public void setTxtBookRef(String bookRef) {
		this.txtBookRef.setText(bookRef);
	}

	public boolean isSourceBtnConfirm(Object source) {
		return source == btnConfirm;
	}
	
	public void enableBtnConfirm(boolean enabled) {
		btnConfirm.setEnabled(enabled);
	}
	
	public boolean isSourceBtnPrint(Object source) {
		return source == btnPrint;
	}
	
	public boolean isSourceBtnExit(Object source) {
		return source == btnExit;
	}
	
	public void enableLeg2(boolean enabled) {
		txtLeg2.setEnabled(enabled);
		lblLeg2.setEnabled(enabled);
	}
	

}
