package com.kuknos.walletconnect.cryptography;




import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AESEncryption {
    private Cipher cipher;
    private SecretKey secretKey;

    public AESEncryption(String key) {
        secretKey = new SecretKeySpec(key.getBytes(), 0, key.length(), "AES");
    }

    public String encrypt(String plainText)
            throws Exception {
        cipher = Cipher.getInstance("AES");
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        String encryptedText = new String(Base64.encode(encryptedByte,Base64.DEFAULT));
        return encryptedText;
    }

    public String decrypt(String encryptedText)
            throws Exception {
        cipher = Cipher.getInstance("AES");
        byte[] encryptedTextByte = Base64.decode(encryptedText.getBytes(),Base64.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

}
