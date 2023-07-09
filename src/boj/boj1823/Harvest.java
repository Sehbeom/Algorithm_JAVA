package boj.boj1823;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Harvest {

    private static int[] rices;
    private static int[][] harvestInfo;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        rices = new int[N + 1];
        harvestInfo = new int[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            rices[i] = Integer.parseInt(in.readLine());
        }

        System.out.println(getAnswer(1, N, 1));

    }

    private static int getAnswer(int start, int end, int days) {
        if (start > end) return 0;

        if (harvestInfo[start][end] > 0) return harvestInfo[start][end];

        return harvestInfo[start][end] = Math.max(rices[start] * days + getAnswer(start + 1, end, days + 1),
                getAnswer(start, end - 1, days + 1) + rices[end] * days);
    }
}
