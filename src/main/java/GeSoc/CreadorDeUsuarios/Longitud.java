package GeSoc.CreadorDeUsuarios;

import GeSoc.exceptions.PasswordCortaException;
import GeSoc.exceptions.PasswordLargaException;

public class Longitud implements ValidacionPassword {
    int longitudMaxima;
    int longitudMinima;

    public Longitud(int longitudMinima, int longitudMaxima) {
        this.longitudMinima = longitudMinima;
        this.longitudMaxima = longitudMaxima;
    }
    public void validar(String password) {
        if(password.length() < longitudMinima) {
            throw new PasswordCortaException("La contraseña debe tener "+longitudMinima+" o más caracteres");
        }
        if(password.length() > longitudMaxima) {
            throw new PasswordLargaException("La contraseña no debe tener más de "+longitudMaxima+" caracteres");
        }
    }
}
