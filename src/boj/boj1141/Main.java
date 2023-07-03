package boj.boj1141;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main {

    private static int maxSize = 0;

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

        for (int i = 1; i <= N; i++) {
            checkMaxSizeOfSet(words, N, new int[i], i, 0, 0);
        }

        System.out.println(maxSize);

    }

    private static void checkMaxSizeOfSet(String[] words, int N, int[] subSet, int subSetLength, int curLength, int curIndex) {
        if (curLength == subSetLength) {
            maxSize = Math.max(maxSize, subSetLength);
            return;
        }

        for (int i = curIndex; i < N; i++) {
            subSet[curLength] = i;
            if (checkCanBeSelected(words, subSet, i)) {
                checkMaxSizeOfSet(words, N, subSet, subSetLength, curLength + 1, i + 1);
            }
        }

    }

    private static boolean checkCanBeSelected(String[] words, int[] subSet, int number) {
        for (int i = 0; i < subSet.length; i++) {
            if (subSet[i] == number) continue;
            if (checkCanBePrefix(words[subSet[i]], words[number])) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkCanBePrefix(String str1, String str2) {
        String shortStr;
        String longStr;
        if (str1.length() > str2.length()) {
            shortStr = str2;
            longStr = str1;
        } else if (str1.length() < str2.length()) {
            shortStr = str1;
            longStr = str2;
        } else {
            return str1.equals(str2);
        }

        for (int i = 0; i < shortStr.length(); i++) {
            if (shortStr.charAt(i) != longStr.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
