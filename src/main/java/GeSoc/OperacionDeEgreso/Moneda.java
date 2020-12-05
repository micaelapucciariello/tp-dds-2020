package GeSoc.OperacionDeEgreso;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Moneda {
    @Id
    @GeneratedValue
    public Long id;

    String siglas;
    String descripcion;
    String simbolo;
    int lugaresDecimal;

    public Moneda(String siglas, String descripcion, String simbolo, int lugaresDecimal) {
        this.siglas = siglas;
        this.descripcion = descripcion;
        this.simbolo = simbolo;
        this.lugaresDecimal = lugaresDecimal;
    }

    public Moneda() {

    }
}
