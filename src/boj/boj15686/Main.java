package boj.boj15686;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int N;
    private static int[] dx = new int[]{0, 0, -1, 1};
    private static int[] dy = new int[]{-1, 1, 0, 0};
    private static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] city = new int[N][N];
        List<Home> homes = new ArrayList<>();
        List<Chicken> chickens = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                city[i][j] = Integer.parseInt(st.nextToken());
                if (city[i][j] == 1) {
                    homes.add(new Home(new int[]{i, j}));
                }

                if (city[i][j] == 2) {
                    chickens.add(new Chicken(new int[]{i, j}));
                    city[i][j] = 0;
                }
            }
        }

        for (Home home : homes) {
            for (Chicken chicken : chickens) {
                home.distances.put(chicken, findChickenDist(home.pos, chicken.pos));
            }
        }

        findAnswer(homes, chickens, new int[M], M, 0, 0);

        System.out.println(answer);
    }

    private static void findAnswer(List<Home> homes, List<Chicken> chickens, int[] selected,
        int size,
        int current, int index) {
        if (size == current) {
            int dists = 0;

            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (Home home : homes) {
                for (int i = 0; i < size; i++) {
                    pq.offer(home.distances.get(chickens.get(selected[i])));
                }
                dists += pq.poll();
                pq.clear();
            }

            answer = Math.min(answer, dists);

            return;
        }

        int[] c;
        for (int i = index; i < chickens.size(); i++) {
            selected[current] = i;
            findAnswer(homes, chickens, selected, size, current + 1, i + 1);
        }
    }

    private static int findChickenDist(int[] home, int[] chicken) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];

        queue.offer(new int[]{home[0], home[1], 0});
        visited[home[0]][home[1]] = true;

        int[] current;
        int nextX;
        int nextY;
        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (canGo(nextX, nextY, N)) {
                    if (nextX == chicken[0] && nextY == chicken[1]) {
                        return current[2] + 1;
                    }

                    if (!visited[nextX][nextY]) {
                        visited[nextX][nextY] = true;
                        queue.offer(new int[]{nextX, nextY, current[2] + 1});
                    }
                }
            }
        }
        return 0;
    }

    private static boolean canGo(int x, int y, int N) {
        if ((0 <= x && x < N) &&
            (0 <= y && y < N)) {
            return true;
        }
        return false;
    }
}

class Home {

    int[] pos;
    Map<Chicken, Integer> distances = new HashMap<>();

    public Home(int[] pos) {
        this.pos = pos;
    }
}

class Chicken {

    int[] pos;

    public Chicken(int[] pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        Chicken c = (Chicken) o;

        return Arrays.equals(this.pos, c.pos);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(pos);
    }
}