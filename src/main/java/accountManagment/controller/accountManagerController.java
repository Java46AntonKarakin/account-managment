package accountManagment.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import accountManagment.dto.Account;
import accountManagment.repository.ObjectRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.extern.java.Log;

import org.slf4j.*;

@Log
@RestController
@RequestMapping("accounts")
public class accountManagerController {
	PasswordEncoder encoder;
	UserDetailsManager manager;
	static Logger log = LoggerFactory.getLogger(accountManagerController.class);

	@Autowired
	ObjectRepository<Account> repo;

	public accountManagerController(PasswordEncoder encoder, UserDetailsManager manager) {
		this.encoder = encoder;
		this.manager = manager;
	}

	@PostMapping
	String addUser(@RequestBody @Valid Account account) {
		String res = String.format("User with name %s already exists", account.username);
		if (!manager.userExists(account.username)) {
			log.debug(String.format("addUser - user with name %s doen't exist", account.username));
			UserDetails user = User.withUsername(account.username).password(encoder.encode(account.username))
					.roles(account.role).build();
			repo.addAccount(account);
			manager.createUser(user);
			res = String.format("user %s has been added ", account.username);
		}
		return res;
	}

	@PutMapping
	String updateUser(@RequestBody @Valid Account account) {
		String res = String.format("User with name %s doesn't exist", account.username);
		if (manager.userExists(account.username)) {
			UserDetails user = User.withUsername(account.username).password(encoder.encode(account.username))
					.roles(account.role).build();
			manager.updateUser(user);
			repo.updateAccount(account);
			res = String.format("user %s has been updated", account.username);
		}
		return res;
	}

	@DeleteMapping("/{username}")
	String deleteUser(@PathVariable("username") String username) {
		String res = String.format("User with name %s doesn't exist", username);
		if (manager.userExists(username)) {
			manager.deleteUser(username);
			repo.deleteAccount(username);
			res = String.format("user %s has been deleted", username);
		}
		return res;
	}

	@GetMapping("/{username}")
	boolean userExists(@PathVariable("username") String username) {
		return manager.userExists(username);
	}

	@PostConstruct
	void updateBD() {
		repo.restoreBase();
	}

	@PreDestroy
	void saveAccounts() {
		repo.saveBase();
	}

}
