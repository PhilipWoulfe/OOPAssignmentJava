/**
* <h1>GUIMain</h1>
* <p>UIMain is the main class of the flight project</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/

package projFlight.gui;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.BorderLayout;

import projFlight.Event.GUIMainEvent;

public class GUIMain {

	GUIMainEvent mainEvent = new GUIMainEvent(this);

	private JFrame frame;
	private GUILoginScreen login = new GUILoginScreen(mainEvent);
	
	
	/**
	 * Launch the application.
	 * @param args Main Method
	 */
	public static void main(String[] args) {
		GUIMain window = new GUIMain();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public GUIMain() {

		initialize();
		controller.start();
		mainEvent.setLogin(login);
		
	}

	/**
	 * Thread controls changing the JPanels in the application window 
	 */
	private Thread controller = new Thread() {
		public void run() {

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {

					frame.getContentPane().add(login);
					addLogo(login);
					frame.revalidate();

				}
			});

		}
	};

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLUE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setBounds(100, 100, 503, 476);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Add a logo to the input panel
	 * @param panel Adds logo to input panel
	 */
	public void addLogo(JPanel panel) {
		BufferedImage myPicture = null;
		try {
			String filePath = ".\\images\\WolfLogo.png";
			myPicture = ImageIO.read(new File(filePath));
		} catch (IOException e) {

			e.printStackTrace();
		}

		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		picLabel.setBounds(0, 0, 170, 128);
		panel.add(picLabel);

	}
	
	/**
	 * Change remove remove from mainFraim and add add
	 * @param mainFrame Frame to change panels in
	 * @param remove Panel to be removed
	 * @param add Panel to be added
	 */
	
	public void changeScreens(JFrame mainFrame, JPanel remove, JPanel add) {
		mainFrame.getContentPane().remove(remove);
		mainFrame.repaint();
		mainFrame.getContentPane().add(add);
		add.setVisible(true);
		mainFrame.repaint();
		mainFrame.revalidate();
	}
	
	/**
	 * getter method for frame
	 * @return JFrame returns this frame
	 */
	public JFrame getFrame() {
		return this.frame;
	}
}
