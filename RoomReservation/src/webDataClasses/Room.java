package webDataClasses;

public class Room {
	private boolean isAvailable;
	private String name;
	private String location;
	
	public Room(boolean isAvailable, String name, String location) {
		super();
		this.isAvailable = isAvailable;
		this.name = name;
		this.location = location;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
