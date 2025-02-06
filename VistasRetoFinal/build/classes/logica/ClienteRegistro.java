package logica;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Clase encargada de realizar el cifrado y descifrado de contraseñas utilizando criptografía RSA.
 * 
 * <p>Esta clase contiene métodos para encriptar y desencriptar contraseñas utilizando claves públicas y privadas RSA.</p>
 * 
 * <p>Para encriptar y desencriptar, se cargan las claves desde archivos de recursos y se usan para operar sobre las contraseñas.</p>
 * 
 * <p>El cifrado se realiza utilizando el algoritmo RSA con el modo de operación "ECB" y el esquema de relleno "PKCS1Padding".</p>
 * 
 * <p>Se utiliza Base64 para la representación de los datos cifrados que se pueden enviar por la red.</p>
 * 
 * @author crice
 */
public class ClienteRegistro {

    /**
     * Encripta una contraseña utilizando RSA y la clave pública almacenada en el archivo "Public.key".
     * 
     * @param contrasena La contraseña en texto claro que se desea cifrar.
     * @return La contraseña cifrada en formato Base64.
     */
    public static String encriptarContraseña(String contrasena) {
        try {
            // Cargar la clave pública desde un archivo
            byte[] publicKeyBytes;
            try (InputStream keyInputStream = ClienteRegistro.class.getResourceAsStream("Public.key");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                if (keyInputStream == null) {
                    throw new FileNotFoundException("No se encontró el archivo de clave pública.");
                }
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = keyInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                publicKeyBytes = baos.toByteArray();
            }

            // Reconstruir la clave pública
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            // Cifrar la contraseña
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(contrasena.getBytes());

            // Convertir a base64 para enviar al servidor
            return java.util.Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Desencripta una contraseña utilizando RSA y la clave privada almacenada en el archivo "Private.key".
     * 
     * @param contrasena La contraseña cifrada en formato Base64 que se desea descifrar.
     * @return La contraseña descifrada en texto claro.
     */
    public static String desencriptarContraseña(String contrasena) {
        try {
            // Cargar la clave privada desde un archivo
            byte[] privateKeyBytes;
            try (InputStream keyInputStream = ClienteRegistro.class.getResourceAsStream("Private.key");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                if (keyInputStream == null) {
                    throw new FileNotFoundException("No se encontró el archivo de clave privada.");
                }
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = keyInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                privateKeyBytes = baos.toByteArray();
            }

            // Reconstruir la clave privada
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Convertir la contraseña cifrada de Base64 a bytes
            byte[] encryptedData = Base64.getDecoder().decode(contrasena);

            // Usar RSA/ECB/PKCS1Padding (asegúrate de que esto es consistente con el cifrado)
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            // Convertir los datos descifrados a String
            return new String(decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método principal para realizar una demostración del proceso de cifrado de contraseñas.
     * 
     * @param args Los argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        // Ejemplo de uso de encriptar la contraseña
        String password = "miContraseñaSecreta";
        String encryptedPassword = encriptarContraseña(password);
        System.out.println("Contraseña cifrada: " + encryptedPassword);

        // Aquí deberías enviar la contraseña cifrada al servidor
    }
}
