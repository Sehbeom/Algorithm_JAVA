package boj.boj17135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class CastleDefense {

    private static int answer = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][M];
        int numOfEnemies = 0;

        int tmp;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < M; j++) {
                tmp = Integer.parseInt(st.nextToken());
                map[i][j] = tmp;
                if (tmp == 1) {
                    numOfEnemies++;
                }
            }
        }

        putArchers(map, N, M, D, 0, 0, new int[3], numOfEnemies);

        System.out.println(answer);

    }

    private static void putArchers(int[][] map, int N, int M, int D, int cnt, int index, int[] selected, int numOfEnemies) {
        if (cnt == 3) {
            int[][] cloneMap = new int[N][M];
            for (int i = 0; i < N; i++)
                cloneMap[i] = map[i].clone();
            defense(cloneMap, N, M, D, new int[][] {{N-1, selected[0]}, {N-1, selected[1]}, {N-1, selected[2]}}, numOfEnemies);

            return;
        }

        for (int i = index; i < M; i++) {
            selected[cnt] = i;
            putArchers(map, N, M, D, cnt + 1, i + 1, selected, numOfEnemies);
        }
    }

    private static void defense(int[][] map, int N, int M, int D, int[][] archers, int numOfEnemies) {
        PriorityQueue<Enemy>[] archerPQ = new PriorityQueue[3];
        for (int i = 0; i < 3; i++)
            archerPQ[i] = new PriorityQueue<>();

        int killCount = 0;
        int killed;
        int turnOfGames = 0;
        while (numOfEnemies > 0 && turnOfGames < map.length) {
            killed = 0;
            for (int i = 0; i < 3; i++) {
                archerPQ[i].clear();
                checkEnemy(map, archers[i], archerPQ[i], D);
            }
            for (int i = 0; i < 3; i++) {
                killed += killEnemies(map, archerPQ[i]);
            }

            killCount += killed;
            numOfEnemies -= killed;
            killed = enemyMove(map, turnOfGames);
            numOfEnemies -= killed;
            turnOfGames += 1;
        }

        answer = Math.max(answer, killCount);
    }

    private static int killEnemies(int[][] map, PriorityQueue<Enemy> archerPQ) {
        int killed = 0;
        if (!archerPQ.isEmpty()) {
            Enemy toKill = archerPQ.poll();
            killed = map[toKill.x][toKill.y];
            map[toKill.x][toKill.y] = 0;
        }

        return killed;
    }

    private static int enemyMove(int[][] map, int turnsOfGames) {
        int inCastle = 0;

        for (int i = map.length - 1; i >= turnsOfGames; i--) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == (map.length - 1)) {
                    if (map[i][j] == 1)
                        inCastle += 1;
                }
                else {
                    map[i + 1][j] = map[i][j];
                    if (i == turnsOfGames) map[i][j] = 0;
                }
            }
        }

        return inCastle;
    }

    private static void checkEnemy(int[][] map, int[] archer, PriorityQueue<Enemy> archerPQ, int range) {
        if (map[archer[0]][archer[1]] == 1)
            archerPQ.offer(new Enemy(archer[0], archer[1], 0));

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[map.length][map[0].length];

        queue.offer(new int[] { archer[0], archer[1], 0 });
        visited[archer[0]][archer[1]] = true;

        int[] current;
        int[] dx = { 0, 0, 1, -1 };
        int[] dy = { -1, 1, 0, 0 };
        int nextX;
        int nextY;
        int nearest = 0;

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (current[2] == range - 1)
                break;

            if (nearest != 0 && nearest == current[2]) break;

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (-1 < nextX && nextX < map.length &&
                        -1 < nextY && nextY < map[0].length &&
                        !visited[nextX][nextY]) {
                    if (nearest == 0 && map[nextX][nextY] == 1)
                        nearest = current[2] + 1;
                    if (nearest != 0 && (current[2] + 1) == nearest && map[nextX][nextY] == 1)
                        archerPQ.add(new Enemy(nextX, nextY, current[2] + 1));
                    queue.offer(new int[] { nextX, nextY, current[2] + 1 });
                    visited[nextX][nextY] = true;
                }
            }
        }
    }

}

class Enemy implements Comparable<Enemy> {
    int x;
    int y;
    int distance;

    Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Enemy(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int compareTo(Enemy o) {
        if (distance > o.distance)
            return 1;
        else if (distance < o.distance)
            return -1;
        else {
            if (y < o.y)
                return -1;
            else if (y > o.y)
                return 1;
            else
                return 0;
        }
    }

}