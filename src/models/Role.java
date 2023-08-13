package models;

public enum Role {
	NORMAL(1), COORDINATOR(2), MANAGER(3);
	
	private int role;
	
	Role(int role) {
		this.role = role;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

}
