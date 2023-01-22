package accountManagment.repository;

import java.util.HashMap;

import accountManagment.dto.Account;

public class AccountRepositoryObjWriterImpl implements AccountRepository<Account> {
	
	HashMap<String, Account> accountBase;

	@Override
	public boolean addAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAccount(Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accountExist(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void restoreBase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveBase() {
		// TODO Auto-generated method stub
		
	}



}
