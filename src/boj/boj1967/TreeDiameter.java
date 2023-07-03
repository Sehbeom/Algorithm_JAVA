package boj.boj1967;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class TreeDiameter {
    private static int n = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(in.readLine());

        if (n == 1){
            System.out.println(0);
            return;
        }

        Node[] nodes = new Node[n + 1];

        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }

        int parent;
        int child;
        int cost;

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            parent = Integer.parseInt(st.nextToken());
            child = Integer.parseInt(st.nextToken());
            cost = Integer.parseInt(st.nextToken());

            nodes[parent].linkWith.add(new Edge(nodes[child], cost));
            nodes[child].linkWith.add(new Edge(nodes[parent], cost));
        }

        int furthestNode = checkOneNode(nodes, 1)[0];
        System.out.println(checkOneNode(nodes, furthestNode)[1]);
    }

    private static int[] checkOneNode(Node[] nodes, int start) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];

        queue.offer(new int[] {start, 0});
        visited[start] = true;

        int maxCost = 0;
        int furthest = 0;

        int[] current;
        int[] next;

        while (!queue.isEmpty()) {
            current = queue.poll();

            for (Edge linked : nodes[current[0]].linkWith) {
                if (!visited[linked.to.number]) {
                    visited[linked.to.number] = true;
                    next = new int[] {linked.to.number, current[1] + linked.cost};

                    if (maxCost < next[1]) {
                        maxCost = next[1];
                        furthest = next[0];
                    }

                    queue.offer(next);
                }
            }
        }

        return new int[] {furthest, maxCost};
    }
}

class Node {
    int number;
    List<Edge> linkWith = new ArrayList<>();

    public Node(int number) {
        this.number = number;
    }
}

class Edge {
    Node to;
    int cost;

    public Edge(Node to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}
