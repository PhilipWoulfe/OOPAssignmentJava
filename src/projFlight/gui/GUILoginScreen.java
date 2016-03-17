package projFlight.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;

import projFlight.GUIMainEvent;

import javax.swing.JPasswordField;
import javax.swing.JButton;

public class GUILoginScreen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnLogin;
	private JButton btnClose;
	private JTextField tboUsername;
	private JPasswordField passwordField;
	private JLabel lblUsernameError;
	private JLabel lblPasswordError;
	
	public GUICustomerScreen customerScreen;

	/**
	 * Create the panel.
	 */
	public GUILoginScreen(GUIMainEvent mainEvent) {
		
		setLayout(null);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 18));
		lblUsername.setBounds(75, 193, 109, 35);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 18));
		lblPassword.setBounds(75, 264, 109, 35);
		add(lblPassword);

		tboUsername = new JTextField();
		tboUsername.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 18));
		tboUsername.setBounds(184, 193, 231, 35);
		add(tboUsername);
		tboUsername.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 18));
		passwordField.setBounds(184, 264, 231, 35);
		add(passwordField);

		btnLogin = new JButton("OK");
		btnLogin.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 18));
		btnLogin.setBounds(184, 330, 109, 35);
		btnLogin.addActionListener(mainEvent);
		add(btnLogin);
				
		btnClose = new JButton("Close");
		btnClose.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 18));
		btnClose.setBounds(306, 330, 109, 35);
		btnClose.addActionListener(mainEvent);
		add(btnClose);

		lblUsernameError = new JLabel("");
		lblUsernameError.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblUsernameError.setForeground(Color.RED);
		lblUsernameError.setBounds(184, 228, 241, 14);
		add(lblUsernameError);

		lblPasswordError = new JLabel("");
		lblPasswordError.setFont(new Font("Aharoni", Font.BOLD | Font.ITALIC, 11));
		lblPasswordError.setForeground(Color.RED);
		lblPasswordError.setBounds(184, 296, 241, 14);
		add(lblPasswordError);

	}

	public String getUsername() {
		return tboUsername.getText();
	}
	
	public char[] getPassword() {
		return passwordField.getPassword();
	}
	
	public void clearPassword() {
		passwordField.setText("");
	}
	
	public void setUsernameError(String error) {
		lblUsernameError.setText(error);
	} 
	
	public void clearUsernameError() {
		lblUsernameError.setText("");
	}
	
	public void clearPasswordError() {
		lblPasswordError.setText("");;
	}
	
	public void setPasswordError() {
		lblPasswordError.setText("Password cannot be blank");
	}
	
	public boolean btnLoginIsSource (Object source) {
		return source == btnLogin;	
	}
	
	public boolean btnCloseIsSource(Object source) {
		return source == btnClose;
	}
	
	public void createCustomerGUI(GUIMainEvent mainEvent, String fName, String lName) {
		customerScreen = new GUICustomerScreen(mainEvent, fName, lName);
		mainEvent.setCustomer(customerScreen);
	}
	
	public void closeCustomerGUI() {
		customerScreen = null;
	}
	
}
