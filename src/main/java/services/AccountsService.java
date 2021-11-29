package services;

import dao.AccountsDao;
import dao.AccountsDaoImpl;
import models.Accounts;
import models.Clients;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToDoubleFunction;

public class AccountsService {
    AccountsDao accountsDao;
    ClientsService clientsService;

    public AccountsService(){
        this.accountsDao = new AccountsDaoImpl();
        this.clientsService = new ClientsService();
    }

    public AccountsService(AccountsDao accountsDao, ClientsService clientsService){
        this.accountsDao = accountsDao;
        this.clientsService = clientsService;
    }

    public List<Accounts> getAllAccounts(){
        return this.accountsDao.getAllAccounts();
    }

    public Accounts getOneAccount(Integer accountId, Integer clientIdFk){
        //need to check clientfk is valid
        Clients client = clientsService.getOneClient(clientIdFk);
        return this.accountsDao.getOneAccount(accountId, client.getId());
    }

    public void createAccount(Accounts account){
        this.accountsDao.createAccount(account);
    }

    public void updateAccount(Integer accountId, Double amount, Integer clientIdFk){
        Clients client = clientsService.getOneClient(clientIdFk);
        this.accountsDao.updateAccount(accountId, amount, client.getId());
    }

    public void deleteAccount(Integer accountId, Integer clientIdFk){
        Clients client = clientsService.getOneClient(clientIdFk);
        this.accountsDao.deleteAccount(accountId, clientIdFk);
    }

    public List<Accounts> getClientAccounts(Integer clientIdFk){
        //List<Accounts> clientAccounts = new ArrayList<>();
        //clientAccounts = accountsDao.getAllAccounts();
        return null;
    }

    public List<Accounts> getClientAccounts(Integer cliendtIdFk, Integer lessThan, Integer greaterThan){
        return null;
    }

    public Boolean withdraw(Integer id, Integer clientIdFk, Double value){
        return false;
    }

    public Boolean deposit(Integer id, Integer clientIdFk, Double value){
        return false;
    }

    public Boolean transfer(Integer fromId, Integer toId, Integer clientIdFk, Double value){
        return false;
    }
}
