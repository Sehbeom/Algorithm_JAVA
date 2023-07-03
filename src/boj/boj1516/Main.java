package boj.boj1516;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());

        Building[] buildings = new Building[N + 1];
        for (int i = 1; i <= N; i++) {
            buildings[i] = new Building(i);
        }

        int timeInput = 0;
        int parentInput = 0;
        Queue<Integer> roots = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(in.readLine());

            timeInput = Integer.parseInt(st.nextToken());
            buildings[i].time = timeInput;

            parentInput = Integer.parseInt(st.nextToken());

            while (parentInput != -1) {
                buildings[parentInput].child.offer(i);
                buildings[i].canCome += 1;
                parentInput = Integer.parseInt(st.nextToken());
            }

            if (buildings[i].canCome == 0) {
                roots.offer(i);
            }
        }

        int[] minTimes = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            minTimes[i] = buildings[i].time;
        }

        setMinTimes(roots, buildings, minTimes);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(minTimes[i]).append("\n");
        }
        System.out.println(sb);

    }

    private static void setMinTimes(Queue<Integer> roots, Building[] buildings, int[] minTimes) {
        int current;
        int child;
        while (!roots.isEmpty()) {
            current = roots.poll();

            while (!buildings[current].child.isEmpty()) {
                child = buildings[current].child.poll();
                if (minTimes[child] < (minTimes[current] + buildings[child].time)) {
                    minTimes[child] = minTimes[current] + buildings[child].time;
                }
                buildings[child].canCome -= 1;

                if (buildings[child].canCome == 0) {
                    roots.offer(child);
                }
            }
        }
    }
}

class Building {
    int number;
    Queue<Integer> child = new ArrayDeque<>();
    int canCome;
    int time;

    public Building(int number) {
        this.number = number;
    }
}