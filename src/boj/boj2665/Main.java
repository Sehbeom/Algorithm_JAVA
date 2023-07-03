package boj.boj2665;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(in.readLine());

        Room[][] rooms = new Room[n][n];

        String tmp;
        for (int i = 0; i < n; i++) {
            tmp = in.readLine();
            for (int j = 0; j < n; j++) {
                rooms[i][j] = new Room(new int[] {i, j}, tmp.charAt(j) - '0');
            }
        }

        System.out.println(findAnswer(rooms, n));
    }

    private static int findAnswer(Room[][] rooms, int n) {
        PriorityQueue<RoomForPQ> pq = new PriorityQueue<>(new Comparator<RoomForPQ>() {
            @Override
            public int compare(RoomForPQ o1, RoomForPQ o2) {
                if (o2.room.blackOrWhite > o1.room.blackOrWhite) {
                    return 1;
                } else if (o2.room.blackOrWhite < o1.room.blackOrWhite) {
                    return -1;
                }
                else {
                    return o1.changeCount - o2.changeCount;
                }
            }
        });
        int[][] minChangeCount = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(minChangeCount[i], Integer.MAX_VALUE);
        }

        pq.offer(new RoomForPQ(rooms[0][0], 0));
        minChangeCount[0][0] = 0;

        int[] dx = new int[] {-1, 0, 0, 1};
        int[] dy = new int[] {0, -1, 1, 0};

        RoomForPQ current;
        int nextX = 0;
        int nextY = 0;
        int nextCount = 0;

        while (!pq.isEmpty()) {
            current = pq.poll();

            if (current.room.position[0] == (n - 1) && current.room.position[1] == (n - 1)) {
                return current.changeCount;
            }

            if (current.changeCount > minChangeCount[current.room.position[0]][current.room.position[1]]) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                nextX = current.room.position[0] + dx[i];
                nextY = current.room.position[1] + dy[i];

                if (checkBoundary(nextX, nextY, n, n)) {
                    nextCount = (current.changeCount + (rooms[nextX][nextY].blackOrWhite + 1) % 2);

                    if (minChangeCount[nextX][nextY] > nextCount) {
                        minChangeCount[nextX][nextY] = nextCount;
                        pq.offer(new RoomForPQ(rooms[nextX][nextY], nextCount));
                    }
                }

            }

        }

        return minChangeCount[n - 1][n - 1];
    }

    private static boolean checkBoundary(int x, int y, int bx, int by) {
        if ((0 <= x && x < bx) && (0 <= y && y < by)) {
            return true;
        }
        return false;
    }
}

class RoomForPQ {
    Room room;
    int changeCount;

    public RoomForPQ(Room room, int changeCount) {
        this.room = room;
        this.changeCount = changeCount;
    }
}

class Room {
    int[] position;
    int blackOrWhite;

    public Room (int[] position, int blackOrWhite) {
        this.position = position;
        this.blackOrWhite = blackOrWhite;
    }
}

