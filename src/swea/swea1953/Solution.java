package swea.swea1953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int N;
        int M;
        int R;
        int C;
        int L;
        int[][] map;
        int[][] direction = {
                {0, -1}, // 좌
                {0, 1}, // 우
                {-1, 0}, // 상
                {1, 0} //하
        };

        int[] canMove = {1, 0, 3, 2};

        int[][] tunnelType = {
                {},
                {0, 1, 2, 3}, //좌우상하
                {2, 3}, //상하
                {0, 1}, //좌우
                {2, 1}, //상우
                {3, 1}, //하우
                {0, 3}, //좌하
                {0, 2} //좌상
        };

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            st = new StringTokenizer(in.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            map = new int[N][M];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < M; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            sb.append(countCanMove(map, tunnelType, R, C, N, M, L, direction, canMove))
                    .append("\n");
        }

        System.out.println(sb);
    }

    private static int countCanMove(int[][] map, int[][] tunnelType, int r, int c, int N, int M,
            int L, int[][] direction, int[] canMove) {
        int count = 1;

        boolean[][] visited = new boolean[N][M];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{r, c, L - 1});
        visited[r][c] = true;

        int[] current;
        int nextX;
        int nextY;
        int hour;
        int curMapValue;
        int nextMapValue;
        int curDirection;

        while (!queue.isEmpty()) {
            current = queue.poll();
            hour = current[2];
            curMapValue = map[current[0]][current[1]];

            if (hour == 0) {
                break;
            }

            for (int i = 0; i < tunnelType[curMapValue].length; i++) {
                curDirection = tunnelType[curMapValue][i];
                nextX = current[0] + direction[curDirection][0];
                nextY = current[1] + direction[curDirection][1];

                if ((-1 < nextX && nextX < N) &&
                        (-1 < nextY && nextY < M)) {
                    nextMapValue = map[nextX][nextY];
                    if (nextMapValue == 0) continue;

                    boolean canGo = false;
                    for (int j = 0; j < tunnelType[nextMapValue].length; j++) {
                        if (canMove[curDirection] == tunnelType[nextMapValue][j])
                            canGo = true;
                    }

                    if (!canGo) continue;

                    if (!visited[nextX][nextY]) {
                        visited[nextX][nextY] = true;
                        queue.offer(new int[]{nextX, nextY, hour - 1});
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
