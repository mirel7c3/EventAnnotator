package de.tuc.miteinander.data.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import de.tuc.miteinander.data.event.model.TimeEvent;

public class Tester {

	public static void main(String[] args) {

		ArrayList<TimeEvent> events = EventImport.importEvents("/users/storz/Dropbox/medientage_2014_2.log");
		
		ArrayList<String> dataLines = EventDataWriter.getDataString(events, 1, false, true);
		
		EventDataWriter.writeData("./data/medientage_2014_app.dat", dataLines);
		
		dataLines.clear();
		
		dataLines = EventDataWriter.getDataString(events, 1, true, false);
		
		EventDataWriter.writeData("./data/medientage_2014_user.dat", dataLines);
		
		//		
//		ArrayList<String> logLines = readLog("/users/storz/Dropbox/medientage_2014.log");
//		
//		ArrayList<TimeEvent> timeEvents = new ArrayList<TimeEvent>();
//		for (String line:logLines) {
//			//System.out.println(line);
//			if (line.contains("user")) {
//				timeEvents.add(new UserEvent(line));
//			}
//			else {
//				timeEvents.add(new StateChangeEvent(line));
//			}
//		}
//		
//		Collections.sort(timeEvents, new EventComparator());
//		
//		for (TimeEvent t:timeEvents) {
//			System.out.println(t.getDate());
//		}
		 
//		StateChangeEvent sce = new StateChangeEvent("2014-09-22 13:59:58,856 INFO  changed to CompareScene at 1411387198856");
//		
//		System.out.println("Date:"+sce.getDate()+" State:"+sce.getState().title);
//		
//		UserEvent ue = new UserEvent("2014-09-22 13:59:58,856 male user 11 returns at position L1");
//		
//		System.out.println("Date:"+ue.getDate()+" Type:"+ue.getType().getTypeName()+" Interaction:"+ue.getInteraction().getInteractionName()+" #:"+ue.getUserNumber());

	}
	
	private static ArrayList<String> readLog(String filename) {
		 ArrayList<String> lines = new ArrayList<String>();    
		
		    if (filename != null){
			    Path file = Paths.get(filename);
			    
			    try (BufferedReader reader = Files.newBufferedReader(file)) {
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			        	lines.add(line);
			        }
			    } catch (IOException x) {
			        System.err.format("IOException: %s%n", x);
			    }
		    }
		 return lines;
	}

}
