package com.example.json.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue =
            new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

    public String encrypt(String valueToEnc) throws Exception {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, generateKey());
        return Base64.getEncoder().encodeToString(c.doFinal(valueToEnc.getBytes()));
    }

    public String decrypt(String encryptedValue) throws Exception {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, generateKey());
        return new String(c.doFinal(Base64.getDecoder().decode(encryptedValue)), StandardCharsets.UTF_8);
    }

    private Key generateKey() {
        return new SecretKeySpec(keyValue, ALGORITHM);
    }
}
