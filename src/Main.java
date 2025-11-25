import java.sql.Connection;
import java.util.List;

public class Main {
    private static final String XML_LOCAL_PATH = "contratos.xml";
    private static final String XML_OUTPUT_PATH = "contratos_sin_tipo.xml";
    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        
        try {
            System.out.println("=== PROCESAMIENTO DE CONTRATOS MENORES ===");
            System.out.println("📁 Archivo XML: " + XML_LOCAL_PATH);
            
            // 1. Lectura del XML
            System.out.println("📖 Leyendo contratos desde XML...");
            List<Contrato> contratos = XMLReader.leerContratos(XML_LOCAL_PATH);
            System.out.println("✅ Contratos leídos: " + contratos.size());
            
            if (contratos.isEmpty()) {
                throw new Exception("No se encontraron contratos en el XML");
            }
            
            // Mostrar muestra de datos
            System.out.println("\n📊 MUESTRA DE DATOS LEÍDOS:");
            for (int i = 0; i < Math.min(3, contratos.size()); i++) {
                Contrato c = contratos.get(i);
                System.out.println("   " + (i+1) + ". " + c.getAdjudicatario() + 
                                 " - " + c.getImporte() + " - " + c.getTipoContrato());
            }
            
            // 2. Conexión y almacenamiento en BD
            System.out.println("\n🗄️ Conectando con la base de datos...");
            Connection conn = DatabaseConnector.conectar();
            
            System.out.println("💾 Almacenando en base de datos...");
            DatabaseService.guardarContratos(conn, contratos);
            
            // 3. Generación de XML filtrado (sin tipoContrato)
            System.out.println("\n📄 Generando XML de salida (sin 'tipoContrato')...");
            XMLWriter.escribirContratosSinTipo(contratos, XML_OUTPUT_PATH);
            
            // 4. Cierre de recursos
            conn.close();
            
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000;
            
            // Resumen final
            System.out.println("\n🎉 PROCESO COMPLETADO EXITOSAMENTE");
            System.out.println("=========================================");
            System.out.println("📈 RESUMEN EJECUCIÓN:");
            System.out.println("   • Contratos procesados: " + contratos.size());
            System.out.println("   • Archivo generado: " + XML_OUTPUT_PATH);
            System.out.println("   • Tiempo total: " + duration + " segundos");
            System.out.println("   • Base de datos: MySQL/PhpMyAdmin");
            System.out.println("   • Campo excluido: 'tipoContrato'");
            System.out.println("=========================================");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR EN EL PROCESO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}