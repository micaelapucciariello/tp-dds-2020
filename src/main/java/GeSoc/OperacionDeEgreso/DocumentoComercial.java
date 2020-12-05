package GeSoc.OperacionDeEgreso;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DocumentoComercial {
    @Id
    @GeneratedValue
    public Long id;

    @Enumerated
    TipoDocumento tipoDocumento;

    int numeroDeOperacion;

    public DocumentoComercial(TipoDocumento tipoDocumento, int numeroDeOperacion) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDeOperacion = numeroDeOperacion;
    }

    public DocumentoComercial() {

    }

    public String getTipoDocumento() {
        return tipoDocumento.name();
    }

    public int getNumeroDeOperacion() {
        return numeroDeOperacion;
    }
}
