//package accountManagment.repository;
//
//import java.io.*;
//import java.nio.file.*;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.stereotype.Repository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import accountManagment.dto.Account;
//import lombok.extern.java.Log;
//
//@Log
//@Repository
//public class AccountRepository implements ObjectRepository<Account> {
//
//	File file;
//	@Value("${app.repository.path}")
//	String repoPath;
//	@Value("${app.repository.name}")
//	String repoName;
//	Path path;
//
//	
//	@Autowired
//	UserDetailsManager manager;
//
//	@Autowired
//	ObjectMapper mapper;
//
//	public AccountRepository() {
//		createRepoFile();
//	}
//
//	public void restorePrevSession(UserDetailsManager manager) {
//		
//		try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
//			while(true) {
//				String accJson = reader.readLine();
//				if (accJson.isEmpty()) {
//					break;
//				}
//				Account account = mapper.readValue(accJson, Account.class);
//				UserDetails user = User.withUsername(account.username).password(account.username)
//						.roles(account.role).build();
//				manager.createUser(user);
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void createRepoFile() {
//		File dir = new File(repoPath);
//		if (!dir.exists()) {
//			dir.mkdir();
//		}
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public String addAccount(Account account) {
//		String res = String.format("account: %s has been created", account.toString());
//		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
//			String accJSON = mapper.writeValueAsString(account);
//			writer.write(accJSON);
//			writer.newLine();
//		} catch (Exception e) {
//			res = String.format("something went wrong: %s", e.getCause());
//		}
//		return res;
//	}
//
//	@Override
//	public String updateAccount(Account account) {
//		try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
//			while(true) {
//				String accJson = reader.readLine();
//				if (accJson.isEmpty()) {
//					break;
//				}
//				Account account = mapper.readValue(accJson, Account.class);
//				UserDetails user = User.withUsername(account.username).password(account.username)
//						.roles(account.role).build();
//				manager.createUser(user);
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public String deleteAccount(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
