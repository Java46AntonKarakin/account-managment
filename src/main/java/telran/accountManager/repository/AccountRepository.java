package telran.accountManager.repository;

public interface AccountRepository <Account>{
	boolean addAccount(Account account);
	boolean updateAccount(Account account);
	boolean deleteAccount(String username);
	boolean accountExist(String username);
	void restoreBase();
	void saveBase();
}
