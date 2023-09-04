package boj.boj2638;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int rowMax;
    private static int rowMin;
    private static int colMax;
    private static int colMin;

    private static int[] dx = new int[]{0, 0, -1, 1};
    private static int[] dy = new int[]{-1, 1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        rowMin = 0;
        rowMax = N - 1;
        colMin = 0;
        colMax = M - 1;

        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        int[] rowMinMax = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        int[] colMinMax = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        boolean[][] visited = new boolean[N][M];
        Queue<int[]> canMelt = new ArrayDeque<>();

        while (true) {
            rowMinMax[0] = Integer.MAX_VALUE;
            rowMinMax[1] = Integer.MIN_VALUE;
            colMinMax[0] = Integer.MAX_VALUE;
            colMinMax[1] = Integer.MIN_VALUE;

            for (int i = rowMin; i <= rowMax; i += (rowMax - rowMin)) {
                for (int j = colMin; j <= colMax; j++) {
                    if (!visited[i][j]) {
                        checkOutsideCheeses(map, visited, canMelt, i, j, rowMinMax, colMinMax);
                    }
                }
            }

            for (int i = rowMin; i <= rowMax; i++) {
                for (int j = colMin; j <= colMax; j += (colMax - colMin)) {
                    if (!visited[i][j]) {
                        checkOutsideCheeses(map, visited, canMelt, i, j, rowMinMax, colMinMax);
                    }
                }
            }

            if (!melting(map, canMelt, visited)) {
                break;
            }

            answer += 1;
            setRowAndColMinMax(rowMinMax, colMinMax);
            for (int i = 0; i < N; i++) {
                Arrays.fill(visited[i], false);
            }
        }

        System.out.println(answer);
    }

    private static void checkOutsideCheeses(int[][] map, boolean[][] visited, Queue<int[]> canMelt,
        int startX, int startY,
        int[] rowMinMax, int[] colMinMax) {
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
                    if (map[nextX][nextY] == 0) {
                        visited[nextX][nextY] = true;
                        queue.offer(new int[]{nextX, nextY});
                        continue;
                    }

                    rowMinMax[0] = Math.min(rowMinMax[0], nextX);
                    rowMinMax[1] = Math.max(rowMinMax[1], nextX);
                    colMinMax[0] = Math.min(colMinMax[0], nextY);
                    colMinMax[1] = Math.max(colMinMax[1], nextY);
                    canMelt.offer(new int[]{nextX, nextY});
                }
            }
        }
    }

    private static boolean melting(int[][] map, Queue<int[]> canMelt, boolean[][] visited) {
        int[] cheese;
        boolean isMelted = false;
        while (!canMelt.isEmpty()) {
            cheese = canMelt.poll();

            if (isMeltCheese(map, cheese, visited)) {
                map[cheese[0]][cheese[1]] = 0;
                isMelted = true;
            }
        }

        return isMelted;
    }

    private static boolean isMeltCheese(int[][] map, int[] cheese, boolean[][] visited) {
        int nextX;
        int nextY;
        int numOfAir = 0;
        for (int i = 0; i < 4; i++) {
            nextX = cheese[0] + dx[i];
            nextY = cheese[1] + dy[i];

            if (canGo(nextX, nextY)) {
                if (map[nextX][nextY] == 0 && visited[nextX][nextY]) {
                    numOfAir += 1;
                }
            }
        }

        if (numOfAir >= 2) {
            return true;
        }
        return false;
    }

    private static void setRowAndColMinMax(int[] rowMinMax, int[] colMinMax) {
        rowMin = Math.max(rowMin, rowMinMax[0] - 1);
        rowMax = Math.min(rowMax, rowMinMax[1] + 1);
        colMin = Math.max(colMin, colMinMax[0] - 1);
        colMax = Math.min(colMax, colMinMax[1] + 1);
    }

    private static boolean canGo(int x, int y) {
        if ((rowMin <= x && x <= rowMax) &&
            (colMin <= y && y <= colMax)) {
            return true;
        }
        return false;
    }
}