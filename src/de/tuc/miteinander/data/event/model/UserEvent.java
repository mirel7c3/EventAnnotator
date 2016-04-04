package de.tuc.miteinander.data.event.model;

public class UserEvent extends TimeEvent {
	private UserType type;
	private InteractionType interaction;
	private int userNumber;
	private String position;
	
	public UserEvent(String dataString) {
		super(dataString);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void parseData(String payload) {
		
		// parse following
		
		// look for usertype like male, female
		for (UserType type: UserType.values()) {
			if (payload.contains(type.getTypeName())) {
				this.setType(type);
			}
		}
		
		// look for interaction type like new, left, returns
		for (InteractionType interaction: InteractionType.values()) {
			if (payload.contains(interaction.getInteractionName())) {
				this.setInteraction(interaction);
			}
		}
		
		// look for user number e.g. user 11
		if (payload.contains("user")) {
			int pos = payload.indexOf("user")+4;
			int userNumber = Integer.parseInt(payload.substring(pos, pos+3).trim());
			//System.out.println(userNumber+"");
			setUserNumber(userNumber);
		}
		else setUserNumber(-1);
		
		// look for user position e.g. "position L1"
		if (payload.contains("position")) {
			int pos = payload.indexOf("position")+8;
			setPosition((payload.substring(pos, pos+2).trim()));
		}
		else setPosition("unknown");
		

	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public InteractionType getInteraction() {
		return interaction;
	}

	public void setInteraction(InteractionType interaction) {
		this.interaction = interaction;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
