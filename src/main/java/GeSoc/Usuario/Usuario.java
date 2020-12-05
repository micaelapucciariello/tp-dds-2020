package GeSoc.Usuario;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Usuario {
    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    public Long id;

    String user;
    byte[] password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Usuario_id")
    List<Mensaje> bandejaDeMensajes = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    TipoUsuario tipo;

    public Usuario(String usuario, byte[] pass, TipoUsuario tipo) {
        this.user = usuario;
        this.password = pass;
        this.tipo = tipo;
    }

    public Usuario() {

    }

    public void suscribirseAOperacion(OperacionDeEgreso egreso) {
        egreso.agregarUsuario(this);
    }
    
    public void recibirMensaje(Mensaje mensaje){
        bandejaDeMensajes.add(mensaje);
    }

    public List<Mensaje> getBandejaDeMensajes() {
    	return bandejaDeMensajes;
    }

    public byte[] getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }
}
