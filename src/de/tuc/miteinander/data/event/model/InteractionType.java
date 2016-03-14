package de.tuc.miteinander.data.event.model;

public enum InteractionType {
	NEW("new"),LEFT("left"),WINS("wins"),RETURNS("returns");
	
	private String interactionName;
	
	public String getInteractionName() {
		return interactionName;
	}

	public static String[] getInteractionNames() {
		String[] names = new String[values().length];
		for (int i=0;i<values().length;i++){ 
			names[i]=values()[i].interactionName;
		}
		return names;
	}
	
	InteractionType(String interactionName) {
		this.interactionName = interactionName;
	}
	
	
}
