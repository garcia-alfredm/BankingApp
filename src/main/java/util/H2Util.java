package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2Util {

    public static String url = "jdbc:h2:./h2/db";
    public static String username = "sa";
    public static String password = "sa";

    public static void createClientsTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "CREATE TABLE clients(\n" +
                    "\tid serial PRIMARY KEY,\n" +
                    "\tname varchar(50) NOT NULL,\n" +
                    "\tclient_date_creation timestamp DEFAULT now()\n" +
                    ")";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void dropClientsTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "DROP TABLE clients;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void createAccountsTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "CREATE TABLE accounts(\n" +
                    "\tid serial PRIMARY KEY,\n" +
                    "\tclient_id_fk int REFERENCES clients(id),\n" +
                    "\tbalance double PRECISION DEFAULT 0.00,\n" +
                    "\taccount_date_creation timestamp DEFAULT now()\n" +
                    ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void dropAccountsTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "DROP TABLE accounts;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
