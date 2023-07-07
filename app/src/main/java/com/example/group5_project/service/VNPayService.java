package com.example.group5_project.service;

import static com.example.group5_project.configuration.PaymentConfig.getParams;

import android.net.Uri;
import android.util.Log;

import com.example.group5_project.Entity.Order;
import com.example.group5_project.configuration.PaymentConfig;
import com.example.group5_project.shared.VNPayStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class VNPayService {
    public static String createVNPayURL(Order order) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Testing don hang";
        String orderType = "100000";
        String vnp_TxnRef = String.valueOf(order.getId());
        String vnp_IpAddr = "111.225.187.171";
        String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

        long amount = order.getTotalPrice() * 100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return PaymentConfig.vnp_PayUrl + "?" + queryUrl;
    }
    public static VNPayStatus extractVNPayStatus(Uri uri) throws Exception {
        Map<String, String> keysValues = getParams(uri);
        String code = "V" + keysValues.get("vnp_ResponseCode");
        if(VNPayStatus.isVNPayStatus(code))
            return VNPayStatus.valueOf(code);
        throw new Exception("Invalid VNPay status is invalid");
    }
    public static String VNPayActionReturn(Uri uri) {
        String message = "Transaction failed";
        try {
            VNPayStatus status = extractVNPayStatus(uri);
            message = status.getMessage();
            return message;
        } catch (Exception e) {
            Log.e("Error", "VNPayActionReturn: " + e.getMessage());
            return message;
        }
    }

    public static long extractVNPTxnRef(Uri uri) {
        Map<String, String> keysValues = getParams(uri);
        String orderId = "0";
        if(keysValues.get("vnp_TxnRef") != null)
            orderId = keysValues.get("vnp_TxnRef");
        return Long.parseLong(orderId);
    }
}
