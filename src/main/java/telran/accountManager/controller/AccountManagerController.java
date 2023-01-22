package telran.accountManager.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import telran.accountManager.dto.Account;
import telran.accountManager.repository.AccountRepository;

import org.slf4j.*;

@RestController
@RequestMapping("accounts")
@Validated
public class AccountManagerController {
	PasswordEncoder encoder;
	static Logger log = LoggerFactory.getLogger(AccountManagerController.class);

	@Autowired
	AccountRepository<Account> repo;

	public AccountManagerController(PasswordEncoder encoder, UserDetailsManager manager) {
		log.debug("AccountManagmentController constructor");
		this.encoder = encoder;
		log.debug("AccountManagmentController constructor created");
	}

	@PostMapping
	String addUser(@RequestBody @Valid Account account) {
		String res = String.format("User with name %s already exists", account.username);
		if (!repo.accountExist(account.username)) {
			repo.addAccount(account);
			res = String.format("user %s has been added ", account.username);
		}
		return res;
	}

	@PutMapping
	String updateUser(@RequestBody @Valid Account account) {
		String res = String.format("User with name %s doesn't exist", account.username);
		if (repo.accountExist(account.username)) {
			repo.updateAccount(account);
			res = String.format("user %s has been updated", account.username);
		}
		return res;
	}

	@DeleteMapping("/{username}")
	String deleteUser(@PathVariable("username") @Email String username) {
		String res = String.format("User with name %s doesn't exist", username);
		if (repo.accountExist(username)) {
			repo.deleteAccount(username);
			res = String.format("user %s has been deleted", username);
		}
		return res;
	}

	@GetMapping("/{username}")
	boolean userExists(@PathVariable("username") @Email String username) {
		return repo.accountExist(username);
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
