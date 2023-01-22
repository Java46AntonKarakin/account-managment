package telran.accountManager.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.ToString;

@ToString
public class Account {
	
	public Account(@Email @NotEmpty String username, @NotEmpty @Length(min = 6) String password,
			@NotEmpty @Pattern(message = "Incorrect role", regexp = "USER|ADMIN") String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public Account() {}
	
	@NotEmpty @Email
	public String username;
	@NotEmpty @Length(min = 6)
	public String password;
	@NotEmpty @Pattern(message = "Incorrect role", regexp = "USER|ADMIN")
	public String role;
}
