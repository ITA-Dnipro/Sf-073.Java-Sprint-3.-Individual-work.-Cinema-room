package com.example.antifraud.validation;

public class LuhnAlgo {

    static final int MOD = 10;
    public static boolean checkLuhn(String cardNumber) {
        int digits = cardNumber.length();

        int numberSum = 0;
        boolean second = false;
        for (int i = digits - 1; i >= 0; i--) {

            int temp = cardNumber.charAt(i) - '0';

            if (second) {
                temp = temp * 2;
            }

            numberSum += temp / MOD;
            numberSum += temp % MOD;

            second = !second;
        }
        return (numberSum % MOD == 0);
    }
}
