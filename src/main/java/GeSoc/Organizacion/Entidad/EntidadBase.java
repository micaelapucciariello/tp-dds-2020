package GeSoc.Organizacion.Entidad;


import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Usuario.Usuario;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class EntidadBase extends Entidad {

	public String descripcion;
	public String nombreFicticio;

	@ManyToOne(cascade = CascadeType.ALL)
	public EntidadJuridica entidadJuridica;

	public EntidadBase(String descripcion, String nombreFicticio, EntidadJuridica entidadJuridica, Categoria categoria, Usuario usuario) {
		this.descripcion = descripcion;
		this.nombreFicticio = nombreFicticio;
		this.entidadJuridica = entidadJuridica;
		this.categoria = categoria;
		this.usuario = usuario;
	}

	public EntidadBase() {

	}
	public String getNombreFicticio() {
		return nombreFicticio;
	}
	public void asignarEntJuridica(EntidadJuridica entjur) {
		categoria.darPertenencia(entjur, this);
	}

	public EntidadJuridica getEntidadJuridica() {
		return entidadJuridica;
	}
	
	public void agregarEntJur(EntidadJuridica entJ) {
		entidadJuridica = entJ;
	}


}
