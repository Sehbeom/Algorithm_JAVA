package swea.swea2001;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int N;
        int M;
        int[][] flies;

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(in.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            flies = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < N; j++) {
                    flies[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            sb.append("#").append(t).append(" ").append(killFliesMax(flies, M, N)).append("\n");
        }

        out.write(sb.toString());
        out.flush();
        out.close();
    }

    private static int killFliesMax(int[][] flies, int M, int N) {
        int maxKill = countMFirst(flies, M);
        int curRowKill = maxKill;
        int curColKill;

        for (int i = 0; i <= (N - M); i++) {
            curColKill = curRowKill;
            for (int j = 0; j < (N - M); j++) {
                curColKill -= countOneCol(flies, M, i, j);
                curColKill += countOneCol(flies, M, i, j + M);
                maxKill = Math.max(maxKill, curColKill);
            }

            if (i < (N - M)) {
                curRowKill -= countOneRow(flies, M, i, 0);
                curRowKill += countOneRow(flies, M, i + M, 0);
                maxKill = Math.max(maxKill, curRowKill);
            }
        }

        return maxKill;
    }

    private static int countMFirst(int[][] flies, int M) {
        int numOfFlies = 0;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                numOfFlies += flies[i][j];
            }
        }

        return numOfFlies;
    }

    private static int countOneCol(int[][] flies, int M, int rowStart, int col) {
        int numOfFlies = 0;

        for (int i = rowStart; i < rowStart + M; i++) {
            numOfFlies += flies[i][col];
        }

        return numOfFlies;
    }

    private static int countOneRow(int[][] flies, int M, int row, int colStart) {
        int numOfFlies = 0;

        for (int i = colStart; i < colStart + M; i++) {
            numOfFlies += flies[row][i];
        }

        return numOfFlies;
    }
}

