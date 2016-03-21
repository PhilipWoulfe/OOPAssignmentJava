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

import projFlight.models.Flight;
import projFlight.models.User;

public class PrintText {
	
	//TODO add comments
	public PrintText(User u, Flight flight) {

		String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
		System.out.println("Default printer: " + defaultPrinter);
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();

		
		InputStream is = null;
		String name = u.getFirstName() + " " + u.getLastName();
		String bookRef = flight.getBookingRef() + "";
		String firstLeg = flight.getDeptLeg1Airport() + " to " + flight.getDestLeg1Airport();
		String secondLeg = flight.getDeptLeg2Airport().equals("") ? flight.getDeptLeg2Airport() + " to " + flight.getDestLeg2Airport(): "No Second Leg";
		String seatType = flight.getLeg1SeatType();
		String insurance = flight.isHasInsurance() ? "Yes" : "No";
				
		
		String outputStr = "\nBooking Reference: " + bookRef + "\nName: " + name + "\nFirst Leg: " + firstLeg + "\nSecond Leg: " + secondLeg + 
									"\nSeat Type: " + seatType + "\nInsurance: " + insurance;
	 
		
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

	public synchronized void waitForDone() {
		try {
			while (!done) {
				wait();
			}
		} catch (InterruptedException e) {
		}
	}
}
