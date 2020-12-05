package GeSoc.Organizacion.Entidad;

import javax.persistence.Entity;

import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Usuario.Usuario;

@Entity
public class OSC extends EntidadJuridica {

    int cantEgresosAceptados;

    public OSC() {

    }

    public OSC(String razonSocial, int cantEgresosAceptados, Categoria categoria, Usuario user) {
        this.cantEgresosAceptados = cantEgresosAceptados;
        this.razonSocial = razonSocial;
        this.categoria = categoria;
        this.usuario = user;
    }

}
