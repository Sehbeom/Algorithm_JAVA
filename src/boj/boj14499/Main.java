package boj.boj14499;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] curPos = new int[2];
        curPos[0] = Integer.parseInt(st.nextToken());
        curPos[1] = Integer.parseInt(st.nextToken());

        int K = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][M];
        int[] moveOrders = new int[K];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < K; i++) {
            moveOrders[i] = Integer.parseInt(st.nextToken());
        }

        int[][] diceInfo = new int[2][6];
        int fromDice = 0;
        int toDice = 1;
        int swapTmp = 0;
        boolean canGo;
        for (int i = 0; i < K; i++) {
            canGo = false;
            switch (moveOrders[i]) {
                case 1:
                    if (canMove(curPos[0], curPos[1] + 1, N, M)) {
                        canGo = true;
                        curPos[1] += 1;
                        MoveOrder.moveToEast(diceInfo[fromDice], diceInfo[toDice]);
                        copyingValue(map, curPos, diceInfo[toDice]);
                    }
                    break;
                case 2:
                    if (canMove(curPos[0], curPos[1] - 1, N, M)) {
                        canGo = true;
                        curPos[1] -= 1;
                        MoveOrder.moveToWest(diceInfo[fromDice], diceInfo[toDice]);
                        copyingValue(map, curPos, diceInfo[toDice]);
                    }
                    break;
                case 3:
                    if (canMove(curPos[0] - 1, curPos[1], N, M)) {
                        canGo = true;
                        curPos[0] -= 1;
                        MoveOrder.moveToNorth(diceInfo[fromDice], diceInfo[toDice]);
                        copyingValue(map, curPos, diceInfo[toDice]);
                    }
                    break;
                case 4:
                    if (canMove(curPos[0] + 1, curPos[1], N, M)) {
                        canGo = true;
                        curPos[0] += 1;
                        MoveOrder.moveToSouth(diceInfo[fromDice], diceInfo[toDice]);
                        copyingValue(map, curPos, diceInfo[toDice]);
                    }
                    break;
            }

            if (canGo) {
                sb.append(diceInfo[toDice][5]).append("\n");
                swapTmp = fromDice;
                fromDice = toDice;
                toDice = swapTmp;
            }
        }

        System.out.println(sb);

    }

    private static void copyingValue(int[][] map, int[] curPos, int[] diceInfo) {
        int x = curPos[0];
        int y = curPos[1];
        if (map[x][y] > 0) {
            diceInfo[4] = map[x][y];
            map[x][y] = 0;
        } else {
            map[x][y] = diceInfo[4];
        }
    }

    private static boolean canMove(int x, int y, int N, int M) {
        if ((0 <= x && x < N) && (0 <= y && y < M)) {
            return true;
        }
        return false;
    }
}

class MoveOrder {
    public static void moveToEast(int[] fromDice, int[] toDice) {
        toDice[0] = fromDice[5];
        toDice[1] = fromDice[4];
        toDice[2] = fromDice[2];
        toDice[3] = fromDice[3];
        toDice[4] = fromDice[0];
        toDice[5] = fromDice[1];
    }

    public static void moveToWest(int[] fromDice, int[] toDice) {
        toDice[0] = fromDice[4];
        toDice[1] = fromDice[5];
        toDice[2] = fromDice[2];
        toDice[3] = fromDice[3];
        toDice[4] = fromDice[1];
        toDice[5] = fromDice[0];
    }

    public static void moveToSouth(int[] fromDice, int[] toDice) {
        toDice[0] = fromDice[0];
        toDice[1] = fromDice[1];
        toDice[2] = fromDice[5];
        toDice[3] = fromDice[4];
        toDice[4] = fromDice[2];
        toDice[5] = fromDice[3];
    }

    public static void moveToNorth(int[] fromDice, int[] toDice) {
        toDice[0] = fromDice[0];
        toDice[1] = fromDice[1];
        toDice[2] = fromDice[4];
        toDice[3] = fromDice[5];
        toDice[4] = fromDice[3];
        toDice[5] = fromDice[2];
    }
}