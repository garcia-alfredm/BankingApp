package dao;

import models.Clients;

import java.util.List;

public class ClientsDaoImpl implements ClientsDao{
    String url;
    String username;
    String password;

    public ClientsDaoImpl() {
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/banking";
        this.username = System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public ClientsDaoImpl(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Clients> getAllClients() {
        return null;
    }

    @Override
    public Clients getOneClient(Integer clientId) {
        return null;
    }

    @Override
    public void createClient(Clients client) {

    }

    @Override
    public void updateClient(Integer clientId) {

    }

    @Override
    public void deleteClient(Integer clientId) {

    }
}
