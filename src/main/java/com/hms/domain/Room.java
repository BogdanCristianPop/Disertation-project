package com.hms.domain;

public class Room {
	private Long id;
	private int roomNumber;
	private int floor;
	private String size;
	private int capacity;
	private double price;
	
	
	public Room(int roomNumber, int floor, String size, int capacity, double price) {
		super();
		this.roomNumber = roomNumber;
		this.floor = floor;
		this.size = size;
		this.capacity = capacity;
		this.price = price;
	}


	public Room(Long id, int roomNumber, int floor, String size, int capacity, double price) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.floor = floor;
		this.size = size;
		this.capacity = capacity;
		this.price = price;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}


	public int getFloor() {
		return floor;
	}


	public void setFloor(int floor) {
		this.floor = floor;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Room [id=" + id + ", roomNumber=" + roomNumber + ", floor=" + floor + ", size=" + size
				+ ", capacity=" + capacity + ", price=" + price + "]";
	}


	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capacity;
		result = prime * result + floor;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + roomNumber;
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (capacity != other.capacity)
			return false;
		if (floor != other.floor)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (roomNumber != other.roomNumber)
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}
}
