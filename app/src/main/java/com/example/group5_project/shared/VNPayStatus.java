package com.example.group5_project.shared;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum VNPayStatus {
    V00 {
        @Override
        public String getMessage() {
            return "Transaction was successful. Thank you very much!";
        }
    },
    V09 {
        @Override
        public String getMessage() {
            return "Fail. Bank account isn't registered in Internet Banking";
        }
    },
    V11 {
        @Override
        public String getMessage() {
            return "Fail. Your transaction is expired";
        }
    },
    V12 {
        @Override
        public String getMessage() {
            return "Fail. Your bank account is frozen";
        }
    },
    V13 {
        @Override
        public String getMessage() {
            return "Fail. Wrong OTP";
        }
    },
    V24 {
        @Override
        public String getMessage() {
            return "Fail. You have cancelled the transaction";

        }
    },
    V51 {
        @Override
        public String getMessage() {
            return "Fail. Your balance account is not enough";
        }
    },
    V65 {
        @Override
        public String getMessage() {
            return "Fail. You have reached your daily transaction limit";
        }
    },
    V75 {
        @Override
        public String getMessage() {
            return "Fail. Your Bank is under maintenance";
        }
    },
    V99 {
        @Override
        public String getMessage() {
            return "Fail.";
        }
    };
    public abstract String getMessage();

    private final static Set<String> vnPayStatusSet = Arrays.stream(values()).map(Enum::name).collect(Collectors.toSet());
    public static boolean isVNPayStatus(String status) {
        return  vnPayStatusSet.contains(status);
    }
}
