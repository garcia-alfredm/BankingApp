package dao;

import models.Clients;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientsDaoImpl implements ClientsDao{
    String url;
    String username;
    String password;

    Logger logger = Logger.getLogger(ClientsDaoImpl.class);

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
        List<Clients> clients = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM clients;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                clients.add(new Clients(rs.getInt(1), rs.getString(2), rs.getDate(3)));
            }
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            logger.error(throwables);
        }
        return clients;
    }

    @Override
    public Clients getOneClient(Integer clientId){
        Clients client = null;

        //try create active connection to databse
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            //use ? to replace variable we want to supply
            String sql = "SELECT * FROM clients WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                client = new Clients(rs.getInt(1), rs.getString(2), rs.getDate(3));
            }

        } catch(SQLException e){
            //e.printStackTrace();
            logger.error(e);
        }
        return client;
    }

    @Override
    public void createClient(Clients client) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO clients VALUES(DEFAULT, ?, DEFAULT);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, client.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            logger.error(e);
        }
    }

    @Override
    public void updateClient(Integer clientId, String name) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE clients SET name = ? WHERE id = ?;";
            PreparedStatement ps =conn.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, clientId);

            ps.executeUpdate();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            logger.error(throwables);
        }
    }

    @Override
    public void deleteClient(Integer clientId) {

    }
}
