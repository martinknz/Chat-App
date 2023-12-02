package server;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 * Die Klasse startet einen Server auf einen angegebenen Port.
 * @author Martin Kunz
 */
public class Server extends Thread {

	public static int port;
	public static Participants participantsList = new Participants();
	public ServerSocket socket;
	public Transceiver transceiver = new Transceiver();
	public Date date = new Date();
	public SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	
	
	@Override
	public void run() {

		if (ServerGUI.portNumber.getText().equals(null) || ServerGUI.portNumber.getText().equals("")) {
			transceiver.writeInGUI("Attention: No Port was given!\n");
		}
		
		int port = Integer.parseInt(ServerGUI.portNumber.getText());
		
		try {
			socket = new ServerSocket(port);
		} 
		catch (IOException e) {
			transceiver.writeInGUI(e.toString() + "\n");
		}
		transceiver.writeInGUI("Server starts on Port " + port + " at " + format.format(date) + "h!\n");
	
		while (!isInterrupted() == true) {
			try {
				Socket activeSocket = socket.accept();
				ServerThreading serverThread = new ServerThreading(activeSocket);
				serverThread.start();
			} 
			catch (Exception e) {
				transceiver.writeInGUI(e.toString() + "\n");
			}
		}

		try {
			socket.close();
		} 
		catch (IOException e) {
			transceiver.writeInGUI(e.toString() + "\n");
		}
	}
}