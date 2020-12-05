package GeSoc.CreadorDeUsuarios;

import GeSoc.exceptions.PasswordDebilException;

public class CaracterRepetitivo implements ValidacionPassword {

    int maximoRepeticionesPermitidas;

    public CaracterRepetitivo(int maximoRepeticionesPermitidas) {
        this.maximoRepeticionesPermitidas = maximoRepeticionesPermitidas;
    }

    public void validar(String password) {
        if (password.length() == 0)
            return;
        char charViejo = password.charAt(0);
        int contadorRepeticiones = 0;
        for (char charActual : password.toCharArray()) {
            if (charActual != charViejo) {
                charViejo = charActual;
                contadorRepeticiones=0;
            }
            contadorRepeticiones++;
            if (contadorRepeticiones > maximoRepeticionesPermitidas)
                throw new PasswordDebilException("La contraseña tiene mas de " + maximoRepeticionesPermitidas + " repeticiones del mismo caracter");
        }
    }
}
