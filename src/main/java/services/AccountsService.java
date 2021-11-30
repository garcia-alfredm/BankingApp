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
        //very client exists
        //get all accounts
        List<Accounts> clientAccounts = new ArrayList<>();
        List<Accounts> allAccounts = getAllAccounts();
        //iterate thru accounts
        for(int i = 0; i < allAccounts.size(); i++ ){
            if(allAccounts.get(i).getClientIdFk() == clientIdFk){
                clientAccounts.add(allAccounts.get(i));
            }
        }
        return clientAccounts;
    }

    public List<Accounts> getClientAccounts(Integer clientIdFk, Double lessThan, Double greaterThan){
        //get all accounts
        List<Accounts> clientAccounts = new ArrayList<>();
        List<Accounts> allAccounts = getAllAccounts();
        //iterate through accounts
        for(int i = 0; i < allAccounts.size(); i++){
            int checkClientFk = allAccounts.get(i).getClientIdFk();
            Double checkBalance = allAccounts.get(i).getBalance();

            if(checkClientFk == clientIdFk && checkBalance > lessThan && checkBalance < greaterThan){
                clientAccounts.add(allAccounts.get(i));
            }
        }
        return clientAccounts;
    }

    public Boolean withdraw(Integer id, Integer clientIdFk, Double value){
        //check account exists
        Accounts target = getOneAccount(id, clientIdFk);
        //verify account has balance
        if(target.getBalance() < value){
            return false;
        }
        Double amount = target.getBalance() - value;
        updateAccount(id, amount, clientIdFk);

        return true;
    }

    public Boolean deposit(Integer id, Integer clientIdFk, Double value){
        //check account exists
        Accounts target = getOneAccount(id, clientIdFk);
        //verify value is valid
        if(value <= 0){
            return false;
        }
        Double amount = target.getBalance() + value;
        updateAccount(id, value, clientIdFk);

        return true;
    }

    public Boolean transfer(Integer fromId, Integer toId, Integer clientIdFk, Double value){
        //verify client exists, if not send 404
        Clients clients = clientsService.getOneClient(clientIdFk);
        //verify account exists
        List<Accounts> accounts = new ArrayList<>();
        accounts.add(getOneAccount(fromId, clientIdFk));
        accounts.add(getOneAccount(toId, clientIdFk));

        if(accounts.get(0).getBalance() < value){
            return false;
        }
        //withdraw amount from acc
        updateAccount(fromId, accounts.get(0).getBalance() - value, clientIdFk);
        //deposit amount from acc
        updateAccount(fromId, accounts.get(1).getBalance() + value, clientIdFk);

        return true;
    }
}
