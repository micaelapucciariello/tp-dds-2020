package GeSoc.Usuario;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TipoUsuario {
    @Id
    @GeneratedValue
    public Long id;

}
