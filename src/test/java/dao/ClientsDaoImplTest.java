package dao;

import models.Clients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.H2Util;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientsDaoImplTest {

    ClientsDao clientsDao;

    ClientsDaoImplTest(){
        this.clientsDao = new ClientsDaoImpl(H2Util.url, H2Util.username, H2Util.password);
    }

    @BeforeEach
    void setUp() throws SQLException{
        H2Util.createTable();
    }

    @AfterEach
    void tearDown(){
        H2Util.dropTable();
    }

    @Test
    void getAllClients() {
        //assign
        List<Clients> expectedResults = new ArrayList<>();
        expectedResults.add(new Clients(1, "Michael", new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Clients(2, "Jonathan", new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Clients(3, "Lilly", new Date(Calendar.getInstance().getTime().getTime())));
        clientsDao.createClient(expectedResults.get(0));
        clientsDao.createClient(expectedResults.get(1));
        clientsDao.createClient(expectedResults.get(2));

        //act
        List<Clients> actualResults = clientsDao.getAllClients();

        //assert
        assertEquals(expectedResults.toString(), actualResults.toString());
    }

    @Test
    void getOneClient() {
        //assign
        List<Clients> expectedResults = new ArrayList<>();
        expectedResults.add(new Clients(1, "Michael", new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Clients(2, "Jonathan", new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Clients(3, "Lilly", new Date(Calendar.getInstance().getTime().getTime())));
        clientsDao.createClient(expectedResults.get(0));
        clientsDao.createClient(expectedResults.get(1));
        clientsDao.createClient(expectedResults.get(2));

        //act
        Clients actualResults = clientsDao.getOneClient(2);

        //assert
        assertEquals(expectedResults.get(1).toString(), actualResults.toString());
    }

    @Test
    void createClient() {
        //assign
        List<Clients> expectedResults = new ArrayList<>();
        expectedResults.add(new Clients(1, "Michael", new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Clients(2, "Jonathan", new Date(Calendar.getInstance().getTime().getTime())));
        expectedResults.add(new Clients(3, "Lilly", new Date(Calendar.getInstance().getTime().getTime())));
        clientsDao.createClient(expectedResults.get(0));
        clientsDao.createClient(expectedResults.get(1));
        clientsDao.createClient(expectedResults.get(2));

        //act
        Clients actualResults = clientsDao.getOneClient(1);

        //assert
        assertEquals(expectedResults.get(0).toString(), actualResults.toString());
    }

    @Test
    void updateClient() {
        //arrange
        Clients clientToPass = new Clients(1, "Michael", new Date(Calendar.getInstance().getTime().getTime()));
        Clients expectedResult = new Clients(1, "Daniel", new Date(Calendar.getInstance().getTime().getTime()));
        clientsDao.createClient(clientToPass);

        //act
        clientsDao.updateClient(clientToPass.getId(), "Daniel");
        Clients actualResult = clientsDao.getOneClient(clientToPass.getId());

        //assert
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    void deleteClient() {
    }
}