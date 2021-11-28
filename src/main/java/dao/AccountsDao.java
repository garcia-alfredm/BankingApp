package dao;

import models.Accounts;

import java.util.List;

public interface AccountsDao {
    List<Accounts> getAllAccounts();

    Accounts getOneAccount(Integer accountId, Integer clientIdFk);

    void createAccount(Integer clientIdFk, Double amount);

    void updateAccount(Integer accountId, Double amount, Integer clientIdFk);

    void deleteAccount(Integer accountId, Integer clientIdFk);
}
