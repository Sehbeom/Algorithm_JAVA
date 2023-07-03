package swea.swea5644;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class WirelessCharge {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int time;
        int numOfBC;
        int[] A;
        int[] B;
        int[][] moveInfo = {{0, 0}, {-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int[][] BCInfo;
        int[][][] map;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            st = new StringTokenizer(in.readLine());
            time = Integer.parseInt(st.nextToken());
            numOfBC = Integer.parseInt(st.nextToken());

            A = new int[time];
            B = new int[time];
            map = new int[10][10][numOfBC];

            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < time; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < time; i++) {
                B[i] = Integer.parseInt(st.nextToken());
            }

            BCInfo = new int[numOfBC + 1][4];
            for (int i = 1; i <= numOfBC; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < 4; j++) {
                    if (j < 2) {
                        BCInfo[i][j] = Integer.parseInt(st.nextToken()) - 1;
                    } else {
                        BCInfo[i][j] = Integer.parseInt(st.nextToken());
                    }
                }
            }

            for (int i = 1; i <= numOfBC; i++) {
                setCharger(map, i, BCInfo[i][2], new int[]{BCInfo[i][0], BCInfo[i][1]}, moveInfo);
            }

            sb.append(movingAAndB(map, A, B, BCInfo, moveInfo)).append("\n");
        }

        System.out.println(sb);
    }

    private static void setCharger(int[][][] map, int BCNum, int range, int[] BCPlace,
            int[][] moveInfo) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{BCPlace[1], BCPlace[0], range});

        boolean[][] visited = new boolean[10][10];
        visited[BCPlace[1]][BCPlace[0]] = true;

        int[] current;
        int index;
        int nextX;
        int nextY;

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (current[2] == -1) {
                break;
            }

            index = 0;

            while (map[current[0]][current[1]][index] != 0) {
                index++;
            }
            map[current[0]][current[1]][index] = BCNum;

            for (int i = 1; i < 5; i++) {
                nextX = current[0] + moveInfo[i][0];
                nextY = current[1] + moveInfo[i][1];

                if (-1 < nextX && nextX < 10 && -1 < nextY && nextY < 10
                        && !visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    queue.offer(new int[]{nextX, nextY, current[2] - 1});
                }
            }
        }
    }

    private static int movingAAndB(int[][][] map, int[] A, int[] B, int[][] BCInfo,
            int[][] moveInfo) {
        int Ax = 0;
        int Ay = 0;
        int Bx = 9;
        int By = 9;

        int[] charge = new int[2];

        if (map[0][0][0] != 0) {
            charge[0] += getOneMaxCharge(map, BCInfo, new int[]{0, 0});
        }
        if (map[9][9][0] != 0) {
            charge[1] += getOneMaxCharge(map, BCInfo, new int[]{9, 9});
        }

        for (int i = 0; i < A.length; i++) {
            Ax = Ax + moveInfo[A[i]][0];
            Ay = Ay + moveInfo[A[i]][1];
            Bx = Bx + moveInfo[B[i]][0];
            By = By + moveInfo[B[i]][1];

            if (map[Ax][Ay][0] != 0 && map[Bx][By][0] != 0) {
                setAllMaxCharge(map, BCInfo, new int[]{Ax, Ay}, new int[]{Bx, By}, charge);
            } else {
                if (map[Ax][Ay][0] != 0) {
                    charge[0] += getOneMaxCharge(map, BCInfo, new int[]{Ax, Ay});
                }
                if (map[Bx][By][0] != 0) {
                    charge[1] += getOneMaxCharge(map, BCInfo, new int[]{Bx, By});
                }
            }
        }

        return charge[0] + charge[1];
    }

    private static int getOneMaxCharge(int[][][] map, int[][] BCInfo, int[] pos) {
        int x = pos[0];
        int y = pos[1];

        int numOfBC = 0;
        while (map[x][y][numOfBC] != 0) {
            numOfBC++;
        }

        int maxCharge = 0;

        for (int i = 0; i < numOfBC; i++) {
            if (maxCharge < map[x][y][i]) {
                maxCharge = BCInfo[map[x][y][i]][3];
            }
        }

        return maxCharge;
    }

    private static void setAllMaxCharge(int[][][] map, int[][] BCInfo, int[] APos,
            int[] BPos, int[] charge) {
        int Ax = APos[0];
        int Ay = APos[1];
        int Bx = BPos[0];
        int By = BPos[1];

        int numOfBCA = 0;
        while (map[Ax][Ay][numOfBCA] != 0) {
            numOfBCA++;
        }
        int numOfBCB = 0;
        while (map[Bx][By][numOfBCB] != 0) {
            numOfBCB++;
        }

        if (numOfBCA == 1 && numOfBCB == 1) {
            if (map[Ax][Ay][0] != map[Bx][Bx][0]) {
                charge[0] += BCInfo[map[Ax][Ay][0]][3];
                charge[1] += BCInfo[map[Bx][By][0]][3];
            } else {
                charge[0] += BCInfo[map[Ax][Ay][0]][3] / 2;
                charge[1] += BCInfo[map[Bx][Bx][0]][3] / 2;
            }

            return;
        }

        int totalMaxCharge = 0;
        int AMaxCharge = 0;
        int BMaxCharge = 0;

        for (int i = 0; i < numOfBCA; i++) {
            for (int j = 0; j < numOfBCB; j++) {
                if (map[Ax][Ay][i] == map[Bx][By][j]) {
                    if (totalMaxCharge < (BCInfo[map[Ax][Ay][i]][3])) {
                        totalMaxCharge = BCInfo[map[Ax][Ay][i]][3];
                        AMaxCharge = totalMaxCharge / 2;
                        BMaxCharge = totalMaxCharge / 2;
                    }
                } else {
                    if (totalMaxCharge < (BCInfo[map[Ax][Ay][i]][3] + BCInfo[map[Bx][By][j]][3])) {
                        totalMaxCharge = BCInfo[map[Ax][Ay][i]][3] + BCInfo[map[Bx][By][j]][3];
                        AMaxCharge = BCInfo[map[Ax][Ay][i]][3];
                        BMaxCharge = BCInfo[map[Bx][By][j]][3];
                    }
                }
            }
        }

        charge[0] += AMaxCharge;
        charge[1] += BMaxCharge;
    }
}
