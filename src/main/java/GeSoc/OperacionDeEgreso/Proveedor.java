package GeSoc.OperacionDeEgreso;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Proveedor {
    @Id
    @GeneratedValue
    public Long id;

    public String nombre;
    public String razonSocial;

    @ManyToOne
    DireccionPostal direccionPostal;

    public Proveedor(String nombre, String razonSocial) {
        this.nombre = nombre;
        this.razonSocial = razonSocial;
    }

    public Proveedor() {

    }

    public Long getId() {
        return id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

}
