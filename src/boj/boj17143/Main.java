package boj.boj17143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<List<Shark>> sharksEachCol = new ArrayList<>();
        for (int i = 0; i < C + 1; i++)
            sharksEachCol.add(new ArrayList<>());

        int x, y, s, d, z;
        int sharkNumber = 0;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());
            s = Integer.parseInt(st.nextToken());
            d = Integer.parseInt(st.nextToken());
            z = Integer.parseInt(st.nextToken());

            sharksEachCol.get(y).add(new Shark(sharkNumber, x, y, s, d, z));

            sharkNumber += 1;
        }

        if (M > 0) {
            for (int i = 1; i < C + 1; i++) {
                if (!sharksEachCol.get(i).isEmpty()) {
                    fishing(sharksEachCol.get(i));
                }

                for (int j = 1; j < C + 1; j++) {
                    for (int k = 0; k < sharksEachCol.get(j).size(); k++) {
                        sharksEachCol.get(j).get(k).moved = false;
                    }
                }

                for (int j = 1; j < C + 1; j++) {
                    for (int k = 0; k < sharksEachCol.get(j).size(); k++) {
                        if (!sharksEachCol.get(j).get(k).moved) {
                            sharksEachCol.get(j).get(k).moving(sharksEachCol, R, C);
                            k--;
                        }
                    }
                }
            }
        }
        System.out.println(answer);
    }

    private static void fishing(List<Shark> fishingCol) {
        int minX = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < fishingCol.size(); i++) {
            if (minX > fishingCol.get(i).x) {
                minX = fishingCol.get(i).x;
                minIndex = i;
            }
        }

        answer += fishingCol.remove(minIndex).size;
    }
}

class Shark {
    int number;
    int x;
    int y;
    int speed;
    int direction;
    int size;
    boolean moved;
    int[] dxdy;

    public Shark(int number, int x, int y, int speed, int direction, int size) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.moved = false;
        setDirection(direction);
    }

    private void setDirection(int direction) {
        if (direction < 3) {
            dxdy = new int[] { 1, 0 };
            this.direction = direction == 1 ? -1 : 1;
        } else {
            dxdy = new int[] { 0, 1 };
            this.direction = direction == 3 ? 1 : -1;
        }
    }

    public boolean moving(List<List<Shark>> sharksEachCol, int R, int C) {
        sharksEachCol.get(y).remove(this);

        int nextX = x;
        int nextY = y;
        for (int i = 1; i <= speed; i++) {
            if ((nextX + dxdy[0] * direction) < 1 || R < (nextX + dxdy[0] * direction) ||
                    (nextY + dxdy[1] * direction) < 1 || C < (nextY + dxdy[1] * direction)) {
                direction *= -1;
            }
            nextX += dxdy[0] * direction;
            nextY += dxdy[1] * direction;
        }
        boolean sameCol = y == nextY;

        x = nextX;
        y = nextY;

        Shark temp;
        boolean duplicated = false;
        for (int i = 0; i < sharksEachCol.get(y).size(); i++) {
            temp = sharksEachCol.get(y).get(i);
            if (x == temp.x && temp.moved) {
                duplicated = true;
                if (size > temp.size) {
                    sharksEachCol.get(y).remove(temp);
                    sharksEachCol.get(y).add(this);
                    moved = true;
                } else {
                    sameCol = false;
                }
            }
        }

        if (!duplicated) {
            sharksEachCol.get(y).add(this);
            moved = true;
        }

        return sameCol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shark shark = (Shark) o;
        return number == shark.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}