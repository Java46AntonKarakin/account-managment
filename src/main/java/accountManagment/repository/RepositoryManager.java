package accountManagment.repository;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import accountManagment.dto.Account;

@Repository
public class RepositoryManager implements ObjectRepository<Account> {

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
	public String addAccount(Account account) {
		accountBase.put(account.username, account);
		return null;
	}

	@Override
	public String updateAccount(Account account) {
		accountBase.remove(account.username);
		accountBase.put(account.username, account);
		return null;
	}

	@Override
	public String deleteAccount(String username) {
		accountBase.remove(username);
		return null;
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
