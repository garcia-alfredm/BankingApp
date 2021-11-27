package dao;

import models.Clients;

import java.util.List;

public interface ClientsDao {
    List<Clients> getAllClients();

    Clients getOneClient(Integer clientId);

    void createClient(Clients client);

    void updateClient(Integer clientId, String name);

    void deleteClient(Integer clientId);

}
