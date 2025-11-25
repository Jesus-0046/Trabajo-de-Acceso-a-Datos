import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class XMLDownloader {
    
    public static boolean descargarXML(String url, String localPath) {
        try {
            System.out.println("Descargando XML desde: " + url);
            
            URL website = new URL(url);
            try (InputStream in = website.openStream();
                 ReadableByteChannel rbc = Channels.newChannel(in);
                 FileOutputStream fos = new FileOutputStream(localPath)) {
                
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
            
            System.out.println("XML descargado exitosamente en: " + localPath);
            return true;
            
        } catch (Exception e) {
            System.err.println("Error descargando XML: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean archivoExiste(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.length() > 0;
    }
}