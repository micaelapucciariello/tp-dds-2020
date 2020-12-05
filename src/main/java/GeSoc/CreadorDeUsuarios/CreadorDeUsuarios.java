package GeSoc.CreadorDeUsuarios;

import GeSoc.Usuario.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class CreadorDeUsuarios {
	private static CreadorDeUsuarios instancia = null;
	static List<ValidacionPassword> validaciones = Arrays.asList(new CaracterRepetitivo(3), new Longitud(8, 64), new MostCommon());

	private CreadorDeUsuarios() {}

	public static CreadorDeUsuarios getInstance() {
		if (instancia == null) {
			instancia = new CreadorDeUsuarios();
		}
		return instancia;
	}

	public Usuario crearUsuario(String user, String pass, TipoUsuario tipo) {
		validarPassword(pass);
		byte[] passEncriptada = encriptarPassword(pass);
		return new Usuario(user, passEncriptada, tipo);
	}

	void validarPassword(String password) {
		for (ValidacionPassword validacion : validaciones) {
			validacion.validar(password);
		}
	}

	public byte[] encriptarPassword(String password) {
		try {
			byte[] datos = password.getBytes(StandardCharsets.UTF_8);
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			return messageDigest.digest(datos);
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}


}
