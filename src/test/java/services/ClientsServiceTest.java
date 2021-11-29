package services;

import dao.ClientsDao;
import models.Clients;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientsServiceTest {

    ClientsDao clientsDao = Mockito.mock(ClientsDao.class);

    ClientsService clientsService;

    public ClientsServiceTest(){ this.clientsService = new ClientsService(clientsDao);}

    @Test
    void getAllClients() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        List<Clients> clients = new ArrayList<>();
        clients.add(new Clients(1, "George", date));
        clients.add(new Clients(2, "Michael", date));
        clients.add(new Clients(3, "Austin", date));
        List<Clients> expectedValue = clients;
        Mockito.when(clientsDao.getAllClients()).thenReturn(clients);

        //act
        List<Clients> actualResults = clientsService.getAllClients();

        //assert
        assertEquals(expectedValue.toString(), actualResults.toString());
    }

    @Test
    void getOneClient() {
        //arrange
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients expectedResult = new Clients(1, "George", date);
        Mockito.when(clientsDao.getOneClient(1)).thenReturn(expectedResult);

        //act
        Clients actualResult = clientsService.getOneClient(expectedResult.getId());

        //assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createClient() {
        //assign
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients expectedResult = new Clients(1, "George", date);

        //act
        clientsService.createClient(expectedResult);

        //assert
        Mockito.verify(clientsDao, Mockito.times(1)).createClient(expectedResult);
    }

    @Test
    void updateClient() {
        //assign
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients expectedResult = new Clients(1, "George", date);
        clientsService.createClient(expectedResult);

        //act
        clientsService.updateClient(expectedResult.getId(), "Michael");

        //assert
        Mockito.verify(clientsDao, Mockito.times(1)).updateClient(1, "Michael");
    }

    @Test
    void deleteClient() {
        //assign
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        Clients expectedResult = new Clients(1, "George", date);
        clientsService.createClient(expectedResult);

        //act
        clientsService.deleteClient(expectedResult.getId());

        //assert
        Mockito.verify(clientsDao, Mockito.times(1)).deleteClient(expectedResult.getId());
    }
}