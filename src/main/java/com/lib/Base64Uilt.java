package com.lib;


import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;

public class Base64Uilt {
    public static String base64encode(String message) {
        try {
            byte[] encodeBase64 = Base64.encodeBase64(message.getBytes("UTF-8"));
            return new String(encodeBase64);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }

    }

    public static String base64decode(String message) {
        byte[] encodeBase64 = Base64.decodeBase64(message);
        return new String(encodeBase64);
    }

}
