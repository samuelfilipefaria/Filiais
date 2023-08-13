package models;

public enum Role {
	NORMAL("normal"), COORDINATOR("coordinator"), MANAGER("manager");
	
	private String role;
	
	Role(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
