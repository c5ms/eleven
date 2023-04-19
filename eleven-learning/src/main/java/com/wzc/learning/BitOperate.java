package com.wzc.learning;

public class BitOperate {

    public static void printBinary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 1;
        printBinary(n);
        n = -~n;
        printBinary(n);
    }

}
