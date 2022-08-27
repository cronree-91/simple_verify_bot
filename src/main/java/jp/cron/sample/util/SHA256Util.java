package jp.cron.sample.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {
    public static String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            byte[] cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
                sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String hmacSha256(String text, String key) {
        try {
            SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(sk);

            byte[] mac_bytes = mac.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder(2 * mac_bytes.length);
            for(byte b: mac_bytes) {
                sb.append(String.format("%02x", b&0xff) );
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            return null;
        }
    }
}
