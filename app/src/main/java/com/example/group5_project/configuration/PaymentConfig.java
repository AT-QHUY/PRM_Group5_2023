package com.example.group5_project.configuration;

import android.net.Uri;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PaymentConfig {
    public static String vnp_TmnCode = "PT6N8X1Y";
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_HashSecret = "HVJJAPLXVTDHSXXQYCBXDAUGPPXSBWOI";
    public static String vnp_ReturnUrl = "myapp://payment";

    public static String getRandomNumber(int randomNum) {
        StringBuilder result = new StringBuilder();
        String alphabet = "0123456789";
        for(int i = 0; i < randomNum; i++) {
            Random random = new Random();
            result.append(random.nextInt(alphabet.length()));
        }
        return result.toString();
    }

    public static Map<String, String> getParams(Uri uri) {
        Map<String, String> result = new HashMap<>();
        String paramsString = uri.toString().substring(uri.toString().indexOf('?') + 1);
        String[] params = paramsString.split("&");
        for (String param: params) {
            String[] keysValues = param.split("=");
            result.put(keysValues[0], keysValues[1]);
        }
        return result;
    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}
