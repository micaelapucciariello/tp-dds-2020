package GeSoc.MedioDePago;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class Cuenta extends MedioDePago {

    public Cuenta() {

    }

    @Override
    public String getTipo(){
        return "CUENTA";
    }
}

