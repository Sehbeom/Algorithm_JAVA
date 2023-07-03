package boj.boj1414;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());
        int[][] edgeInfo = new int[N][N];
        boolean[][] isLinked = new boolean[N][N];

        String tmp;
        int cost = 0;
        for (int i = 0; i < N; i++) {
            tmp = in.readLine();
            for (int j = 0; j < N; j++) {
                cost = tmp.charAt(j);
                if (cost == 48) continue;
                cost = cost >= 97 ? cost - 96 : cost - 38;
                edgeInfo[i][j] = cost;
                isLinked[i][j] = true;
                isLinked[j][i] = true;
            }
        }

        if (!checkAllLinked(isLinked)) {
            System.out.println(-1);
            return;
        }

        for (int i = 0; i < N; i++) {
            answer += edgeInfo[i][i];
            edgeInfo[i][i] = 0;
        }



        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o2.cost - o1.cost;
            }
        });
        int[] edgeCountOfEachNode = new int[N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (edgeInfo[i][j] > 0) {
                    pq.offer(new Edge(i, j, edgeInfo[i][j]));
                    edgeCountOfEachNode[i] += 1;
                    edgeCountOfEachNode[j] += 1;
                }
            }
        }

        PriorityQueue<Edge> secondPq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o2.cost - o1.cost;
            }
        });

        Edge current;
        while (!pq.isEmpty()) {
            current = pq.poll();

            if (edgeCountOfEachNode[current.from] > 2 && edgeCountOfEachNode[current.to] > 2) {
                answer += current.cost;
                edgeCountOfEachNode[current.from] -= 1;
                edgeCountOfEachNode[current.to] -= 1;
            } else {
                secondPq.offer(current);
            }
        }

        if (secondPq.size() > (N - 1)) {
            if (!secondPq.isEmpty())
                answer += secondPq.poll().cost;
        }

        System.out.println(answer);
    }

    private static boolean checkAllLinked(boolean[][] isLinked) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[isLinked.length];

        queue.offer(0);
        visited[0] = true;

        int current;

        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < isLinked.length; i++) {
                if (isLinked[current][i] && !visited[i]) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i])
                return false;
        }

        return true;
    }
}
//
//class Edge {
//    int from;
//    int to;
//    int cost;
//
//    public Edge(int from, int to, int cost) {
//        this.from = from;
//        this.to = to;
//        this.cost = cost;
//    }
//}