package server;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.*;


/**
 * Die Klasse ist zust&auml;ndig f&uuml;r das Senden und Empfangen von Nachrichten.
 * @author Martin Kunz
 */
public class Transceiver {
	
	public static Socket socket;
	public static Message message;
	public static PrintWriter printWriter;

	
	/**
	 * Die Methode sendet eine Message &uuml;ber den PrintWriter.
	 * @param printWriter: PrintWriter, &uuml;ber welchen gesendet werden soll.
	 * @param message: Message, welche &uuml;bermittelt werden soll.
	 * @return String: Gibt die Message zur&uuml;ck.
	 */
	public String send(PrintWriter printWriter, Message message) {
		printWriter.println(message.toString());
		return message.toString();
	}

	/**
	 * Writes a message to the GUI chat area.
	 * @param message The message to be displayed in the chat area.
	 */
	public void writeInGUI(String message) {
		ServerGUI.chatTextArea.append(message + "\n");
	}

	
	/**
	 * Schreibt jeden einzelnen Participant in die JList der GUI
	 */
	public void sendParticipantsList() {
		ServerGUI.list.clear();
		Participant[] p = Server.participantsList.allParticipants();
		String participantsList = "";
		String teilnehmer;

		for (Participant value: p) {
			participantsList += value.getName() + ":";
		}
		message = new Message("Participants", participantsList);

		for (Participant participant: p) {
			socket = participant.getSocket();
			teilnehmer = participant.getName();

			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			send(printWriter, message);
			writeInList(teilnehmer);

		}
	}


	/**
	 * F&uuml;gt einen Participant in die JList der GUI hinzu.
	 * @param p: Ist der Participant, welcher der JList hinzugef&uuml;gt werden soll.
	 */
	public void writeInList(String p) {
		ServerGUI.list.addElement(p);
	}
	
	
	/**
	 * Sendet eine eingehende Message an alle Participant des Chats.
	 * @param message: Schl&uuml;sselwort für die jeweilige Message.
	 * @param rest: Inhalt einer Message.
	 * @param p: Participant, welche die Message gesendet hat.
	 * @param ps: Participants mit allen Usern.
	 * @return String: Gibt den Username und die dazugeh&ouml;rige Message zur&uuml;ck.
	 */
	public String sendToAll(String message, String rest, Participant p, Participants ps) {
		String name = p.getName();
		Participant[] part = ps.allParticipants();
		Transceiver.message = new Message(message, name + ": " + rest);
		Date zeit = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		for (int i = 0; i < part.length; i++) {
			socket = part[i].getSocket();
			try {
				printWriter = new PrintWriter(socket.getOutputStream(), true);
			} 
			catch (IOException e) {
				writeInGUI(e.toString());
			}
			send(printWriter, Transceiver.message);
		}
		return name + ": " + rest + " (" + format.format(zeit) + ")";
	}
}