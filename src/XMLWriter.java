import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class XMLWriter {
    
    public static void escribirContratosSinTipo(List<Contrato> contratos, String outputFile) throws Exception {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream(outputFile), "UTF-8");
        
        try {
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\n");
            writer.writeStartElement("contratos");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            
            for (Contrato contrato : contratos) {
                writer.writeCharacters("\n  ");
                writer.writeStartElement("contrato");
                
                escribirElemento(writer, "nif", contrato.getNif());
                escribirElemento(writer, "adjudicatario", contrato.getAdjudicatario());
                escribirElemento(writer, "objetoGenerico", contrato.getObjetoGenerico());
                escribirElemento(writer, "objeto", contrato.getObjeto());
                
                if (contrato.getFechaAdjudicacion() != null) {
                    escribirElemento(writer, "fechaAdjudicacion", dateFormat.format(contrato.getFechaAdjudicacion()));
                }
                
                if (contrato.getImporte() != null) {
                    String importeFormateado = String.format("%.2f €", contrato.getImporte())
                                              .replace(".", ",");
                    escribirElemento(writer, "importe", importeFormateado);
                }
                
                escribirElemento(writer, "proveedoresConsultados", contrato.getProveedoresConsultados());
                
             
                
                writer.writeCharacters("\n  ");
                writer.writeEndElement();
            }
            
            writer.writeCharacters("\n");
            writer.writeEndElement();
            writer.writeEndDocument();
            
        } finally {
            writer.close();
        }
        
        System.out.println("XML generado sin campo 'tipoContrato': " + outputFile);
    }
    
    private static void escribirElemento(XMLStreamWriter writer, String elementName, String value) throws Exception {
        if (value != null && !value.trim().isEmpty()) {
            writer.writeCharacters("\n    ");
            writer.writeStartElement(elementName);
            writer.writeCharacters(value);
            writer.writeEndElement();
        }
    }
}