package services;

import dao.ClientsDao;
import dao.ClientsDaoImpl;
import models.Clients;

import java.util.List;

public class ClientsService {
    ClientsDao clientsDao;

    public ClientsService(){ this.clientsDao = new ClientsDaoImpl(); }

    public ClientsService(ClientsDao clientsDao){ this.clientsDao = clientsDao; }

    public List<Clients> getAllClients(){ return this.clientsDao.getAllClients();};

    public Clients getOneClient(Integer clientId){ return this.clientsDao.getOneClient(clientId); }

    public void createClient(Clients client){
        this.clientsDao.createClient(client);
    }

    public void updateClient(Integer clientId, String name){
        this.clientsDao.updateClient(clientId, name);
    }

    public void deleteClient(Integer clientId){ this.clientsDao.deleteClient(clientId);}
}
