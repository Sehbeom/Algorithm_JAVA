package boj.boj11404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int n = Integer.parseInt(in.readLine());
        int m = Integer.parseInt(in.readLine());

        int[][] busInfo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(busInfo[i], Integer.MAX_VALUE);
        }

        int from, to, cost;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            from = Integer.parseInt(st.nextToken()) - 1;
            to = Integer.parseInt(st.nextToken()) - 1;
            cost = Integer.parseInt(st.nextToken());

            busInfo[from][to] = Math.min(busInfo[from][to], cost);
        }

        findMinPath(busInfo);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(busInfo[i][j] == Integer.MAX_VALUE ? 0 : busInfo[i][j]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static void findMinPath(int[][] busInfo) {
        for (int i = 0; i < busInfo.length; i++) {
            for (int j = 0; j < busInfo.length; j++) {
                if (i == j) continue;
                if (busInfo[j][i] < Integer.MAX_VALUE) {
                    for (int k = 0; k < busInfo.length; k++) {
                        if (i == k || j == k) continue;
                        if (busInfo[i][k] < Integer.MAX_VALUE) {
                            busInfo[j][k] = Math.min(busInfo[j][k], busInfo[j][i] + busInfo[i][k]);
                        }
                    }
                }
            }
        }
    }
}
