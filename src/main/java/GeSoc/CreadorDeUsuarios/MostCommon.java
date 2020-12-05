package GeSoc.CreadorDeUsuarios;

import GeSoc.exceptions.PasswordDebilException;

import java.io.*;

public class MostCommon implements ValidacionPassword {

    public void validar(String password) {

        String linea;
        try {
            InputStream in = getClass().getResourceAsStream("10k-most-common.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ( (linea = reader.readLine() ) != null) {
                if (linea.contains(password)) {
                    throw new PasswordDebilException("La contraseña es de las más usadas");
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
