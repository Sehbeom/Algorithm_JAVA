package swea.swea1868;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(in.readLine());
        int N;
        char[][] board;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            N = Integer.parseInt(in.readLine());
            board = new char[N][N];
            for (int i = 0; i < N; i++) {
                board[i] = in.readLine().toCharArray();
            }

            sb.append(countClick(board, N)).append("\n");
        }

        System.out.println(sb);
    }

    private static int countClick(char[][] board, int N) {
        int click = 0;
        boolean[][] visited = new boolean[N][N];
        boolean[][] minused = new boolean[N][N];

        int[] coordinate = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '*' || visited[i][j]) {
                    continue;
                }

                coordinate[0] = i;
                coordinate[1] = j;
                click += 1;
                visited[i][j] = true;

                if (isMineExist(board, coordinate, N)) {
                    continue;
                }

                click -= checkZeroArea(board, visited, minused, coordinate, N);
            }
        }

        return click;
    }

    private static boolean isMineExist(char[][] board, int[] coordinate, int N) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        int x;
        int y;
        for (int i = 0; i < 8; i++) {
            x = coordinate[0] + dx[i];
            y = coordinate[1] + dy[i];
            if (-1 < x && x < N &&
                    -1 < y && y < N &&
                    board[x][y] == '*') {
                return true;
            }
        }
        return false;
    }

    private static int countAndReflectFlags(boolean[][] visited, boolean[][] minused,
            int[] coordinate, int N) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        int numOfminused = 0;
        int x;
        int y;
        for (int i = 0; i < 8; i++) {
            x = coordinate[0] + dx[i];
            y = coordinate[1] + dy[i];

            if (-1 < x && x < N &&
                    -1 < y && y < N) {
                if (visited[x][y]) {
                    if (!minused[x][y]) {
                        numOfminused++;
                        minused[x][y] = true;
                    }
                } else {
                    visited[x][y] = true;
                }
            }
        }

        return numOfminused;
    }

    private static int checkZeroArea(char[][] board, boolean[][] visited, boolean[][] minused,
            int[] coordinate, int N) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(coordinate);

        int numOfminused = 0;
        int[] current;
        int x;
        int y;

        while (!queue.isEmpty()) {
            current = queue.poll();
            visited[current[0]][current[1]] = true;

            for (int i = 0; i < 8; i++) {
                x = current[0] + dx[i];
                y = current[1] + dy[i];

                if (-1 < x && x < N &&
                        -1 < y && y < N) {
                    if (!isMineExist(board, new int[]{x, y}, N) && !visited[x][y]) {
                        queue.offer(new int[]{x, y});
                        numOfminused --;
                    }
                }
            }
            numOfminused += countAndReflectFlags(visited, minused, current, N);
        }

        return numOfminused;
    }
}
