package com.hms.domain;

public class Guest {
	
	private Long id;
	private String firstName;
	private String lastname;
	private String DOB;
	private String passportNumber;
	private String membershipNumber;
	private Boolean isActive;
	
	
	public Guest() {
		
	}
	public Guest(Long id, String firstName, String lastname, String dOB, String passportNumber,
			String membershipNumber, Boolean isActive) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastname = lastname;
		DOB = dOB;
		this.passportNumber = passportNumber;
		this.membershipNumber = membershipNumber;
		this.isActive = isActive;
	}
	public Guest(String firstName, String lastname, String dOB, String passportNumber,
			String membershipNumber, Boolean isActive) {
		this.firstName = firstName;
		this.lastname = lastname;
		DOB = dOB;
		this.passportNumber = passportNumber;
		this.membershipNumber = membershipNumber;
		this.isActive = isActive;
		
	}
	public Guest(Long id, Boolean isActive) {
		this.id = id;
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return "Guest [id=" + id + ", firstName=" + firstName + ", lastname=" + lastname + ", DOB=" + DOB
				+ ", passportNumber=" + passportNumber + ", membershipNumber=" + membershipNumber + ", isActive="
				+ isActive + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getMembershipNumber() {
		return membershipNumber;
	}
	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DOB == null) ? 0 : DOB.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((membershipNumber == null) ? 0 : membershipNumber.hashCode());
		result = prime * result + ((passportNumber == null) ? 0 : passportNumber.hashCode());
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
		Guest other = (Guest) obj;
		if (DOB == null) {
			if (other.DOB != null)
				return false;
		} else if (!DOB.equals(other.DOB))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (membershipNumber == null) {
			if (other.membershipNumber != null)
				return false;
		} else if (!membershipNumber.equals(other.membershipNumber))
			return false;
		if (passportNumber == null) {
			if (other.passportNumber != null)
				return false;
		} else if (!passportNumber.equals(other.passportNumber))
			return false;
		return true;
	}

	
	
	
}
