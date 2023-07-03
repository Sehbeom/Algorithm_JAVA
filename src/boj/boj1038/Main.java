package boj.boj1038;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static int N = 0;
    private static long answer = 0;
    private static int accSum = -1;
    private static boolean foundAnswer = false;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(in.readLine());

        if (N > 1022) {
            System.out.println(-1);
            return;
        }

        int[][] descNumCount = new int[10][10];

        for (int i = 0; i < 10; i++) {
            descNumCount[0][i] = 1;
        }

        for (int i = 1; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                descNumCount[i][j] = sumPrevCount(descNumCount, i - 1, j);
            }
        }

        exit : for (int i = 0; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                accSum += descNumCount[i][j];
                if (accSum > N) {
                    answer += (long)(j * Math.pow(10, i));

                    int[] candidates = new int[j];
                    for (int c = j - 1; c >= 0; c--) {
                        candidates[candidates.length - c - 1] = c;
                    }
                    findAnswer(candidates, new int[i], i, 0, 0);
                    break exit;
                } else if (accSum == N) {
                    for (int k = i; k >= 0; k--) {
                        answer += (long)((j - i + k) * Math.pow(10, k));
                    }

                    break exit;
                }

            }
        }

        System.out.println(answer);
    }

    private static void findAnswer(int[] cadidates, int[] selected, int digits, int curDigits, int index) {
        if (!foundAnswer) {
            if (curDigits == digits) {
                if (accSum == N) {
                    for (int i = 0; i < selected.length; i++) {
                        answer += (long) (selected[i] * Math.pow(10, selected.length - i - 1));
                    }
                    foundAnswer = true;
                }
                accSum -= 1;
                return;
            }

            for (int i = index; i < cadidates.length; i++) {
                selected[curDigits] = cadidates[i];
                findAnswer(cadidates, selected, digits, curDigits + 1, i + 1);
            }
        }

    }

    private static int sumPrevCount(int[][] descNumCount, int prevDigit, int curDigitNumber) {
        int sum = 0;

        for (int i = prevDigit; i < curDigitNumber; i++) {
            sum += descNumCount[prevDigit][i];
        }

        return sum;
    }
}
