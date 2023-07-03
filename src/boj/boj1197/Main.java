package boj.boj1197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        int[] roots = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            roots[i] = i;
        }

        PriorityQueue<Edge> edges = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.cost - o2.cost;
            }
        });

        int node1;
        int node2;
        int cost;

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(in.readLine());
            node1 = Integer.parseInt(st.nextToken());
            node2 = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            if (node1 != node2) {
                edges.offer(new Edge(
                        node1,
                        node2,
                        cost
                ));
            }
        }

        Edge current;
        int rootOfNode1;
        int rootOfNode2;
        int answer = 0;

        while (!edges.isEmpty()) {
            current = edges.poll();

            rootOfNode1 = findRoot(roots, current.node1);
            rootOfNode2 = findRoot(roots, current.node2);
            if (rootOfNode1 != rootOfNode2) {
                roots[rootOfNode2] = rootOfNode1;
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