package boj.boj11403;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[][] graph = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());

            for (int j = 0; j < N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] canGo = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (graph[i][j] == 1) {
                    if (!canGo[i][j]) {
                        checkPath(graph, canGo, new boolean[N], i, j);
                    }
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(canGo[i][j] ? "1" : "0").append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);

    }

    private static void checkPath(int[][] graph, boolean[][] canGo, boolean[] visited, int start, int to) {
        canGo[start][to] = true;
        visited[to] = true;

        for (int i = 0; i < graph.length; i++) {
            if (graph[to][i] == 1) {
                if (!visited[i]) {
                    checkPath(graph, canGo, visited, start, i);
                }
            }
        }
    }
}
