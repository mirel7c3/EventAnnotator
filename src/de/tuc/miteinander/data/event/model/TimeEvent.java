package de.tuc.miteinander.data.event.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TimeEvent {
	
	private Date date;
	
	public TimeEvent(Date date) {
		this.setDate(date);
	}
	
	public TimeEvent(String dataString) {
		String dateAsString = dataString.substring(0,22);
		String payload = dataString.substring(23);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		
		Date d;
		try {
			d = df.parse(dateAsString);
		} catch (ParseException e) {
			d = new Date(0);
			e.printStackTrace();
		}
		
		this.setDate(d);
		
		parseData(payload);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	abstract protected void parseData(String payload);
}
