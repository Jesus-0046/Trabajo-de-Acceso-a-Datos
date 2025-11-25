import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {
    
    public static List<Contrato> leerContratos(String filePath) throws Exception {
        List<Contrato> contratos = new ArrayList<>();
        
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                private Contrato contrato;
                private String currentElement;
                private StringBuilder currentValue = new StringBuilder();
                
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    currentValue.setLength(0);
                    if ("contrato".equalsIgnoreCase(qName)) {
                        contrato = new Contrato();
                    }
                    currentElement = qName;
                }
                
                public void characters(char[] ch, int start, int length) throws SAXException {
                    currentValue.append(ch, start, length);
                }
                
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (contrato == null) return;
                    
                    String value = currentValue.toString().trim();
                    
                    switch (qName.toLowerCase()) {
                        case "nif":
                            contrato.setNif(value);
                            break;
                        case "adjudicatario":
                            contrato.setAdjudicatario(value);
                            break;
                        case "objectogenerico":
                            contrato.setObjetoGenerico(value);
                            break;
                        case "objeto":
                            contrato.setObjeto(value);
                            break;
                        case "fechaadjudicacion":
                            try {
                                // El formato es: 2016-01-02T00:00:00.000
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                contrato.setFechaAdjudicacion(dateFormat.parse(value));
                            } catch (Exception e) {
                                System.err.println("Error parseando fecha: " + value);
                            }
                            break;
                        case "importe":
                            try {
                                // DE ESTO 110,50 € A 110.50
                                String cleanValue = value.replace("€", "")
                                                       .replace(".", "")
                                                       .replace(",", ".")
                                                       .trim();
                                contrato.setImporte(new BigDecimal(cleanValue));
                            } catch (NumberFormatException e) {
                                System.err.println("Error parseando importe: " + value);
                            }
                            break;
                        case "proveedoresconsultados":
                            contrato.setProveedoresConsultados(value);
                            break;
                        case "tipocontrato":
                            contrato.setTipoContrato(value);
                            break;
                        case "contrato":
                            contratos.add(contrato);
                            contrato = null;
                            break;
                    }
                }
            };
            
            saxParser.parse(new File(filePath), handler);
            
        } catch (Exception e) {
            throw new Exception("Error leyendo XML: " + e.getMessage(), e);
        }
        
        return contratos;
    }
}