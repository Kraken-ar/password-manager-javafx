package app.Classes;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class Cypher{

    public static String encrypt(String plaintext,String key){
        String buffer = "";
        int keyLength = key.length();
        int textLength = plaintext.length();
        for (int i = 0,x=0;i<textLength;i++,x++){
            int plainValue =  plaintext.charAt(i);
            int keyValue = key.charAt(x);
            char cypher = (char) ((plainValue+keyValue)%127);
            buffer += cypher;
            if (x == keyLength-1)
                x = -1;
        }
        return buffer;
    }

    public static String decrypt(String cyphertext,String key){
        String buffer = "";
        int keyLength = key.length();
        int textLength = cyphertext.length();
        for (int i = 0,x=0;i<textLength;i++,x++){
            int cypherValue =  cyphertext.charAt(i);
            int keyValue = key.charAt(x);
            int temp = ((cypherValue-keyValue));
            if(temp <0)
                temp+=127;
            char plain = (char) temp ;
            buffer += plain;
            if (x == keyLength-1)
                x = -1;
        }
        return buffer;
    }


    public static String hashPassword(String password) {
        // Define the salt (use a secure random generator in a real application)
        byte[] salt = "12345678".getBytes(); // In a real application, use a secure random generator for the salt

        // Define the iterations and key length
        int iterations = 65536;
        int keyLength = 256;

        try {
            // Create the PBEKeySpec with the given password, salt, iterations, and key length
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

            // Create the SecretKeyFactory for PBKDF2WithHmacSHA256
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            // Generate the hash
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Convert the hash to a Base64 encoded string and return it

            return encrypt( Base64.getEncoder().encodeToString(hash),password);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing the password", e);
        }
    }

    public static void main(String[] args) {
        String x = "Mohamed_4";
        String y = "ab*del23fa_ta";

//        System.out.println(encrypt(x,y));
//        System.out.println(decrypt(encrypt(x,y),y));
        System.out.println(hashPassword(x));
    }

}