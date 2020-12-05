package GeSoc.OperacionDeEgreso;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Direccion {
    @Id
    @GeneratedValue
    public Long id;

    public String calle;
    public int altura;
    public int piso;

    public Direccion(String calle, int altura, int piso) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
    }

    public Direccion(String calle, int altura) {
        this(calle, altura, 0);
    }

    public Direccion() {

    }
}
