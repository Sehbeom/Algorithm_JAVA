package swea.swea1868;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class MineGame {

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
        boolean[][] mineExist = makeMineExist(board, N);
//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(mineExist[i]));
//        }
//        System.out.println();
        boolean[][] zeroExist = makeZeroExist(board, mineExist, N);
//        for (int i = 0; i < N; i++) {
//            System.out.println(Arrays.toString(zeroExist[i]));
//        }
        boolean[][] visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '*') continue;
                if (!visited[i][j]) {
                    click++;
                    if (!zeroExist[i][j]) {
                        visited[i][j] = true;
                    } else {
                        visitZeros(board, zeroExist, visited, new int[]{i, j}, N);
                    }
                }
            }
        }

        return click;
    }

    private static void visitZeros(char[][] board, boolean[][] zeroExist, boolean[][] visited, int[] start, int N) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start[0]][start[1]] = true;
        int[] current;

        int x;
        int y;

        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < 8; i++) {
                x = current[0] + dx[i];
                y = current[1] + dy[i];

                if (-1 < x && x < N &&
                        -1 < y && y < N &&
                        board[x][y] != '*' &&
                        !visited[x][y] &&
                        zeroExist[x][y]) {
                    queue.offer(new int[] {x, y});
                    visited[x][y] = true;
                }
            }
        }
    }

    private static boolean[][] makeMineExist(char[][] board, int N) {
        boolean[][] mineExist = new boolean[N][N];

        int[] coordinate = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '*') {
                    continue;
                }

                coordinate[0] = i;
                coordinate[1] = j;

                mineExist[i][j] = isMineExist(board, coordinate, N);
            }
        }

        return mineExist;
    }

    private static boolean[][] makeZeroExist(char[][] board, boolean[][] mineExist, int N) {
        boolean[][] zeroExist = new boolean[N][N];

        int[] coordinate = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == '*') {
                    continue;
                }

                coordinate[0] = i;
                coordinate[1] = j;

                if (!mineExist[i][j]) zeroExist[i][j] = true;
                else zeroExist[i][j] = isZeroExist(board, mineExist, coordinate, N);
            }
        }

        return zeroExist;
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

    private static boolean isZeroExist(char[][] board, boolean[][] mineExist, int[] coordinate,
            int N) {
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        int x;
        int y;
        for (int i = 0; i < 8; i++) {
            x = coordinate[0] + dx[i];
            y = coordinate[1] + dy[i];
            if (-1 < x && x < N &&
                    -1 < y && y < N) {
                if (board[x][y] == '*')
                    continue;

                if (!mineExist[x][y])
                    return true;
            }
        }
        return false;
    }
}
