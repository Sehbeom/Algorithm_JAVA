package boj.boj1451;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static long answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] rectangle = new int[N][M];

        String tmp;
        for (int i = 0; i < N; i++) {
            tmp = in.readLine();
            for (int j = 0; j < M; j++) {
                rectangle[i][j] = tmp.charAt(j) - '0';
            }
        }

        checkTypeOne(rectangle, N, M);
        checkTypeTwo(rectangle, N, M);

        System.out.println(answer);
    }

    private static void checkTypeOne(int[][] rectangle, int N, int M) {
        long[] sums = new long[3];

        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < M - 1; j++) {
                sums[0] = getSum(rectangle, 0, i, 0, j);
                sums[1] = getSum(rectangle, i + 1, N - 1, 0, j);
                sums[2] = getSum(rectangle, 0, N - 1, j + 1, M - 1);
                reflectAnswer(sums);
                sums[1] = getSum(rectangle, i + 1, N - 1, 0, M - 1);
                sums[2] = getSum(rectangle, 0, i, j + 1, M - 1);
                reflectAnswer(sums);
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                sums[0] = getSum(rectangle, i, N - 1, j, M - 1);
                sums[1] = getSum(rectangle, 0, i - 1, j, M - 1);
                sums[2] = getSum(rectangle, 0, N - 1, 0, j - 1);
                reflectAnswer(sums);
                sums[1] = getSum(rectangle, 0, i - 1, 0, M - 1);
                sums[2] = getSum(rectangle, i, N - 1, 0, j - 1);
                reflectAnswer(sums);
            }
        }
    }

    private static void checkTypeTwo(int[][] rectangle, int N, int M) {
        long[] sums = new long[3];

        for (int i = 1; i <= M - 2; i++) {
            for (int j = 1; j <= M - i - 1; j++) {
                sums[0] = getSum(rectangle, 0, N - 1, 0, i - 1);
                sums[1] = getSum(rectangle, 0, N - 1, i, i + (j - 1));
                sums[2] = getSum(rectangle, 0, N - 1, i + j, M - 1);
                reflectAnswer(sums);
            }
        }

        for (int i = 1; i <= N - 2; i++) {
            for (int j = 1; j <= N - i - 1; j++) {
                sums[0] = getSum(rectangle, 0, i - 1, 0, M - 1);
                sums[1] = getSum(rectangle, i, i + (j - 1), 0, M - 1);
                sums[2] = getSum(rectangle, i + j, N - 1, 0, M - 1);
                reflectAnswer(sums);
            }
        }
    }


    private static long getSum(int[][] rectangle, int rowStart, int rowEnd, int colStart,
            int colEnd) {
        long sum = 0;

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                sum += rectangle[i][j];
            }
        }

        return sum;
    }

    private static void reflectAnswer(long[] sums) {
        answer = Math.max(answer, sums[0] * sums[1] * sums[2]);
    }

}
