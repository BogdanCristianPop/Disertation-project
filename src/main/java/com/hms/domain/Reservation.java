package com.hms.domain;

public class Reservation {
	private Long id;
	private int guestId;
	
	public Reservation() {
	}
	
	
	
	public Reservation(int guestId) {
		super();
		this.guestId = guestId;

	}
	
	public Reservation(Long id, int guestId) {
		super();
		this.id = id;
		this.guestId = guestId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public int getGuestId() {
		return guestId;
	}



	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}



	@Override
	public String toString() {
		return "Reservation [id=" + id + ", guestId=" + guestId + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + guestId;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Reservation other = (Reservation) obj;
		if (guestId != other.guestId)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
}