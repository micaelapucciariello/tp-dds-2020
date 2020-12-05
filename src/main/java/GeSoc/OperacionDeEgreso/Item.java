package GeSoc.OperacionDeEgreso;

import javax.persistence.*;
import java.math.BigDecimal;
import static java.util.Objects.requireNonNull;

@Entity
public class Item {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    OperacionDeEgreso egresoAsociado;

    @ManyToOne(cascade = CascadeType.PERSIST)
    Moneda moneda;

    public String detalle;
    public String descripcion;
    public BigDecimal valor;

    public Item(Moneda moneda, BigDecimal valor, OperacionDeEgreso egresoAsociado) {
        this.moneda = requireNonNull(moneda, "Cada item debe estar asociado obligatoriamente a una moneda");
        this.egresoAsociado = requireNonNull(egresoAsociado, "Cada item debe estar asociado obligatoriamente a un egreso");
        this.valor = valor;
    }

    public Item() {

    }

    public BigDecimal getValor() {
        return valor;
    }
}
