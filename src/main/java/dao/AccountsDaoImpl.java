package dao;

import models.Accounts;

import java.util.List;

public class AccountsDaoImpl implements AccountsDao {

    @Override
    public List<Accounts> getAllAccounts() {
        return null;
    }

    @Override
    public Accounts getOneAccount(Integer accountId, Integer clientIdFk) {
        return null;
    }

    @Override
    public void createAccount(Accounts account, Integer clientIdFk) {

    }

    @Override
    public void updateAccount(Integer accountId, Double amount, Integer clientIdFk) {

    }

    @Override
    public void deleteAccount(Integer accountId, Integer clientIdFk) {

    }
}
