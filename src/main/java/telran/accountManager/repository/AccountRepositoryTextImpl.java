package telran.accountManager.repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.accountManager.controller.AccountManagerController;
import telran.accountManager.dto.Account;

@Service
public class AccountRepositoryTextImpl implements AccountRepository<Account> {

	static Logger log = LoggerFactory.getLogger(AccountManagerController.class);
	PasswordEncoder encoder;

	public AccountRepositoryTextImpl(PasswordEncoder encoder) {
		this.encoder = encoder;
//		repoPath = ".//accounts";
//		repoName = ".accounts.txt";
		createRepo();
	}

	private void createRepo() {
		System.out.println("repoPath = " + repoPath);
		System.out.println("repoName = " + repoName);
		boolean tmp = Files.exists(Paths.get(repoPath, repoName));

		String logText = tmp ? "repo file in folder exists" : "repo file in folder does not exist";
		log.debug(logText);

		if (!tmp) {
			logText = new File(repoPath).mkdir() ? "repo folder was created" : "repo folder was not created";
			log.debug(logText);
			try {
				logText = new File(repoPath, repoName).createNewFile() ? "repo txt was created"
						: "repo txt was not created";
			} catch (IOException e) {
				log.debug(e.getStackTrace().toString());
				e.printStackTrace();
			}
			log.debug(logText);
		}
	}

	File file;
	@Value("${app.repository.path}")
	String repoPath;
	@Value("${app.repository.name}")
	String repoName;
	Path path;
	HashMap<String, Account> accountBase;

	@Autowired
	UserDetailsManager manager;

	@Autowired
	ObjectMapper mapper;

	@Override
	public boolean addAccount(Account account) {
		accountBase.put(account.username, getSafeAccount(account));
		return accountExist(account.username);
	}

	@Override
	public boolean updateAccount(Account account) {
		accountBase.remove(account.username);
		accountBase.put(account.username, getSafeAccount(account));
		return !accountBase.get(account.username).equals(account);
	}

	@Override
	public boolean deleteAccount(String username) {
		accountBase.remove(username);
		return !accountBase.containsKey(username);
	}

	@Override
	public boolean accountExist(String username) {
		return accountBase.containsKey(username);
	}

	public void saveBase() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			accountBase.entrySet().stream().forEach(account -> {
				String accJSON;
				try {
					accJSON = mapper.writeValueAsString(account);
					writer.write(accJSON);
					writer.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void restoreBase() {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (true) {
				String accJson = reader.readLine();
				if (accJson.isEmpty()) {
					break;
				}
				Account account = mapper.readValue(accJson, Account.class);
				UserDetails user = User.withUsername(account.username).password(account.username).roles(account.role)
						.build();
				manager.createUser(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Account getSafeAccount(Account unsafeAccount) {
		return new Account(unsafeAccount.username, unsafeAccount.role, encoder.encode(unsafeAccount.password));
	}
}
