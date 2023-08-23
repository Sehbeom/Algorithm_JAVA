package boj.boj2206;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    private static int answer = Integer.MAX_VALUE;
    private static int N;
    private static int M;

    private static int[] dx = new int[]{0, 0, -1, 1};
    private static int[] dy = new int[]{-1, 1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(in.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N + 1][M + 1];
        String tmp;
        for (int i = 1; i <= N; i++) {
            tmp = in.readLine();
            for (int j = 1; j <= M; j++) {
                map[i][j] = tmp.charAt(j - 1) - '0';
            }
        }

        if (N == 1 && M == 1) {
            System.out.println(1);
            return;
        }

        checkBreak(map);

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    private static void checkBreak(int[][] map) {
        PriorityQueue<Room> pq = new PriorityQueue<>();
        pq.offer(new Room(1, 1, false, 1));

        int[][] minCostsNotBroken = new int[N + 1][M + 1];
        int[][] minCostsBroken = new int[N + 1][M + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(minCostsNotBroken[i], Integer.MAX_VALUE);
            Arrays.fill(minCostsBroken[i], Integer.MAX_VALUE);
        }

        minCostsNotBroken[1][1] = 1;

        Room current;
        int nextX;
        int nextY;
        while (!pq.isEmpty()) {
            current = pq.poll();

            if (current.broken) {
                if (current.depth > minCostsBroken[current.x][current.y]) {
                    continue;
                }
            } else {
                if (current.depth > minCostsNotBroken[current.x][current.y]) {
                    continue;
                }
            }

            for (int i = 0; i < 4; i++) {
                nextX = current.x + dx[i];
                nextY = current.y + dy[i];

                if (canGo(nextX, nextY)) {
                    if (map[nextX][nextY] == 1) {
                        if ((!current.broken)
                            && (current.depth + 1) < minCostsBroken[nextX][nextY]) {
                            pq.offer(new Room(nextX, nextY, true, current.depth + 1));
                            minCostsBroken[nextX][nextY] = current.depth + 1;
                        }
                        continue;
                    }

                    if (current.broken) {
                        checkMinCostsBroken(minCostsBroken, pq, current, nextX, nextY);
                    } else {
                        checkMinCostsNotBroken(minCostsNotBroken, pq, current, nextX, nextY);
                    }
                }
            }
        }

        answer = Math.min(minCostsBroken[N][M], minCostsNotBroken[N][M]);
    }

    private static void checkMinCostsNotBroken(int[][] minCostsNotBroken,
        PriorityQueue<Room> pq, Room current, int nextX, int nextY) {
        if ((current.depth + 1) < minCostsNotBroken[nextX][nextY]) {
            pq.offer(new Room(nextX, nextY, current.broken, current.depth + 1));
            minCostsNotBroken[nextX][nextY] = current.depth + 1;
        }
    }

    private static void checkMinCostsBroken(int[][] minCostsBroken,
        PriorityQueue<Room> pq, Room current, int nextX, int nextY) {
        if ((current.depth + 1) < minCostsBroken[nextX][nextY]) {
            pq.offer(new Room(nextX, nextY, current.broken, current.depth + 1));
            minCostsBroken[nextX][nextY] = current.depth + 1;
        }
    }

    private static boolean canGo(int x, int y) {
        if ((1 <= x && x <= N) &&
            (1 <= y && y <= M)) {
            return true;
        }

        return false;
    }
}

class Room implements Comparable<Room> {

    int x;
    int y;
    boolean broken;
    int depth;

    public Room(int x, int y, boolean broken, int depth) {
        this.x = x;
        this.y = y;
        this.broken = broken;
        this.depth = depth;
    }

    @Override
    public int compareTo(Room o) {
        return this.depth - o.depth;
    }
}