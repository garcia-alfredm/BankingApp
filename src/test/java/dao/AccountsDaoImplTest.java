package dao;

import models.Accounts;
import models.Clients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.util.resources.CalendarData;
import util.H2Util;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountsDaoImplTest {

    AccountsDao accountsDao;
    ClientsDao clientsDao;

    public AccountsDaoImplTest(){
        this.accountsDao = new AccountsDaoImpl(H2Util.url, H2Util.username, H2Util.password);
        this.clientsDao = new ClientsDaoImpl(H2Util.url, H2Util.username, H2Util.password);
    }

    @BeforeEach
    void setUp(){
        H2Util.createClientsTable();
        H2Util.createAccountsTable();
    }

    @AfterEach
    void tearDown(){
        H2Util.dropAccountsTable();
        H2Util.dropClientsTable();

    }

    @Test
    void getAllAccounts() {
        //assign
        List<Accounts> expectedResults = new ArrayList<>();
        Clients client = new Clients(1, "Michael", new Date(Calendar.getInstance().getTime().getTime()));
        clientsDao.createClient(client);
        expectedResults.add(new Accounts(1, 1, 0.00, new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Accounts(2, 1, 20.00, new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Accounts(3, 1, 100.00, new Date(Calendar.getInstance().getTime().getTime())));
        accountsDao.createAccount(expectedResults.get(0).getId(), 0.00);
        accountsDao.createAccount(expectedResults.get(0).getId(), 20.00);
        accountsDao.createAccount(expectedResults.get(0).getId(), 100.00);

        //act
        List<Accounts> actualResults = accountsDao.getAllAccounts();

        //assert
        assertEquals(expectedResults.toString(), actualResults.toString());
    }

    @Test
    void getOneAccount() {
        //assign
        List<Accounts> expectedResults = new ArrayList<>();
        Clients client = new Clients(1, "Michael", new Date(Calendar.getInstance().getTime().getTime()));
        clientsDao.createClient(client);
        expectedResults.add(new Accounts(1, 1, 0.00, new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Accounts(2, 1, 20.00, new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Accounts(3, 1, 100.00, new Date(Calendar.getInstance().getTime().getTime())));
        Double value = 0.00;

        //act
        accountsDao.createAccount(clientsDao.getOneClient(1).getId(), value);
        accountsDao.createAccount(clientsDao.getOneClient(1).getId(), 20.00);
        accountsDao.createAccount(clientsDao.getOneClient(1).getId(), 100.00);
        Accounts actualResults = accountsDao.getOneAccount(2, clientsDao.getOneClient(1).getId());

        //assert
        assertEquals(expectedResults.get(1).toString(), actualResults.toString());
    }

    @Test
    void createAccount() {
        //assign
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients client = new Clients(1, "Michael", date);
        clientsDao.createClient(client);
        Accounts expectedResults = new Accounts(1, 1, 0.00, date);
        Double value = 0.00;

        //act
        accountsDao.createAccount(expectedResults);
        Accounts actualResults = accountsDao.getOneAccount(1, clientsDao.getOneClient(1).getId());

        //assert
        assertEquals(expectedResults.toString(), actualResults.toString());
    }

    @Test
    void updateAccount() {
        //assign
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients client = new Clients(1, "Michael", date);
        clientsDao.createClient(client);
        Accounts expectedResults = new Accounts(1, 1, 20.00, date);
        accountsDao.createAccount(clientsDao.getOneClient(1).getId(), 0.00);

        //act
        accountsDao.updateAccount(1, 20.00, clientsDao.getOneClient(1).getId());
        Accounts actualResults = accountsDao.getOneAccount(1,clientsDao.getOneClient(1).getId());
        //assert
        assertEquals(expectedResults.toString(), actualResults.toString());
    }

    @Test
    void deleteAccount() {
        //assign
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients client = new Clients(1, "Michael", date);
        clientsDao.createClient(client);

        List<Accounts> expectedResults = new ArrayList<>();
        expectedResults.add(new Accounts(1, 1, 0.00, date));
        expectedResults.add(new Accounts(2, 1, 20.00, date));
        expectedResults.add(new Accounts(3, 1, 100.00, date));
        accountsDao.createAccount(expectedResults.get(0));
        accountsDao.createAccount(expectedResults.get(1));
        accountsDao.createAccount(expectedResults.get(2));

        //act
        //remove 2nd account element
        expectedResults.remove(1);
        accountsDao.deleteAccount(2, 1);
        List<Accounts> actualResults = accountsDao.getAllAccounts();

        //assert
        assertEquals(expectedResults.toString(), actualResults.toString());
    }
}