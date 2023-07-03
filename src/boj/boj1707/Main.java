package boj.boj1707;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static boolean canDivide = true;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int K = Integer.parseInt(in.readLine());
        int V = 0;
        int E = 0;

        Node[] graph;
        boolean[] visited;
        for (int k = 1; k <= K; k++) {
            st = new StringTokenizer(in.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            graph = new Node[V + 1];
            visited = new boolean[V + 1];
            canDivide = true;

            for (int i = 1; i <= V; i++) {
                graph[i] = new Node(i);
            }

            int node1 = 0;
            int node2 = 0;
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(in.readLine());
                node1 = Integer.parseInt(st.nextToken());
                node2 = Integer.parseInt(st.nextToken());

                graph[node1].linkWith.add(node2);
                graph[node2].linkWith.add(node1);
            }

            for (int i = 1; i <= V; i++) {
                if (!visited[i]) {
                    bfs(graph, visited, i);
                }

                if (!canDivide) {
                    break;
                }
            }

            sb.append(canDivide ? "YES" : "NO").append("\n");

        }
        System.out.println(sb);
    }

    private static void bfs(Node[] graph, boolean[] visited, int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start] = true;
        graph[start].team = 0;

        int current;
        int nextTeam;
        exit : while (!queue.isEmpty()) {
            current = queue.poll();
            visited[current] = true;
            nextTeam = (graph[current].team + 1) % 2;

            for (int next : graph[current].linkWith) {
                if (!visited[next]) {
                    if (graph[next].team == graph[current].team) {
                        canDivide = false;
                        break exit;
                    }

                    graph[next].team = nextTeam;
                    queue.offer(next);
                }
            }
        }
    }
}

class Node {
    int number;
    int team;
    List<Integer> linkWith;

    public Node(int number) {
        this.number = number;
        this.team = -1;
        this.linkWith = new ArrayList<>();
    }
}
