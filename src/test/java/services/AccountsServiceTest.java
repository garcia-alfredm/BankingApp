package services;

import dao.AccountsDao;
import models.Accounts;
import models.Clients;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountsServiceTest {

    AccountsDao accountsDao = Mockito.mock(AccountsDao.class);
    ClientsService clientsService = Mockito.mock(ClientsService.class);
    AccountsService accountsService;

    public AccountsServiceTest(){
        this.accountsService = new AccountsService(accountsDao, clientsService);
    }

    @Test
    void getAllAccounts() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        List<Accounts> accounts = new ArrayList<>();
        accounts.add(new Accounts(1, 1, 0.0, date));
        accounts.add(new Accounts(2, 1, 0.0, date));
        accounts.add(new Accounts(3, 1, 0.0, date));
        List<Accounts> expectedResults = accounts;
        Mockito.when(accountsDao.getAllAccounts()).thenReturn(accounts);

        //act
        List<Accounts> actualResults = accountsService.getAllAccounts();

        //assign
        assertEquals(expectedResults, actualResults);
    }

    @Test
    void getOneAccount() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients client = new Clients(1, "John", date);
        List<Accounts> accounts = new ArrayList<>();
        accounts.add(new Accounts(1, 1, 0.0, date));
        accounts.add(new Accounts(2, 1, 0.0, date));
        accounts.add(new Accounts(3, 1, 0.0, date));
        Accounts expectedResults = accounts.get(0);
        Mockito.when(clientsService.getOneClient(1)).thenReturn(client);
        Mockito.when(accountsDao.getOneAccount(1, 1)).thenReturn(accounts.get(0));

        //act
        Accounts actualResults = accountsService.getOneAccount(1, 1);

        //assign
        assertEquals(expectedResults, actualResults);
    }

    @Test
    void createAccount() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Accounts expectedResult = new Accounts(1, 1, 0.0, date);

        //act
        accountsService.createAccount(expectedResult);

        //assert
        Mockito.verify(accountsDao, Mockito.times(1)).createAccount(expectedResult);

    }

    @Test
    void updateAccount() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients client = new Clients(1, "John", date);
        Accounts expectedResult = new Accounts(1, 1, 0.0, date);
        Double amount = 20.00;
        Mockito.when(clientsService.getOneClient(1)).thenReturn(client);

        //act
        accountsService.updateAccount(1, amount, 1);

        //assert
        Mockito.verify(accountsDao,
                Mockito.times(1)).
                updateAccount(1, amount, 1);
    }

    @Test
    void deleteAccount() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients client = new Clients(1, "John", date);
        Accounts expectedResult = new Accounts(1, 1, 0.0, date);
        Double amount = 20.00;
        Mockito.when(clientsService.getOneClient(1)).thenReturn(client);

        //act
        accountsService.deleteAccount(1, client.getId());

        //assert
        Mockito.verify(accountsDao,
                Mockito.times(1)).
                deleteAccount(1, 1);
    }

    @Test
    void getClientAccounts() {
    }

    @Test
    void getClientAccountsLessThanGreaterThan() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void deposit() {
    }

    @Test
    void transfer() {
    }
}