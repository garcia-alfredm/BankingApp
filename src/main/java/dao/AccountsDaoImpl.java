package dao;

import models.Accounts;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountsDaoImpl implements AccountsDao {
    String url;
    String username;
    String password;

    Logger logger = Logger.getLogger(ClientsDaoImpl.class);

    public AccountsDaoImpl(){
        this.url = "jdbc:postgresql://" + System.getenv("AWS_RDS_ENDPOINT") + "/banking";
        this.username = System.getenv("RDS_USERNAME");
        this.password = System.getenv("RDS_PASSWORD");
    }

    public AccountsDaoImpl(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    @Override
    public List<Accounts> getAllAccounts() {
        List<Accounts> accounts = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM accounts;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                accounts.add(new Accounts(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDate(4)));
            }

        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return accounts;
    }

    @Override
    public Accounts getOneAccount(Integer accountId, Integer clientIdFk) {
        Accounts account = null;
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "SELECT * FROM accounts WHERE client_id_fk = ? GROUP BY id HAVING id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, clientIdFk);
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                account = new Accounts(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDate(4));
            }
        } catch (SQLException throwables) {
            logger.error(throwables);
        }
        return account;
    }

    @Override
    public void createAccount(Integer clientIdFk, Double amount) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO accounts VALUES(DEFAULT, ?, ?, DEFAULT);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientIdFk);
            ps.setDouble(2, amount);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }
    @Override
    public void createAccount(Accounts account) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "INSERT INTO accounts VALUES(DEFAULT, ?, ?, DEFAULT);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getClientIdFk());
            ps.setDouble(2, account.getBalance());

            ps.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }

    @Override
    public void updateAccount(Integer accountId, Double amount, Integer clientIdFk) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "UPDATE accounts SET balance = ? WHERE id = ? AND client_id_fk = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, accountId);
            ps.setInt(3, clientIdFk);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }

    @Override
    public void deleteAccount(Integer accountId, Integer clientIdFk) {
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            String sql = "DELETE FROM accounts WHERE id = ? AND client_id_fk = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setInt(2, clientIdFk);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            logger.error(throwables);
        }
    }
}
