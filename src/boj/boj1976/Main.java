package boj.boj1976;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int M = Integer.parseInt(in.readLine());

        City[] cities = new City[N + 1];
        for (int i = 1; i <= N; i++) {
            cities[i] = new City(i);
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 1; j <= N; j++) {
                if (st.nextToken().equals("1")) {
                    cities[i].linkWith.add(j);
                    cities[j].linkWith.add(i);
                }
            }
        }

        st = new StringTokenizer(in.readLine());

        int[] haveToGo = new int[M];
        for (int i = 0; i < M; i++) {
            haveToGo[i] = Integer.parseInt(st.nextToken());
        }

        boolean[][] canGo = new boolean[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            canGo[i][i] = true;
        }

        for (int i = 1; i <= N; i++) {
            checkCanGo(cities, canGo, i);
        }

        int from;
        int to;

        for (int i = 0; i < (M - 1); i++) {
            from = haveToGo[i];
            to = haveToGo[i + 1];

            if (!canGo[from][to]) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }

    private static void checkCanGo(City[] cities, boolean[][] canGo, int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[cities.length];

        queue.offer(start);
        visited[start] = true;

        int current;
        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int l : cities[current].linkWith) {
                if (!visited[l]) {
                    visited[l] = true;
                    canGo[start][l] = true;
                    canGo[l][start] = true;
                    queue.offer(l);
                }
            }
        }
    }
}

class City {

    int number;
    List<Integer> linkWith = new ArrayList<>();

    public City(int number) {
        this.number = number;
    }
}