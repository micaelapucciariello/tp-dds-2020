package GeSoc.MedioDePago;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MedioDePago {
    @Id
    @GeneratedValue
    public Long id;

    public abstract String getTipo();

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public Long getId() {
        return id;
    }

}
