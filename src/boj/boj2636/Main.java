package boj.boj2636;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int minCol;
    private static int minRow;
    private static int maxCol;
    private static int maxRow;

    private static int N;

    private static int M;

    private static int[] dx = new int[]{0, 0, -1, 1};
    private static int[] dy = new int[]{-1, 1, 0, 0};

    private static int countAnswer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        minCol = 0;
        minRow = 0;
        maxRow = N;
        maxCol = M;

        int[][] board = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int nextCountAnswer = checkMeltCheese(board);
        int days = 0;

        while (nextCountAnswer > 0) {
            countAnswer = nextCountAnswer;
            nextCountAnswer = checkMeltCheese(board);
            days += 1;
        }

        System.out.println(days);
        System.out.println(countAnswer);
    }

    private static int checkMeltCheese(int[][] board) {
        boolean[][] visited = new boolean[N][M];
        boolean[][] shouldMelt = new boolean[N][M];

        for (int i = minRow; i < maxRow; i += i + (maxRow - minRow + 1)) {
            for (int j = minCol; j < maxCol; j++) {
                if (!visited[i][j]) {
                    checkAir(board, shouldMelt, visited, i, j);
                }
            }
        }

        for (int i = minRow; i < maxRow; i++) {
            for (int j = minCol; j < maxCol; j += (maxCol - minCol + 1)) {
                if (!visited[i][j]) {
                    checkAir(board, shouldMelt, visited, i, j);
                }
            }
        }

        int nextMinRow = Integer.MAX_VALUE;
        int nextMaxRow = Integer.MIN_VALUE;
        int nextMinCol = Integer.MAX_VALUE;
        int nextMaxCol = Integer.MIN_VALUE;
        int nextCountAnswer = 0;

        for (int i = minRow; i < maxRow; i++) {
            for (int j = minCol; j < maxCol; j++) {
                if (shouldMelt[i][j]) {
                    nextCountAnswer += 1;
                    board[i][j] = 0;

                    nextMinRow = Math.min(nextMinRow, i);
                    nextMaxRow = Math.max(nextMaxRow, i);
                    nextMinCol = Math.min(nextMinCol, j);
                    nextMaxCol = Math.max(nextMaxCol, j);
                }
            }
        }

        return nextCountAnswer;
    }

    private static void checkAir(int[][] board, boolean[][] shouldMelt, boolean[][] visited,
        int startX, int startY) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{startX, startY});
        visited[startX][startY] = true;

        int[] current;
        int nextX;
        int nextY;
        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (canGo(nextX, nextY) && !visited[nextX][nextY]) {
                    if (board[nextX][nextY] == 1) {
                        shouldMelt[nextX][nextY] = true;
                    } else {
                        visited[nextX][nextY] = true;
                        queue.offer(new int[]{nextX, nextY});
                    }
                }
            }
        }
    }

    private static boolean canGo(int x, int y) {
        if ((minRow <= x && x < maxRow) &&
            (minCol <= y && y < maxCol)) {
            return true;
        }

        return false;
    }
}
