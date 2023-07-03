package boj.boj1034;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int maxCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        long[] lamps = new long[N];

        String tmp;
        for (int i = 0; i < N; i++) {
            tmp = in.readLine();
            for (int j = 0; j < M; j++) {
                lamps[i] += (tmp.charAt(j) == '0') ? 0 : Math.pow(2, M - j - 1);
            }
        }

        int K = Integer.parseInt(in.readLine());

        int isOdd = K % 2;
        int maxNumOfCase = Math.min(K, M);

        for (int i = 0; i <= maxNumOfCase; i++) {
            if ((i % 2) == isOdd) {
                makeCombination(0L, i, 0, 0, M, N, lamps);
            }
        }

        System.out.println(maxCount);

    }

    private static void makeCombination(long selected, int maxCnt, int curCnt, int index, int M, int N, long[] lamps) {
        if (curCnt == maxCnt) {
            switching(selected, M, N, lamps);
            return;
        }

        for (int i = index; i < M; i++) {
            selected = selected | (1L<<i);
            makeCombination(selected, maxCnt, curCnt + 1, i + 1, M, N, lamps);
            selected = selected & ~(1L<<i);
        }
    }

    private static void switching(long selected, int M, int N, long[] lamps) {
        int count = 0;
        for (int i = 0; i < M; i++) {
            if ((selected & (1L<<i)) == Math.pow(2, i)) {
                for (int j = 0; j < N; j++) {
                    lamps[j] = lamps[j] ^ (1L<<i);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (lamps[i] == (Math.pow(2, M) - 1)) {
                count += 1;
            }
        }

        if (maxCount < count) {
            maxCount = count;
        }

        for (int i = 0; i < M; i++) {
            if ((selected & (1L<<i)) == Math.pow(2, i)) {
                for (int j = 0; j < N; j++) {
                    lamps[j] = lamps[j] ^ (1L<<i);
                }
            }
        }
    }
}