import java.math.BigDecimal;
import java.util.Date;

public class Contrato {
    private String nif;
    private String adjudicatario;
    private String objetoGenerico;
    private String objeto;
    private Date fechaAdjudicacion;
    private BigDecimal importe;
    private String proveedoresConsultados;
    private String tipoContrato;
    
    // Constructor
    public Contrato() {}
    
    // Getters y Setters
    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    
    public String getAdjudicatario() { return adjudicatario; }
    public void setAdjudicatario(String adjudicatario) { this.adjudicatario = adjudicatario; }
    
    public String getObjetoGenerico() { return objetoGenerico; }
    public void setObjetoGenerico(String objetoGenerico) { this.objetoGenerico = objetoGenerico; }
    
    public String getObjeto() { return objeto; }
    public void setObjeto(String objeto) { this.objeto = objeto; }
    
    public Date getFechaAdjudicacion() { return fechaAdjudicacion; }
    public void setFechaAdjudicacion(Date fechaAdjudicacion) { this.fechaAdjudicacion = fechaAdjudicacion; }
    
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    
    public String getProveedoresConsultados() { return proveedoresConsultados; }
    public void setProveedoresConsultados(String proveedoresConsultados) { this.proveedoresConsultados = proveedoresConsultados; }
    
    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }
    
    @Override
    public String toString() {
        return String.format("Contrato{NIF=%s, Adjudicatario=%s, Importe=%s, Fecha=%s}", 
                           nif, adjudicatario, importe, fechaAdjudicacion);
    }
}