package boj.boj1141;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Prefix {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = in.readLine();
        }

        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        boolean[] removed = new boolean[N];
        int count = N;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (checkCanBePrefix(words[i], words[j])) {
                    count -= 1;
                    break;
                }
            }
        }

        System.out.println(count);
    }

    private static boolean checkCanBePrefix(String shortStr, String longStr) {
        if (shortStr.length() == longStr.length()) {
            return shortStr.equals(longStr);
        }

        for (int i = 0; i < shortStr.length(); i++) {
            if (shortStr.charAt(i) != longStr.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
