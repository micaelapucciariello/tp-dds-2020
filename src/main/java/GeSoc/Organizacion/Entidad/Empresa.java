package GeSoc.Organizacion.Entidad;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import GeSoc.Organizacion.EntidadJuridica.Categoria.Categoria;
import GeSoc.Organizacion.TipoEmpresa.TipoEmpresa;
import GeSoc.Usuario.Usuario;

@Entity
public class Empresa extends EntidadJuridica {

	@Enumerated(EnumType.ORDINAL)
	public TipoEmpresa tipo;

	public double CUIT;

	public Empresa(TipoEmpresa tipo, String razonSocial, double CUIT, Categoria categoria, Usuario user) {
		this.tipo = tipo;
		this.razonSocial = razonSocial;
		this.CUIT = CUIT;
		this.categoria = categoria;
		this.usuario = user;
	}

	public Empresa() {

	}

}
