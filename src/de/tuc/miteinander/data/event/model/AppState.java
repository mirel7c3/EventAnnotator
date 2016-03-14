package de.tuc.miteinander.data.event.model;

public enum AppState {
	COMPARE("CompareScene"),PONG("PongScene"),MENU("Menu"),AIRHOCKEY("AirHockey"),TRAILS("TouchTrails"),FLUID("FluidParticles"),WATER("Water"),OFFLINE("offline");
	
	
	public String title;
	
	public static String[] getTitles() {
		String[] names = new String[values().length];
		for (int i=0;i<values().length;i++){ 
			names[i]=values()[i].title;
		}
		return names;
	}
	
	AppState(String title) {
		this.title = title;
	}
}
