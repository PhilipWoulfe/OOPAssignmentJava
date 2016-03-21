/**
* <h1>GUILoginScreen</h1>
* <p>GUILoginScreen handles the login for flight project</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/

package projFlight.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;

import projFlight.Event.GUIMainEvent;



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
	private JButton btnHelp;

	/**
	 * Construct the panel
	 * @param mainEvent Uses event to create actionlisteners
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

		btnLogin = new JButton("Login");
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

		btnHelp = new JButton("Help");
		btnHelp.setBounds(85, 337, 60, 23);
		btnHelp.addActionListener(mainEvent);
		add(btnHelp);
	}

	/**
	 * Gets username
	 * @return String Returns string of input username
	 */
	public String getUsername() {
		return tboUsername.getText();
	}

	/**
	 * get Password
	 * @return char[] returns char array of input password
	 */
	public char[] getPassword() {
		return passwordField.getPassword();
	}

	/**
	 * clears password
	 */
	public void clearPassword() {
		passwordField.setText("");
	}

	/**
	 * set username error message
	 * @param error accepts username error message
	 */
	public void setUsernameError(String error) {
		lblUsernameError.setText(error);
	}

	/**
	 * clear username error
	 */
	public void clearUsernameError() {
		lblUsernameError.setText("");
	}
	
	/**
	 * clear password error
	 */
	public void clearPasswordError() {
		lblPasswordError.setText("");
	}
	
	/**
	 * set passowrd error
	 */
	public void setPasswordError() {
		lblPasswordError.setText("Password cannot be blank");
	}

	/**
	 * check if login was source
	 * @param source accepts object
	 * @return boolean returns if this object matches input object
	 */ 
	public boolean btnLoginIsSource(Object source) {
		return source == btnLogin;
	}

	/**
	 * Check if close is source
	 * @param source accepts object
	 * @return boolean returns if this object matches input object
	 */
	public boolean btnCloseIsSource(Object source) {
		return source == btnClose;
	}

	/**
	 * check if help was source
	 * @param source accepts object
	 * @return boolean returns if this object matches input object
	 */
	public boolean isSourceBtnHelp(Object source) {
		return source == btnHelp;
	}
}
