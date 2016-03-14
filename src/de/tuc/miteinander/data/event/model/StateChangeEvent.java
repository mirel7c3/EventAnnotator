/**
 * 
 */
package de.tuc.miteinander.data.event.model;

/**
 * @author storz
 *
 */
public class StateChangeEvent extends TimeEvent {
	private AppState state;
	
	public StateChangeEvent(String dataString) {
		super(dataString);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.tuc.miteinander.data.TimeEvent#parseData(java.lang.String)
	 */
	@Override
	protected void parseData(String payload) {
		
		for (AppState state: AppState.values()) {
			if (payload.contains(state.title)) {
				this.setState(state);
			}
		}
	}

	public AppState getState() {
		return state;
	}

	public void setState(AppState state) {
		this.state = state;
	}
}
