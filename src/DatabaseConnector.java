import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    
    public static Connection conectar() throws SQLException {
        // Configuración para PHPMyAdmin (MySQL)
        String url = "jdbc:mysql://localhost:3306/contratos_db";
        String user = "root";  // Usuario por defecto de XAMPP
        String password = "54321ROOT";  // Contraseña por defecto de XAMPP (vacía)
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
        }
    }
}