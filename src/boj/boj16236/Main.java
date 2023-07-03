package boj.boj16236;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());

        int[][] map = new int[N][N];

        Shark babyShark = new Shark(0, 0, 0);
        int tmp;
        int numOfFishes = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                tmp = Integer.parseInt(st.nextToken());
                map[i][j] = tmp;
                if (tmp == 9) {
                    babyShark.x = i;
                    babyShark.y = j;
                    continue;
                }
                if (tmp > 0) {
                    numOfFishes++;
                }
            }
        }

        int babySharkSize = 2;
        int eat = 0;
        int movingDist = 0;
        PriorityQueue<Shark> nearestSharks = new PriorityQueue<>();

        while (numOfFishes > 0) {
            nearestSharks.clear();
            calcDistance(map, babySharkSize, babyShark, nearestSharks);
            if (nearestSharks.isEmpty()) {
                break;
            }
            movingDist += eating(map, babyShark, nearestSharks);
            eat++;
            numOfFishes--;

            if (eat == babySharkSize) {
                babySharkSize++;
                eat = 0;
            }
        }

        System.out.println(movingDist);

    }

    private static void calcDistance(int[][] map, int babySize, Shark babyShark,
            PriorityQueue<Shark> nearestSharks) {
        Queue<int[]> queue = new ArrayDeque<>();

        int[] current;
        boolean[][] visited = new boolean[map.length][map.length];

        queue.offer(new int[]{babyShark.x, babyShark.y, 0});
        visited[babyShark.x][babyShark.y] = true;

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        int nextX;
        int nextY;
        int nearestDist = 0;

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (nearestDist > 0 && current[2] == nearestDist) {
                break;
            }

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (-1 < nextX && nextX < map.length &&
                        -1 < nextY && nextY < map.length &&
                        !visited[nextX][nextY] &&
                        map[nextX][nextY] <= babySize) {
                    visited[nextX][nextY] = true;
                    queue.offer(new int[]{nextX, nextY, current[2] + 1});
                    if (map[nextX][nextY] > 0 && map[nextX][nextY] < babySize) {
                        if (nearestDist == 0) {
                            nearestDist = current[2] + 1;
                        }
                        if (current[2] + 1 == nearestDist) {
                            nearestSharks.offer(new Shark(nextX, nextY, nearestDist));
                        }
                    }
                }
            }
        }
    }

    private static int eating(int[][] map, Shark babyShark, PriorityQueue<Shark> nearestSharks) {
        Shark nearest = nearestSharks.poll();

        map[babyShark.x][babyShark.y] = 0;

        babyShark.x = nearest.x;
        babyShark.y = nearest.y;

        map[nearest.x][nearest.y] = 0;

        return nearest.distance;
    }

}

class Shark implements Comparable<Shark> {

    int x;
    int y;
    int distance;

    public Shark(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public int compareTo(Shark o) {
        if (distance > o.distance) {
            return 1;
        } else if (distance < o.distance) {
            return -1;
        } else {
            if (x > o.x) {
                return 1;
            } else if (x < o.x) {
                return -1;
            } else {
                if (y > o.y) {
                    return 1;
                } else if (y < o.y) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
}