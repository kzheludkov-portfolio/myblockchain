package com.exitCertified.blockchain.complete;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {

    public static String Hash2HexString(byte[] hash) {
        String buffer = "";
        for (byte character : hash) {
            buffer += Integer.toHexString(
                    0xFF & character);
        }
        return buffer;
    }

    public static String setHash(String data) throws NoSuchAlgorithmException {
        String stringHash;
        MessageDigest digest =
                MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                data.getBytes(StandardCharsets.UTF_8));
        stringHash = Utilities.Hash2HexString(hash);
        return stringHash;
    }
}
