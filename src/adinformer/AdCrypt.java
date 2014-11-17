package adinformer;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author zhenya mogsev@gmail.com
 */
public class AdCrypt {
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = 
    new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
    private String encryptedValue;
    private String decryptedValue;
    private static Key key;
    
    public String encrypt(String valueToEnc) {
        try {
        Key key = generateKey();
        Cipher cip = Cipher.getInstance(ALGORITHM);
        cip.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = cip.doFinal(valueToEnc.getBytes());                
        encryptedValue = new BASE64Encoder().encode(encValue);
        } catch (Exception ex) {
            ADInformer.isError("Error in encrypt", ex);
        }
        return encryptedValue;
    }

    public String decrypt(String encryptedValue) {
        try {
            key = generateKey();
            Cipher cip = Cipher.getInstance(ALGORITHM);
            cip.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
            byte[] decValue = cip.doFinal(decordedValue);
            decryptedValue = new String(decValue);        
        } catch (Exception ex) {
            ADInformer.isError("Error in decrypt", ex);
        }
        return decryptedValue;
    }

    private static Key generateKey() {
        try {
            key = new SecretKeySpec(keyValue, ALGORITHM);
        } catch (Exception ex) {
            ADInformer.isError("Error in generateKey", ex);
        }
        return key;
    }
}

