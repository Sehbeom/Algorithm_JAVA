package boj.boj1504;

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

    private static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        Node[] nodes = new Node[N + 1];
        for (int i = 1; i <= N; i++) {
            nodes[i] = new Node(i);
        }

        int a;
        int b;
        int c;
        for (int i = 1; i <= E; i++) {
            st = new StringTokenizer(in.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            nodes[a].linkWith.add(new ToEdge(b, c));
            nodes[b].linkWith.add(new ToEdge(a, c));
        }

        st = new StringTokenizer(in.readLine());

        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        int[][] cases = new int[][] {
            {1, v1, v2, N},
            {1, v2, v1, N}
        };

        int costSum = 0;
        int result = 0;

        continuePoint : for (int i = 0; i < 2; i++) {
            costSum = 0;
            for (int j = 0; j < 3; j++) {
                result = findShortestPath(nodes, N, cases[i][j], cases[i][j + 1]);
                if (result == Integer.MAX_VALUE) {
                    continue continuePoint;
                }
                costSum += result;
            }
            answer = Math.min(answer, costSum);
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    private static int findShortestPath(Node[] nodes, int N, int start, int dst) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        int[] shortestPath = new int[N + 1];
        Arrays.fill(shortestPath, Integer.MAX_VALUE);
        shortestPath[start] = 0;

        pq.offer(new int[]{start, 0});

        int[] current;
        while (!pq.isEmpty()) {
            current = pq.poll();

            if (current[1] > shortestPath[current[0]]) {
                continue;
            }

            for (ToEdge n : nodes[current[0]].linkWith) {
                if ((current[1] + n.cost) < shortestPath[n.to]) {
                    shortestPath[n.to] = current[1] + n.cost;
                    pq.offer(new int[]{n.to, current[1] + n.cost});
                }
            }

        }

        return shortestPath[dst];
    }
}

class Node {

    int number;
    List<ToEdge> linkWith = new ArrayList<>();

    public Node(int number) {
        this.number = number;
    }
}

class ToEdge {

    int to;
    int cost;

    public ToEdge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}