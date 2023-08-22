package boj.boj14890;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] rampRowInfo = new boolean[N][N];
        boolean[][] rampColInfo = new boolean[N][N];
        int answer = 0;
        for (int i = 0; i < N; i++) {
            if (canOneRowPass(map, rampRowInfo, L, i)) {
                answer += 1;
            }
        }

        for (int i = 0; i < N; i++) {
            if (canOneColPass(map, rampColInfo, L, i)) {
                answer += 1;
            }
        }

        System.out.println(answer);
    }

    private static boolean canOneRowPass(int[][] map, boolean[][] rampInfo, int L, int row) {
        for (int i = 0; i < map[0].length - 1; i++) {
            if ((map[row][i + 1] - map[row][i]) == 1) {
                if (!canLayRampBack(map, rampInfo, L, new int[]{row, i}, true)) {
                    return false;
                }
            } else if ((map[row][i] - map[row][i + 1]) == 1) {
                if (!canLayRampFront(map, rampInfo, L, new int[]{row, i + 1}, true)) {
                    return false;
                }
            } else if (Math.abs(map[row][i] - map[row][i + 1]) > 1) {
                return false;
            }
        }

        return true;
    }

    private static boolean canOneColPass(int[][] map, boolean[][] rampInfo, int L, int col) {
        for (int i = 0; i < map[0].length - 1; i++) {
            if ((map[i + 1][col] - map[i][col]) == 1) {
                if (!canLayRampBack(map, rampInfo, L, new int[]{i, col}, false)) {
                    return false;
                }
            } else if ((map[i][col] - map[i + 1][col]) == 1) {
                if (!canLayRampFront(map, rampInfo, L, new int[]{i + 1, col}, false)) {
                    return false;
                }
            } else if (Math.abs(map[i][col] - map[i + 1][col]) > 1) {
                return false;
            }
        }

        return true;
    }

    private static boolean canLayRampFront(int[][] map, boolean[][] rampInfo, int L, int[] start,
        boolean isRow) {
        if (rampInfo[start[0]][start[1]]) {
            return false;
        }

        int curX = start[0];
        int curY = start[1];
        int value = map[curX][curY];
        int len = L - 1;

        while (len > 0) {
            if (isRow) {
                curY += 1;
            } else {
                curX += 1;
            }

            if (!canCheck(curX, curY, map.length)) {
                return false;
            }

            if (value != map[curX][curY]) {
                return false;
            }

            if (rampInfo[curX][curY]) {
                return false;
            }

            len -= 1;
        }

        for (int i = 0; i < L; i++) {
            if (isRow) {
                rampInfo[start[0]][start[1] + i] = true;
            } else {
                rampInfo[start[0] + i][start[1]] = true;
            }
        }

        return true;
    }

    private static boolean canLayRampBack(int[][] map, boolean[][] rampInfo, int L, int[] start,
        boolean isRow) {
        if (rampInfo[start[0]][start[1]]) {
            return false;
        }

        int curX = start[0];
        int curY = start[1];
        int value = map[curX][curY];
        int len = L - 1;

        while (len > 0) {
            if (isRow) {
                curY -= 1;
            } else {
                curX -= 1;
            }

            if (!canCheck(curX, curY, map.length)) {
                return false;
            }

            if (value != map[curX][curY]) {
                return false;
            }

            if (rampInfo[curX][curY]) {
                return false;
            }

            len -= 1;
        }

        for (int i = 0; i < L; i++) {
            if (isRow) {
                rampInfo[start[0]][start[1] - i] = true;
            } else {
                rampInfo[start[0] - i][start[1]] = true;
            }
        }

        return true;
    }

    private static boolean canCheck(int x, int y, int n) {
        if ((0 <= x && x < n) &&
            (0 <= y && y < n)) {
            return true;
        }

        return false;
    }

}
