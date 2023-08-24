
package com.eleven.learning;

import java.util.Scanner;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        int len = in.nextInt();
        if (len > input.length()) {
            return;
        }
        char[] chars = input.toCharArray();
        int max = 0;
        int start = 0;

        for (int i = 0; i < chars.length - len; i++) {

            int mu = 0;
            for (int j = i; j < i + len; j++) {
                char ch = chars[j];
                if (ch == 'G' || ch == 'C') {
                    mu++;
                }
            }
            if (mu > max) {
                max = mu;
                start = i;
            }
        }

        System.out.println(input.substring(start, start + len));
    }


}
