package webDataClasses;

import java.util.ArrayList;

public class RoomList {
	private ArrayList<Room> rooms;
	
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	public RoomList() {
		rooms = new ArrayList<Room>();
	}
	public void addRoom(Room newRoom) {
		rooms.add(newRoom);
	}
	public int size() {
		return rooms.size();
	}
}
