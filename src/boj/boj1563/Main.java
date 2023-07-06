package boj.boj1563;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        int[][] dp = new int[1001][3];

        dp[0][1] = 1;
        dp[1][0] = 1;
        dp[1][1] = 1;
        dp[1][2] = 1;
        dp[2][0] = 3;
        dp[2][1] = 2;
        dp[2][2] = 3;
        for (int i = 3; i <= 1000; i++) {
            dp[i][0] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2]) % 1000000;
            dp[i][1] = (dp[i - 1][1] + dp[i - 2][1] + dp[i - 3][1]) % 1000000;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 2][0] + dp[i - 2][1]) % 1000000;
        }

        System.out.println((dp[N][0] + dp[N][1] + dp[N][2]) % 1000000);
    }

}
