/**
 * 
 */
package de.tuc.miteinander.data.event;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.tuc.miteinander.data.event.model.AppState;
import de.tuc.miteinander.data.event.model.StateChangeEvent;
import de.tuc.miteinander.data.event.model.TimeEvent;
import de.tuc.miteinander.data.event.model.UserEvent;
import de.tuc.miteinander.data.event.model.UserType;

/**
 * @author storz
 *
 * write .dat files for GNU Plot
 */
public class EventDataWriter {
	
	/**
	 * Create data string out of events.
	 * 
	 * @param events - sorted (time) list of events
	 * @param interval - in minutes
	 * @param user - set true to write user data
	 * @param app - set true to write app data
	 * @return
	 */
	public static ArrayList<String> getDataString(ArrayList<TimeEvent> events, int interval, boolean user, boolean app) {
		ArrayList<String> userDataString = new ArrayList<String>();
		Calendar cal = GregorianCalendar.getInstance();
		
		Date startDate = events.get(0).getDate();
		Date endDate = events.get(events.size()-1).getDate();
		
		//init user data
		//counts types like male, female, children
		int[] typeCount = new int[UserType.values().length];
		//init
		for (int i=0; i<typeCount.length;i++){
			typeCount[i] = 0;
		}
		boolean[] userAtTable = new boolean[100];
		for (int i=0;i<100;i++ ){
			userAtTable[i] = false;
		}
		
		//init app data
		int[] activeAppTracker = new int[AppState.values().length];
		int activeApp = -1;
		
		for (long i=startDate.getTime();i<=endDate.getTime();i+=60000*interval) {
			long nextInterval = i+60000*interval;
			for (TimeEvent te: events) {
				if (te instanceof UserEvent && user) {
					if (te.getDate().getTime()>=i && te.getDate().getTime()<nextInterval) {
						UserEvent ue = (UserEvent) te;
						switch (ue.getInteraction()) {
							case NEW: case RETURNS: {
								if (userAtTable[ue.getUserNumber()]) {
									System.out.println("User "+ue.getUserNumber()+" already at table");
								}
								else {
								userAtTable[ue.getUserNumber()]=true;
									typeCount[ue.getType().ordinal()]++; 
								}
								break;
							}
							case LEFT: {
								if (!userAtTable[ue.getUserNumber()]) System.out.println("User "+ue.getUserNumber()+" not at table");
								else {
									userAtTable[ue.getUserNumber()]=false;
									typeCount[ue.getType().ordinal()]--; 
								}
								break;
							}
							case WINS:
								break;
							default:
								break;
						}
					}
				}
				if (te instanceof StateChangeEvent && app) {
					if (te.getDate().getTime()>=i && te.getDate().getTime()<nextInterval) {
					
						StateChangeEvent sce = (StateChangeEvent) te;
						
						if (activeApp!= -1) activeAppTracker[activeApp] = 0;
						activeAppTracker[sce.getState().ordinal()] = 1;
						activeApp = sce.getState().ordinal();
					}
				}
			}
			
			//assemble data string
			cal.setTime(new Date(i));
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			
			String stringToBuild = hour +":"+ minute;
			
			int[] values; 
			if (user) { //user
				values = typeCount;
			}
			else { //app
				values = activeAppTracker;
			}
			
			for (int c:values) {
				stringToBuild += " " + c;
			}
			
			System.out.println(stringToBuild);
			
			userDataString.add(stringToBuild);
		}
	
		return userDataString;
	} 
	
	public static void writeData(String filename, List<String> lines) {
		Path file = Paths.get(filename);
	    try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
