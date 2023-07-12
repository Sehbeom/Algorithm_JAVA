package boj.boj9251;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String str1 = in.readLine();
        String str2 = in.readLine();

        String shorter = "";
        String longer = "";

        if (str1.length() <= str2.length()) {
            shorter = str1;
            longer = str2;
        } else {
            shorter = str2;
            longer = str1;
        }

        int shortPtr = 0;
        int longPtr = 0;
        int answer = 0;

        while ((shortPtr < shorter.length()) && (longPtr < longer.length())) {
            if (shorter.charAt(shortPtr) == longer.charAt(longPtr)) {
                answer += 1;
                shortPtr += 1;
                longPtr += 1;
            } else {
                longPtr += 1;
            }
        }

        if (shortPtr < shorter.length() && longPtr == longer.length()) {
            while (shortPtr < shorter.length()) {
                if (shorter.charAt(shortPtr) == longer.charAt(longPtr - 1)) {
                    answer += 1;
                }
                shortPtr += 1;
            }
        } else if (shortPtr == shorter.length() && longPtr < longer.length()) {
            while (longPtr < longer.length()) {
                if (shorter.charAt(shortPtr - 1) == longer.charAt(longPtr)) {
                    answer += 1;
                }
                longPtr += 1;
            }
        }

        System.out.println(answer);

    }
}
