/**
 * 
 */
package de.tuc.miteinander.data.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import de.tuc.miteinander.data.event.model.StateChangeEvent;
import de.tuc.miteinander.data.event.model.TimeEvent;
import de.tuc.miteinander.data.event.model.UserEvent;

/**
 * @author storz
 *
 */
public class EventImport {
	
	public static ArrayList<TimeEvent> importEvents(String logfile) {
		ArrayList<String> lines = readLog(logfile);
		ArrayList<TimeEvent> timeEvents = new ArrayList<TimeEvent>();
		for (String line:lines) {
			//System.out.println(line);
			//todo: improve safety
			if (line.contains("user")) {
				timeEvents.add(new UserEvent(line));
			}
			else {
				timeEvents.add(new StateChangeEvent(line));
			}
		}
		//sort by time
		Collections.sort(timeEvents, new EventComparator());
		return timeEvents;
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
