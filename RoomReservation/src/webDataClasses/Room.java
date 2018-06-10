package webDataClasses;

import java.util.ArrayList;

public class Room {
	//private boolean isAvailable;
	private String name;
	private ArrayList<String> date;
	public void setDate(ArrayList<String> date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getDate() {
		return date;
	}
	public void addDate(String date) {
		this.date.add(date);
	}
	

	
}
