/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author 2dam
 */
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * <b>Criptograf�a Asim�trica (Clave p�blica) - Generador Claves</b> <br/>
 * <br/>
 *
 * En un <b>Cifrado asim�trico</b> hay dos participantes: el emisor y el
 * receptor. Los pasos a seguir son:
 *
 * <ul>
 * <li>Generar una <b>clave p�blica</b> y otra <b>privada</b>. La clave p�blica
 * se env�a al emisor</li>
 * <li>El emisor <u>cifra</u> los datos con <b>clave p�blica</b> y se env�an al
 * receptor</li>
 * <li>El receptor <u>descifra</u> los datos con <b>clave privada</b></li>
 * </ul>
 *
 * En este caso, el algoritmo utilizado es el <b>RSA</b>.
 */
public class AsimetricoRSA_KeyGenerator {

    /**
     * Genera el fichero con la clave privada
     */
    public void generatePrivateKey() {

        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA"); // Si RSA no es posible implementar 
            // NoSuchAlgorithmException 
            generator.initialize(1024); // Indicamos el tamanio del key: 1024 bits
            KeyPair keypair = generator.generateKeyPair();
            PublicKey publicKey = keypair.getPublic();
            PrivateKey privateKey = keypair.getPrivate();
            System.out.println(publicKey.getFormat());
            System.out.println(privateKey.getFormat()); 

            // Publica     
            FileOutputStream fileOutputStream = new FileOutputStream(".\\Public.key");
            fileOutputStream.write(publicKey.getEncoded());
            fileOutputStream.close();

            // Privada    
            fileOutputStream = new FileOutputStream(".\\Private.key");
            fileOutputStream.write(privateKey.getEncoded());
            fileOutputStream.close();
        } catch (NoSuchAlgorithmException e) {
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AsimetricoRSA_KeyGenerator asimetricoRSA_KeyGenerator = new AsimetricoRSA_KeyGenerator();
        asimetricoRSA_KeyGenerator.generatePrivateKey();
        System.out.println("Ficheros de Clave Generados!");
    }
}