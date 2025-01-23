/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author crice
 */
import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class ClienteRegistro {

    public static String encriptarContraseña(String password) {
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
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(password.getBytes());

            // Convertir a base64 para enviar al servidor
            return java.util.Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Ejemplo de uso de encriptar la contraseña
        String password = "miContraseñaSecreta";
        String encryptedPassword = encriptarContraseña(password);
        System.out.println("Contraseña cifrada: " + encryptedPassword);

        // Aquí deberías enviar la contraseña cifrada al servidor
    }
}
