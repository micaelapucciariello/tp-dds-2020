package GeSoc.Organizacion.EntidadJuridica.Categoria;

import GeSoc.OperacionDeEgreso.OperacionDeEgreso;
import GeSoc.Organizacion.Entidad.Entidad;
import GeSoc.Organizacion.Entidad.EntidadBase;
import GeSoc.Organizacion.Entidad.EntidadJuridica;
import GeSoc.Usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class Categoria {
    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    public Usuario usuario;

    @ManyToMany(cascade = CascadeType.PERSIST)
    List<Funcionalidad> funcionalidades;

    public String nombre;

    public Categoria(List<Funcionalidad> funcionalidades, String nombre, Usuario user) {
        this.funcionalidades = funcionalidades;
        this.nombre = nombre;
        this.usuario = user;
    }

    public Categoria() {

    }

    public String getNombre() {
        return nombre;
    }

    public void aceptarEgreso(Entidad entidad,OperacionDeEgreso op) {
    	funcionalidades.forEach(func -> func.puedeAceptarEgreso(entidad));
    	op.validar();
		if(op.estaAceptada()){
			entidad.agregarOpEgreso(op);
		}
    }
    
    public void darPertenencia(EntidadJuridica entJ, EntidadBase entB) {
    	funcionalidades.forEach(func -> func.puedePertenecerA(entJ));
    	entB.agregarEntJur(entJ);
    }
    
    public void agregarEntBase(EntidadBase entB, EntidadJuridica entJ) {
    	funcionalidades.forEach(Funcionalidad::puedeAgregarEntBase);
    	entB.agregarEntJur(entJ);
    	
    }

    public void agregarFuncionalidad(Funcionalidad funcionalidad) {
        funcionalidades.add(funcionalidad);
    }
    public void eliminarFuncionalidad(Funcionalidad funcionalidad) {
        funcionalidades.remove(funcionalidad);
    }

    public List<Funcionalidad> getFuncionalidades() {
        return funcionalidades;
    }

}
