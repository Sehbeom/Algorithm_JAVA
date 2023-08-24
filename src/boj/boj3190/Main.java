package boj.boj3190;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int K = Integer.parseInt(in.readLine());

        int[][] map = new int[N + 1][N + 1];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(in.readLine());
            map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 1;
        }

        int L = Integer.parseInt(in.readLine());
        char[] directions = new char[10_001];
        for (int i = 0; i < L; i++) {
            st = new StringTokenizer(in.readLine());
            directions[Integer.parseInt(st.nextToken())] = st.nextToken().charAt(0);
        }

        int time = 1;
        char dir = directions[1];
        Snake snake = new Snake();

        while (snake.move(map, dir)) {
            time += 1;
            if (time < 10_001) {
                dir = directions[time];
            } else {
                dir = ' ';
            }
        }

        System.out.println(time);

    }
}

class Snake {

    Deque<int[]>[] bodies = new Deque[2];

    int direction;
    int curBody;

    int[] dx = new int[]{-1, 0, 1, 0};
    int[] dy = new int[]{0, 1, 0, -1};

    public Snake() {
        bodies[0] = new ArrayDeque<>();
        bodies[1] = new ArrayDeque<>();
        bodies[0].offerFirst(new int[]{1, 1});
        direction = 1;
        curBody = 0;
    }

    public boolean move(int[][] map, char dir) {
        int[] curHead = bodies[curBody].peekFirst();
        int[] nextHead = new int[]{curHead[0] + dx[direction], curHead[1] + dy[direction]};

        if (!canGo(nextHead, map.length - 1)) {
            return false;
        }

        int nextBody = (curBody + 1) % 2;

        bodies[nextBody].offerFirst(nextHead);

        while (!bodies[curBody].isEmpty()) {
            bodies[nextBody].offerLast(bodies[curBody].pollFirst());
        }

        if (map[nextHead[0]][nextHead[1]] == 0) {
            bodies[nextBody].pollLast();
        }

        map[nextHead[0]][nextHead[1]] = 0;

        curBody = nextBody;

        if (dir == 'L' || dir == 'D') {
            changeDirection(dir);
        }

        return true;
    }

    private void changeDirection(char direction) {
        if (direction == 'L') {
            this.direction = (this.direction - 1 == -1) ? 3 : this.direction - 1;
            return;
        }

        if (direction == 'D') {
            this.direction = (this.direction + 1 == 4) ? 0 : this.direction + 1;
        }
    }

    private boolean canGo(int[] nextHead, int N) {
        if ((0 < nextHead[0] && nextHead[0] <= N) &&
            (0 < nextHead[1] && nextHead[1] <= N) &&
            !meetBody(nextHead)) {
            return true;
        }

        return false;
    }

    private boolean meetBody(int[] nextHead) {
        for (int[] p : this.bodies[curBody]) {
            if (p[0] == nextHead[0] && p[1] == nextHead[1]) {
                return true;
            }
        }
        return false;
    }
}