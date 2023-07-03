package boj.boj14938;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());

        Area[] areas = new Area[n + 1];
        for (int i = 1; i <= n; i++) {
            areas[i] = new Area(i, Integer.parseInt(st.nextToken()));
        }

        int area1;
        int area2;
        int cost;
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(in.readLine());
            area1 = Integer.parseInt(st.nextToken());
            area2 = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            areas[area1].linkWith.add(new Road(area2, cost));
            areas[area2].linkWith.add(new Road(area1, cost));
        }

        int[] shortestPath = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            Arrays.fill(shortestPath, Integer.MAX_VALUE);
            shortestPath[i] = 0;
            setShortestPath(areas, shortestPath, i);
            setAnswer(areas, shortestPath, n, m);
        }

        System.out.println(answer);
    }

    private static void setAnswer(Area[] areas, int[] shortestPath, int n, int m) {
        int totalCountOfItems = 0;
        for (int i = 1; i <= n; i++) {
            if (shortestPath[i] <= m) {
                totalCountOfItems += areas[i].numOfItems;
            }
        }
        answer = Math.max(answer, totalCountOfItems);
    }

    private static void setShortestPath(Area[] areas, int[] shortestPath, int start) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        pq.offer(new int[] {start, 0});

        int[] current;
        int nextCost;

        while (!pq.isEmpty()) {
            current = pq.poll();

            if (current[1] > shortestPath[current[0]])
                continue;

            for (Road road : areas[current[0]].linkWith) {
                nextCost = current[1] + road.cost;
                if (nextCost < shortestPath[road.to]) {
                    shortestPath[road.to] = nextCost;
                    pq.offer(new int[] {road.to, nextCost});
                }
            }
        }


    }
}

class Area {
    int number;
    int numOfItems;
    List<Road> linkWith = new ArrayList<>();

    public Area(int number, int numOfItems) {
        this.number = number;
        this.numOfItems = numOfItems;
    }
}

class Road {
    int to;
    int cost;

    public Road(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}