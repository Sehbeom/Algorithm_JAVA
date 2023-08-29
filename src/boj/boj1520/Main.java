package boj.boj1520;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static int[] dx = new int[]{0, 0, -1, 1};
    private static int[] dy = new int[]{-1, 1, 0, 0};

    private static int[][] dp;
    private static int answer = 0;

    private static int M = 0;
    private static int N = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        dp = new int[M][N];
        for (int i = 0; i < M; i++) {
            Arrays.fill(dp[i], -1);
        }
        int[][] map = new int[M][N];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        checkRoutes(map, new int[]{0, 0});

        System.out.println(dp[0][0]);
    }

    private static int checkRoutes(int[][] map, int[] current) {
        if (current[0] == (M - 1) &&
            current[1] == (N - 1)) {
            return 1;
        }

        if (dp[current[0]][current[1]] > -1) {
            return dp[current[0]][current[1]];
        }

        dp[current[0]][current[1]] = 0;

        int[] next = new int[2];
        for (int i = 0; i < 4; i++) {
            next[0] = current[0] + dx[i];
            next[1] = current[1] + dy[i];

            if (canGo(map, current, next)) {
                dp[current[0]][current[1]] += checkRoutes(map, next);
            }
        }

        return dp[current[0]][current[1]];
    }

    private static boolean canGo(int[][] map, int[] current, int[] next) {
        if ((0 <= next[0] && next[0] < M) &&
            (0 <= next[1] && next[1] < N) &&
            (map[current[0]][current[1]] > map[next[0]][next[1]])) {
            return true;
        }
        return false;
    }
}
