package de.tuc.miteinander.data.event;

import java.util.Comparator;


import de.tuc.miteinander.data.event.model.TimeEvent;


public class EventComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		return ((TimeEvent) o1).getDate().compareTo(((TimeEvent)o2).getDate());
	}

}
