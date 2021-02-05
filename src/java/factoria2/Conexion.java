package factoria2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private Connection conexion;

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String SERVERNAME = "localhost"; //"127.0.0.1";
    private static final String PORT = "3306";
    private static final String DATABASE = "factoria2";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://"
            + SERVERNAME + ":"
            + PORT + "/"
            + DATABASE + "?autoReconnect=true&useSSL=false";

    public Conexion() {

    }

    public Conexion(boolean flag) {
        this.setConexion(flag);
    }

    public void setConexion(boolean flag) {
        try {
            if (flag) {
                Class.forName(DRIVER);
                conexion = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
                String query = "use " + DATABASE;//LENGUAJE SQL
                Statement sql = conexion.createStatement();
                sql.executeUpdate(query);
            } else {
                conexion.close();
            }
        } catch (SQLException e) {
            conexion = null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Connection getConexion() {
        return conexion;
    }
}
