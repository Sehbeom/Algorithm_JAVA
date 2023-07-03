//package boj.boj1967;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Queue;
//import java.util.StringTokenizer;
//
//public class Main {
//
//    private static int answer = 0;
//
//    public static void main(String[] args) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st;
//
//        int n = Integer.parseInt(in.readLine());
//
//        Node[] nodes = new Node[n + 1];
//
//        for (int i = 1; i <= n; i++) {
//            nodes[i] = new Node(i);
//        }
//
//        int parent;
//        int child;
//        int cost;
//
//        for (int i = 1; i < n; i++) {
//            st = new StringTokenizer(in.readLine());
//            parent = Integer.parseInt(st.nextToken());
//            child = Integer.parseInt(st.nextToken());
//            cost = Integer.parseInt(st.nextToken());
//
//            nodes[parent].linkWith.add(new Edge(nodes[child], cost));
//            nodes[child].linkWith.add(new Edge(nodes[parent], cost));
//        }
//
//        boolean[] visited = new boolean[n + 1];
//        Queue<Node> queue = new ArrayDeque<>();
//        for (int i = 1; i <= n; i++) {
//            checkOneNode(visited, queue, nodes[i]);
//            Arrays.fill(visited, false);
//            for (int j = 1; j <= n; j++) {
//                nodes[j].costSum = 0;
//            }
//        }
//
//        System.out.println(answer);
//
//    }
//
//    private static void checkOneNode(boolean[] visited, Queue<Node> queue, Node start) {
//        queue.offer(start);
//        visited[start.number] = true;
//        int nextCost = 0;
//        Node current;
//
//        while (!queue.isEmpty()) {
//            current = queue.poll();
//
//            for (Edge linked : current.linkWith) {
//                if (!visited[linked.to.number]) {
//                    visited[linked.to.number] = true;
//                    nextCost = current.costSum + linked.cost;
//                    answer = Math.max(answer, nextCost);
//                    linked.to.costSum = nextCost;
//                    queue.offer(linked.to);
//                }
//            }
//        }
//    }
//}
////
////class Node {
////    int number;
////    List<Edge> linkWith = new ArrayList<>();
////    int costSum;
////
////    public Node(int number) {
////        this.number = number;
////    }
////}
////
////class Edge {
////    Node to;
////    int cost;
////
////    public Edge(Node to, int cost) {
////        this.to = to;
////        this.cost = cost;
////    }
////}
