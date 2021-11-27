package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2Util {

    public static String url = "jdbc:h2:./h2/db";
    public static String username = "sa";
    public static String password = "sa";

    public static void createTable(){
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

    public static void dropTable(){
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "DROP TABLE clients";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.executeUpdate();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
