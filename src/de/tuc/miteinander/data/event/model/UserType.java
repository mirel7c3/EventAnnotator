package de.tuc.miteinander.data.event.model;

public enum UserType {
	MALE("male"),FEMALE("female"),CHILD("child");
	
	private String typeName;
	
	public String getTypeName() {
		return typeName;
	}
	
	public static String[] getTypeNames() {
		String[] names = new String[values().length];
		for (int i=0;i<values().length;i++){ 
			names[i]=values()[i].typeName;
		}
		return names;
	}

	UserType(String type) {
		this.typeName = type;
	}
}
