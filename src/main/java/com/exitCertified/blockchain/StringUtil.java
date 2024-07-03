package com.exitCertified.blockchain;

import java.security.MessageDigest;

public class StringUtil {

    public static String getSHA2HexValue(String str) throws RuntimeException {

        try {
            byte[] cipher_byte;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(str.getBytes());
                cipher_byte = md.digest();
                StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
                for (byte b : cipher_byte) {
                    String hex = Integer.toHexString(b & 0xff);
                    if (hex.length() == 1)
                        sb.append('0');
                    sb.append(hex);
                }
                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
