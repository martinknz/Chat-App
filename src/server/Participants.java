package server;
import java.util.*;
import common.*;


/**
 * Die Klasse stellt Methoden zur Verf&uuml;gung, um die einzelnen Participant zu verwalten.
 * @author Martin Kunz
 */
public class Participants {
	
	
	private ArrayList<Participant> participant = new ArrayList<>();
	
	
	/**
	 * Default-Konstruktor
	 */
	public Participants() {
	}
	
	
	/**
	 * Die Methode f&uuml;gt einen Participant der ArrayList hinzu
	 * @param p: Ist der Participant, welcher hinzugef&uuml;gt werden soll
	 * @return boolean: liefert true, wenn der Participant erfolgreich der ArrayList hinzugef&uuml;gt wurde, ansonsten false
	 */
	public synchronized boolean hinzufuegen(Participant p) {
		
		if (participant.size() == 3) {
			return false;
		}
		for (int i = 0; i < participant.size(); i++) {
			if (participant.get(i).getName().equals(p.getName())) {
				return false;
			}
		}
		participant.add(p);
		return true;
	}
	
	
	/**
	 * Die Methode l&ouml;scht einen Participant aus der ArrayList
	 * @param p: Der Participant, welcher entfernt werden soll
	 * @return boolean: liefert true wenn der Participant erfolgreich gel&ouml;scht wurde, ansonsten false
	 */
	public synchronized boolean delete(Participant p) {
		if (participant.contains(p)) {
			participant.remove(p);
			return true;
		} 
		else {
			return false;					
		}
	}
	
	
	/**
	 * Die Methode liefert alle Participant
	 * @return Array: Liefert alle Participant zur&uuml;ck
	 */
	public synchronized Participant[] allParticipants() {
		int size;
		size = participant.size();
		Participant[] p = new Participant[size];
		
		for (int i = 0; i < participant.size(); i++) {
			p[i] = participant.get(i);
		}
		return p;
	}
	
	
	/**
	 * Die Methode gibt die Gr&uuml;&szlig;e der ArrayList zur&uuml;ck
	 * @return int: Liefert die Gr&uuml;&szlig;e der Participant-Liste zur&uuml;ck
	 */
	public synchronized int size() {
		if (participant == null || participant.size() == 0) {
			return 0;
		}
		else {
			return participant.size();
		}
	}
}
