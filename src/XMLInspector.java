import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class XMLInspector {
    
    public static void inspeccionarEstructura(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            Set<String> elementosUnicos = new HashSet<>();
            
            DefaultHandler handler = new DefaultHandler() {
                private int totalElementos = 0; // Movido dentro de la clase anónima
                
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    elementosUnicos.add(qName);
                    totalElementos++;
                    
                    // Mostrar los primeros elementos para entender la estructura
                    if (totalElementos < 50) {
                        System.out.println("Elemento: " + qName);
                        for (int i = 0; i < attributes.getLength(); i++) {
                            System.out.println("  Atributo: " + attributes.getQName(i) + " = " + attributes.getValue(i));
                        }
                    }
                }
                
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String value = new String(ch, start, length).trim();
                    if (!value.isEmpty() && totalElementos < 50) {
                        System.out.println("  Valor: " + value);
                    }
                }
            };
            
            System.out.println("=== INSPECCIÓN DE ESTRUCTURA XML ===");
            saxParser.parse(new File(filePath), handler);
            
            System.out.println("\n=== ELEMENTOS ÚNICOS ENCONTRADOS ===");
            for (String elemento : elementosUnicos) {
                System.out.println("- " + elemento);
            }
            
        } catch (Exception e) {
            System.err.println("Error inspeccionando XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}