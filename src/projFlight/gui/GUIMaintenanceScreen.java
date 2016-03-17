package projFlight.gui;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUIMaintenanceScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField txtAddAirport;

	public JComboBox<String> cboAirportRemove;
	public JButton btnRemove;
	public JButton btnAddAirport;
	public JComboBox<String> cboCode1;
	public JComboBox<String> cboCode2;
	public JComboBox<String> cboCode3;
	public JButton btnLogout;
	public JTable bookingTable;
	public JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public GUIMaintenanceScreen() {
		super(new BorderLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane, BorderLayout.CENTER);

		JComponent airportPanel = makeTextPanel();
		tabbedPane.addTab("Airports", null, airportPanel, "holder");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel bookingPanel = new JPanel();
		tabbedPane.addTab("Bookings", null, bookingPanel, null);
		bookingPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 494, 361);
		bookingPanel.add(scrollPane);

		scrollPane.setViewportView(bookingTable);

		JPanel userPanel = new JPanel();
		tabbedPane.addTab("Users", null, userPanel, null);

		JPanel pnlButtons = new JPanel();
		pnlButtons.setBounds(0, 0, 494, 395);
		add(pnlButtons, BorderLayout.SOUTH);

		btnLogout = new JButton("Logout");
		pnlButtons.add(btnLogout);

	}

	protected JComponent makeTextPanel() {
		JPanel panel = new JPanel(false);
		panel.setLayout(null);

		JLabel lblRemove = new JLabel("Select airport to remove");
		lblRemove.setBounds(60, 56, 177, 14);
		panel.add(lblRemove);

		cboAirportRemove = new JComboBox<String>();
		cboAirportRemove.setBounds(60, 81, 221, 33);
		panel.add(cboAirportRemove);

		btnRemove = new JButton("Remove");
		btnRemove.setBounds(344, 81, 89, 33);
		panel.add(btnRemove);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 150, 475, 2);
		panel.add(separator);

		JLabel lblAirportCode = new JLabel("Airport Code");
		lblAirportCode.setToolTipText("Select Three Letter Airport Code");
		lblAirportCode.setBounds(60, 177, 145, 14);
		panel.add(lblAirportCode);

		txtAddAirport = new JTextField();
		txtAddAirport.setBounds(60, 284, 221, 33);
		panel.add(txtAddAirport);
		txtAddAirport.setColumns(10);

		JLabel lblAirportName = new JLabel("Airport Name");
		lblAirportName.setBounds(60, 258, 66, 14);
		panel.add(lblAirportName);

		btnAddAirport = new JButton("Add Airport");
		btnAddAirport.setBounds(344, 284, 89, 33);
		panel.add(btnAddAirport);

		cboCode1 = new JComboBox<String>();
		cboCode1.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
		cboCode1.setBounds(60, 202, 47, 33);
		panel.add(cboCode1);

		cboCode2 = new JComboBox<String>();
		cboCode2.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
		cboCode2.setBounds(117, 202, 47, 33);
		panel.add(cboCode2);

		cboCode3 = new JComboBox<String>();
		cboCode3.setModel(new DefaultComboBoxModel<String>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
		;
		cboCode3.setBounds(174, 202, 53, 33);
		panel.add(cboCode3);

		return panel;
	}
}
