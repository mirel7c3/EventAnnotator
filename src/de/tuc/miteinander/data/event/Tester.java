package de.tuc.miteinander.data.event;

import de.tuc.miteinander.data.event.model.StateChangeEvent;
import de.tuc.miteinander.data.event.model.UserEvent;

public class Tester {

	public static void main(String[] args) {
		
		StateChangeEvent sce = new StateChangeEvent("2014-09-22 13:59:58,856 INFO  changed to CompareScene at 1411387198856");
		
		System.out.println("Date:"+sce.getDate()+" State:"+sce.getState().title);
		
		UserEvent ue = new UserEvent("2014-09-22 13:59:58,856 male user 11 returns at position L1");
		
		System.out.println("Date:"+ue.getDate()+" Type:"+ue.getType().getTypeName()+" Interaction:"+ue.getInteraction().getInteractionName()+" #:"+ue.getUserNumber());

	}

}
