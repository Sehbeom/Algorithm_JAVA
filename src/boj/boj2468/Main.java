package boj.boj2468;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[][] area = new int[N][N];
        Set<Integer> rainHeights = new HashSet<>();
        rainHeights.add(0);

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                area[i][j] = Integer.parseInt(st.nextToken());
                rainHeights.add(area[i][j]);
            }
        }

        int maxCount = 0;
        int curCount = 0;
        for (int rainHeight : rainHeights) {
            curCount = countSafeArea(area, rainHeight);

            maxCount = Math.max(maxCount, curCount);
        }

        System.out.println(maxCount);
    }

    private static int countSafeArea(int[][] area, int rainHeight) {
        boolean[][] visited = new boolean[area.length][area.length];
        int N = area.length;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (area[i][j] <= rainHeight) {
                    visited[i][j] = true;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    bfs(visited, new int[] {i, j});
                    count += 1;
                }
            }
        }

        return count;
    }

    private static void bfs(boolean[][] visited, int[] startPoint) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(startPoint);

        int[] dx = new int[] {0, 0, -1, 1};
        int[] dy = new int[] {-1, 1, 0, 0};

        int[] current;
        int nextX;
        int nextY;
        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (checkBounds(nextX, nextY, visited.length) &&
                !visited[nextX][nextY]) {
                    queue.offer(new int[]{nextX, nextY});
                    visited[nextX][nextY] = true;
                }
            }
        }
    }

    private static boolean checkBounds(int x, int y, int N) {
        if ((-1 < x && x < N ) && (-1 < y && y < N)) {
            return true;
        }
        return false;
    }
}
