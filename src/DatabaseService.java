import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseService {
    
    public static void guardarContratos(Connection conn, List<Contrato> contratos) throws SQLException {
        
        String dropTableSQL = "DROP TABLE IF EXISTS contratos";
        try (PreparedStatement stmt = conn.prepareStatement(dropTableSQL)) {
            stmt.execute();
            System.out.println("✅ Tabla existente eliminada");
        } catch (SQLException e) {
            System.out.println("ℹ️ No había tabla existente o no se pudo eliminar");
        }
        
       
        String createTableSQL = "CREATE TABLE contratos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nif VARCHAR(20), " +
                "adjudicatario VARCHAR(500), " +
                "objeto_generico VARCHAR(300), " +
                "objeto TEXT, " +
                "fecha_adjudicacion DATETIME, " +
                "importe DECIMAL(15,2), " +
                "proveedores_consultados TEXT, " +
                "tipo_contrato VARCHAR(100), " +
                "fecha_importacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        
        try (PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
            stmt.execute();
            System.out.println("✅ Tabla 'contratos' creada con estructura correcta");
        }
        
        // Insertar contratos
        String insertSQL = "INSERT INTO contratos (nif, adjudicatario, objeto_generico, objeto, " +
                         "fecha_adjudicacion, importe, proveedores_consultados, tipo_contrato) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            int totalInsertados = 0;
            
            for (Contrato contrato : contratos) {
                stmt.setString(1, contrato.getNif());
                stmt.setString(2, contrato.getAdjudicatario());
                stmt.setString(3, contrato.getObjetoGenerico());
                stmt.setString(4, contrato.getObjeto());
                
                if (contrato.getFechaAdjudicacion() != null) {
                    stmt.setTimestamp(5, new java.sql.Timestamp(contrato.getFechaAdjudicacion().getTime()));
                } else {
                    stmt.setNull(5, java.sql.Types.TIMESTAMP);
                }
                
                if (contrato.getImporte() != null) {
                    stmt.setBigDecimal(6, contrato.getImporte());
                } else {
                    stmt.setNull(6, java.sql.Types.DECIMAL);
                }
                
                stmt.setString(7, contrato.getProveedoresConsultados());
                stmt.setString(8, contrato.getTipoContrato());
                
                stmt.executeUpdate();
                totalInsertados++;
                
                // Mostrar progresos
                if (totalInsertados % 10 == 0) {
                    System.out.println("📦 " + totalInsertados + " contratos insertados...");
                }
            }
            
            System.out.println("Total contratos insertados en BD: " + totalInsertados);
        }
    }
}