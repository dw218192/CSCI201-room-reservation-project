package webDataClasses;

public class User {
	private String username; 
	private String password;
	public User()
	{
		
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}

	public void setUsername(String newUsername)
	{
		this.username = newUsername;
	}
	
	public void setPassword(String newPassword)
	{
		this.password = newPassword;
	}
}
