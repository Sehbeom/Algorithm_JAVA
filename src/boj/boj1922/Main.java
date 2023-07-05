package boj.boj1922;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int M = Integer.parseInt(in.readLine());

        int[] roots = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            roots[i] = i;
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost - o2.cost;
            }
        });

        int a;
        int b;
        int c;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());

            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            if (a != b) {
                pq.offer(new Edge(a, b, c));
            }
        }

        Edge current;
        int node1Root;
        int node2Root;
        while (!pq.isEmpty()) {
            current = pq.poll();

            node1Root = findRoot(roots, current.node1);
            node2Root = findRoot(roots, current.node2);

            if (node1Root != node2Root) {
                roots[node2Root] = node1Root;
                answer += current.cost;
            }
        }

        System.out.println(answer);
    }

    private static int findRoot(int[] roots, int node) {
        if (roots[node] == node) {
            return node;
        }

        return roots[node] = findRoot(roots, roots[node]);
    }
}

class Edge {
    int node1;
    int node2;
    int cost;

    public Edge(int node1, int node2, int cost) {
        this.node1 = node1;
        this.node2 = node2;
        this.cost = cost;
    }
}