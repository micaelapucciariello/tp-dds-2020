package GeSoc.OperacionDeEgreso;

import javax.persistence.*;

@Entity
public class DireccionPostal {
    @Id
    @GeneratedValue
    public Long id;

    @OneToOne(cascade = CascadeType.ALL)
    Direccion direccion;

    String ciudad, provincia, pais;
    int codigoPostal;

    public DireccionPostal(Direccion direccion, int codigoPostal, String ciudad, String provincia, String pais) {
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.pais = pais;
    }

    public DireccionPostal() {

    }
}