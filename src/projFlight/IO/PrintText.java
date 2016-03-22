/**
* <h1>PrintText</h1>
* <p>Prints the details of the booked flight</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-22 
 */
package projFlight.IO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.JOptionPane;

import projFlight.models.Flight;
import projFlight.models.User;

public class PrintText {
	
	/**
	 * Takes in a user and a flight and prints out the users name and flight details
	 * @param u uses this for the name
	 * @param flight uses this for every other detail
	 */
	public PrintText(User u, Flight flight) {

		String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
		System.out.println("Default printer: " + defaultPrinter);
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		// Get values from input
		InputStream is = null;
		String border = "*****************************************************************************";
		String name = u.getFirstName() + " " + u.getLastName();
		String bookRef = flight.getBookingRef() + "";
		String firstLeg = flight.getDeptLeg1Airport() + " to " + flight.getDestLeg1Airport();
		String secondLeg = flight.getDeptLeg2Airport().equals("") ? "No Second Leg" : flight.getDeptLeg2Airport() + " to " + flight.getDestLeg2Airport();
		String seatType = flight.getLeg1SeatType();
		String insurance = flight.isHasInsurance() ? "Yes" : "No";
		
		String outputStr = border + "\n\r\n\rWolfAir Booking Details\n\r\n\r" + border + "\n\r\n\rBooking Reference: " + bookRef + "\n\rName: " + name + "\n\rFirst Leg: " + firstLeg + "\n\rSecond Leg: " + secondLeg + 
									"\n\rSeat Type: " + seatType + "\n\rInsurance: " + insurance;
	 
		
		try {
			is = new ByteArrayInputStream(outputStr.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(new Copies(1));

		DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
		Doc doc = new SimpleDoc(is, flavor, null);
		DocPrintJob job = service.createPrintJob();

		PrintJobWatcher pjw = new PrintJobWatcher(job);
		try {
			job.print(doc, pras);
		} catch (PrintException e) {
			JOptionPane.showMessageDialog(null, "Error Printing");
			e.printStackTrace();
		}
		pjw.waitForDone();
		try {
			is.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}

/**
* <h1>PrintJObWatcher</h1>
* <p>Honestly, I have no idea what this one does. I assume it watches the print job.</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-22 
 */
class PrintJobWatcher {
	boolean done = false;

	PrintJobWatcher(DocPrintJob job) {
		job.addPrintJobListener(new PrintJobAdapter() {
			public void printJobCanceled(PrintJobEvent pje) {
				allDone();
			}

			public void printJobCompleted(PrintJobEvent pje) {
				allDone();
			}

			public void printJobFailed(PrintJobEvent pje) {
				allDone();
			}

			public void printJobNoMoreEvents(PrintJobEvent pje) {
				allDone();
			}

			void allDone() {
				synchronized (PrintJobWatcher.this) {
					done = true;
					System.out.println("Printing done ...");
					PrintJobWatcher.this.notify();
				}
			}
		});
	}

	/**
	 * I think this waits for the print to be done
	 */
	public synchronized void waitForDone() {
		try {
			while (!done) {
				wait();
			}
		} catch (InterruptedException e) {
		}
	}
}
