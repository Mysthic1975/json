package com.example.json.test;

import com.example.json.security.AESUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AESUtilTest {

    @Test
    public void testEncrypt() throws Exception {
        AESUtil aesUtil = new AESUtil();
        String originalText = "Test";
        String encryptedText = aesUtil.encrypt(originalText);
        assertNotEquals(originalText, encryptedText, "The encrypted text should not be the same as the original text");

        // Ausgabe des verschlüsselten Texts in der Konsole
        System.out.println("Verschlüsselter Text: " + encryptedText);
    }
}
