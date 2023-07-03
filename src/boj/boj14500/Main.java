package boj.boj14500;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;
    private static int maxValue = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] board = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                maxValue = Math.max(maxValue, board[i][j]);
            }
        }

        boolean[][] visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                    visited[i][j] = true;
                    checkMaxSum(board, visited, board[i][j], 1, new int[]{i, j});
                    visited[i][j] = false;
            }
        }

        checkFuck(board);

        System.out.println(answer);
    }

    private static void checkMaxSum(int[][] board, boolean[][] visited, int curSum, int curSize, int[] curPos) {
        if (curSize == 4) {
            answer = Math.max(answer, curSum);
            return;
        }


        int[] dx = new int[] {0, 0, 1};
        int[] dy = new int[] {-1, 1, 0};

        int nextX = 0;
        int nextY = 0;
        for (int i = 0; i < 3; i++) {
            nextX = curPos[0] + dx[i];
            nextY = curPos[1] + dy[i];

            if (boundaryCheck(nextX, nextY, board.length, board[0].length) &&
                    !visited[nextX][nextY] &&
                    (answer < (curSum + maxValue * (4 - curSize)))) {
                visited[nextX][nextY] = true;
                checkMaxSum(board, visited, curSum + board[nextX][nextY], curSize + 1, new int[] {nextX, nextY});
                visited[nextX][nextY] = false;
            }
        }
    }

    private static void checkFuck(int[][] board) {
        int[][] dx = new int[][] {
                {-1, 0, 0, 0},
                {-1, 0, 0, 1},
                {0, 0, 0, 1},
                {-1, 0, 0, 1}
        };
        int[][] dy = new int[][] {
                {0, -1, 0, 1},
                {0, 0, 1, 0},
                {-1, 0, 1, 0},
                {0, -1, 0, 0}
        };
        int N = board.length;
        int M = board[0].length;

        int sum = 0;

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < (M - 1); j++) {
                sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += board[i + dx[0][k]][j + dy[0][k]];
                }
                answer = Math.max(answer, sum);
            }
        }

        for (int i = 1; i < (N - 1); i++) {
            for (int j = 0; j < (M - 1); j++) {
                sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += board[i + dx[1][k]][j + dy[1][k]];
                }
                answer = Math.max(answer, sum);
            }
        }

        for (int i = 0; i < (N - 1); i++) {
            for (int j = 1; j < (M - 1); j++) {
                sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += board[i + dx[2][k]][j + dy[2][k]];
                }
                answer = Math.max(answer, sum);
            }
        }

        for (int i = 1; i < (N - 1); i++) {
            for (int j = 1; j < M; j++) {
                sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum += board[i + dx[3][k]][j + dy[3][k]];
                }
                answer = Math.max(answer, sum);
            }
        }

    }

    private static boolean boundaryCheck(int x, int y, int bx, int by) {
        if ((0 <= x && x < bx) && (0 <= y && y < by)) {
            return true;
        }
        return false;
    }
}
