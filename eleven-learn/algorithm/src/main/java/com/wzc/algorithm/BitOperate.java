package com.wzc.algorithm;

public class BitOperate {

    public static void printBinary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println();
    }

    public static void printDecimal(int num) {
        System.out.println(num);
    }

    // 在位运算中，1 是一个很有意义的数字，一般都是通过对 1 进行左移，右移，然后 &  | ^ 来实现一些指定位操作。
    //  00000000000000011000101101100110
    //& 11111111111111111111111111111011
    //  --------------------------------
    //  00000000000000011000101101100010
    public static void main(String[] args) {
        int x = -101221;
        printBinary(x);
        printBinary((1 << 30));
    }

}
