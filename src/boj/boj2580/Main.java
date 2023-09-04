package boj.boj2580;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int[][] map = new int[10][10];
        LinkedList<EmptySpot> emptySpots = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 1; j < 10; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                if (map[i][j] == 0) {
                    emptySpots.add(new EmptySpot(i, j));
                }
            }
        }
//
//        boolean isOneEmptyExist = true;
//
//        while (isOneEmptyExist) {
//            isOneEmptyExist = false;
//            for (int i = 0; i < emptySpots.size(); i++) {
//                if (isOneEmptySpotFilled(map, emptySpots.get(i))) {
//                    isOneEmptyExist = true;
//                    emptySpots.remove(i);
//                    i -= 1;
//                }
//            }
//        }
//
//        if (emptySpots.isEmpty()) {
//            makeAnswer(map, sb);
//            System.out.println(sb);
//            return;
//        }

        findAllFilledMap(map, emptySpots, 0);
        makeAnswer(map, sb);
        System.out.println(sb);

    }

    private static boolean findAllFilledMap(int[][] map, LinkedList<EmptySpot> emptySpots,
        int current) {
        EmptySpot emptySpot = emptySpots.get(current);
        Set<Integer> removedCanBe = new HashSet<>(emptySpot.canBe);

        checkRow(map, emptySpot.x, emptySpot.y, removedCanBe);
        checkCol(map, emptySpot.x, emptySpot.y, removedCanBe);
        checkRoom(map, emptySpot.x, emptySpot.y, removedCanBe);

        if (current == (emptySpots.size() - 1)) {
            for (int n : removedCanBe) {
                map[emptySpot.x][emptySpot.y] = n;
            }
            return true;
        }

        for (int n : removedCanBe) {
            map[emptySpot.x][emptySpot.y] = n;
            if (findAllFilledMap(map, emptySpots, current + 1)) {
                return true;
            }
            map[emptySpot.x][emptySpot.y] = 0;
        }

        return false;
    }

    private static void makeAnswer(int[][] map, StringBuilder sb) {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                sb.append(map[i][j]).append(" ");
            }
            sb.append("\n");
        }
    }

    private static boolean isOneEmptySpotFilled(int[][] map, EmptySpot emptySpot) {
        checkRow(map, emptySpot.x, emptySpot.y, emptySpot.canBe);
        checkCol(map, emptySpot.x, emptySpot.y, emptySpot.canBe);
        checkRoom(map, emptySpot.x, emptySpot.y, emptySpot.canBe);

        if (emptySpot.canBe.size() == 1) {
            for (int n : emptySpot.canBe) {
                map[emptySpot.x][emptySpot.y] = n;
            }

            return true;
        }

        return false;
    }

    private static void checkRow(int[][] map, int curX, int curY, Set<Integer> canBe) {
        for (int i = 1; i < 10; i++) {
            if (i == curY) {
                continue;
            }

            if (map[curX][i] == 0) {
                continue;
            }

            canBe.remove(map[curX][i]);
        }
    }

    private static void checkCol(int[][] map, int curX, int curY, Set<Integer> canBe) {
        for (int i = 1; i < 10; i++) {
            if (i == curX) {
                continue;
            }

            if (map[i][curY] == 0) {
                continue;
            }

            canBe.remove(map[i][curY]);
        }
    }

    private static void checkRoom(int[][] map, int curX, int curY, Set<Integer> canBe) {
        int startX = findStart(curX);
        int startY = findStart(curY);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((startX + i) == curX &&
                    (startY + j) == curY) {
                    continue;
                }

                if (map[startX + i][startY + j] == 0) {
                    continue;
                }

                canBe.remove(map[startX + i][startY + j]);
            }
        }


    }

    private static int findStart(int value) {
        if ((1 <= value && value < 4)) {
            return 1;
        }

        if ((4 <= value && value < 7)) {
            return 4;
        }

        return 7;
    }
}

class EmptySpot {

    int x;
    int y;
    Set<Integer> canBe = new HashSet<>();

    public EmptySpot(int x, int y) {
        this.x = x;
        this.y = y;
        for (int i = 1; i < 10; i++) {
            canBe.add(i);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        EmptySpot e = (EmptySpot) o;
        if (this.x == e.x && this.y == e.y) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}