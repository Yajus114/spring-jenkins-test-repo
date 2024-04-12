package com.dawnbit.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Slf4j
public class Crypt {
    private static final String ALGO = "AES"; // Default uses ECB PKCS5Padding
    private static final String SECRET = "dawnbit@2020#dps";

    public static String encrypt(final String Data) throws Exception {

        final String encodedBase64Key = Crypt.encodeKey(SECRET);
        if (log.isInfoEnabled()) {
            log.info("encodedBase64Key:  --" + encodedBase64Key);
        }
        final Key key = generateKey(encodedBase64Key);
        final Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        final byte[] encVal = c.doFinal(Data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    public static String decrypt(final String strToDecrypt) {
        try {
            final String encodedBase64Key = Crypt.encodeKey(SECRET);
            final Key key = generateKey(encodedBase64Key);
            final Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (final Exception e) {
            if (log.isInfoEnabled()) {
                log.info("Error while decrypting:" + e.toString());
            }
        }
        return null;
    }

    private static Key generateKey(final String secret) throws Exception {
        final byte[] decoded = Base64.getDecoder().decode(secret.getBytes());
        return new SecretKeySpec(decoded, ALGO);

    }

    public static String decodeKey(final String str) {
        final byte[] decoded = Base64.getDecoder().decode(str.getBytes());
        return new String(decoded);
    }

    public static String encodeKey(final String str) {
        final byte[] encoded = Base64.getEncoder().encode(str.getBytes());
        return new String(encoded);
    }

}