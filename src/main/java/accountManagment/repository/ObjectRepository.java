package accountManagment.repository;

public interface ObjectRepository <Account>{
	String addAccount(Account account);
	String updateAccount(Account account);
	String deleteAccount(String username);
	void restoreBase();
	void saveBase();
}
