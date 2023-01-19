package accountManagment.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class Account {
	
	@Email @NotNull
	public String username;
	@Length(min = 6)
	public String password;
	@Value("USER")
	public String role;
}
