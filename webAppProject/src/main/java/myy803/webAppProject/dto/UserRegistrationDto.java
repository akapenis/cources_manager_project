package myy803.webAppProject.dto;

public class UserRegistrationDto {
	private String first;
	private String last;
	private String username;
	private String password;
	
	public UserRegistrationDto() {
		
	}

	public UserRegistrationDto(String first, String last, String username,
		String password) {
		super();
		this.first = first;
		this.last = last;
		this.username = username;
		this.password = password;
	}

	public String getFirst() {
		return first;
	}
	
	public String getLast() {
		return last;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setFirst(String first) {
		this.first = first;
	}
	
	public void setLast(String last) {
		this.last = last;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}