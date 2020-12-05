package GeSoc.OperacionDeEgreso;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Entity
public class Presupuesto {
    @Id
    @GeneratedValue
    public Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Presupuesto_id")
    List<Item> items = new ArrayList<>();

    public Presupuesto(List<Item> items) {
        this.items = items;
    }

    public Presupuesto() {

    }

    public BigDecimal valorTotal() {
        return items
                .stream()
                .map(Item::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void agregarItem(Item item) {
    	items.add(item);    
    }
    
    public void eliminarItem(int id) {
    	items.remove(id);
    }

}
