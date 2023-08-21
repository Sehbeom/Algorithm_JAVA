package boj.boj14890;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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

        int[][] rampInfo = new int[N][N];
        int answer = 0;
        boolean[] canRowPasses = new boolean[N];
        boolean[] canColPasses = new boolean[N];

        for (int i = 0; i < N; i++) {
            canRowPasses[i] = canOneRowPass(map, rampInfo, L, i);
            canColPasses[i] = canOneColPass(map, rampInfo, L, i);

            System.out.println("=========== i : " + i + " =============");
            for (int j = 0; j < N; j++) {
                System.out.println(Arrays.toString(rampInfo[j]));
            }
        }

        boolean canMakeRampRow;
        boolean canMakeRampCol;

        for (int i = 0; i < N; i++) {
            canMakeRampRow = true;
            canMakeRampCol = true;

            if (canRowPasses[i]) {
                for (int j = 0; j < N; j++) {
                    if (rampInfo[i][j] > 1) {
                        canMakeRampRow = false;
                        break;
                    }
                }
                if (canMakeRampRow) {
                    answer += 1;
                }
            }

            if (canColPasses[i]) {
                for (int j = 0; j < N; j++) {
                    if (rampInfo[j][i] > 1) {
                        canMakeRampCol = false;
                        break;
                    }
                }
                if (canMakeRampCol) {
                    answer += 1;
                }
            }
        }

        System.out.println(answer);
    }

    private static boolean canOneRowPass(int[][] map, int[][] rampInfo, int L, int row) {
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

    private static boolean canOneColPass(int[][] map, int[][] rampInfo, int L, int col) {
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

    private static boolean canLayRampFront(int[][] map, int[][] rampInfo, int L, int[] start,
        boolean isRow) {
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

            len -= 1;
        }

        for (int i = 0; i < L; i++) {
            if (isRow) {
                rampInfo[start[0]][start[1] + i] += 1;
            } else {
                rampInfo[start[0] + i][start[1]] += 1;
            }
        }

        return true;
    }

    private static boolean canLayRampBack(int[][] map, int[][] rampInfo, int L, int[] start,
        boolean isRow) {
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

            len -= 1;
        }

        for (int i = 0; i < L; i++) {
            if (isRow) {
                rampInfo[start[0]][start[1] + i] += 1;
            } else {
                rampInfo[start[0] + i][start[1]] += 1;
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
