package samsung_a_prevtest.supplyroute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class SupplyRoute {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(in.readLine());

        int N;
        int[][] map;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            N = Integer.parseInt(in.readLine());
            map = new int[N][N];

            String tmp;
            for (int i = 0; i < N; i++) {
                tmp = in.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = tmp.charAt(j) - '0';
                }
            }

            sb.append(findShortestRoute(map, N)).append("\n");
        }
        System.out.println(sb);

    }

    private static int findShortestRoute(int[][] map, int N) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[2] - o2[2]);
        boolean[][] visited = new boolean[N][N];

        pq.offer(new int[] {0, 0, 0}); // x, y, time
        visited[0][0] = true;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        int[] current;
        int nextX;
        int nextY;
        breakPoint : while (!pq.isEmpty()) {
            current = pq.poll();

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (-1 < nextX && nextX < N &&
                -1 < nextY && nextY < N &&
                !visited[nextX][nextY]) {
                    if (nextX == N - 1 && nextY == N - 1) {
                        return current[2] + map[nextX][nextY];
                    }
                    pq.offer(new int[] {nextX, nextY, current[2] + map[nextX][nextY]});
                    visited[nextX][nextY] = true;
                }
            }
        }
        return 0;
    }
}
