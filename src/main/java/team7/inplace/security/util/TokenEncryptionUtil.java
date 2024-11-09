package team7.inplace.security.util;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;


public class TokenEncryptionUtil {

    private final TextEncryptor encryptor;

    public TokenEncryptionUtil(String oauthPassword, String oauthSort) {
        this.encryptor = Encryptors.text(oauthPassword, oauthSort);
    }

    public String encrypt(String plainText) {
        return encryptor.encrypt(plainText);
    }

    public String decrypt(String encryptedText) {
        return encryptor.decrypt(encryptedText);
    }
}
