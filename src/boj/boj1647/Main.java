package boj.boj1647;

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
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] roots = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            roots[i] = i;
        }

        PriorityQueue<Path> pq = new PriorityQueue<>(new Comparator<Path>() {
            @Override
            public int compare(Path o1, Path o2) {
                return o1.cost - o2.cost;
            }
        });

        int A;
        int B;
        int C;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            if (A == B) continue;
            pq.offer(new Path(A, B, C));
        }

        int answer = 0;
        int count = 0;

        Path current;
        int town1Root;
        int town2Root;
        while (count < (N - 2)) {
            current = pq.poll();

            town1Root = findRoot(roots, current.town1);
            town2Root = findRoot(roots, current.town2);

            if (town1Root != town2Root) {
                roots[town2Root] = town1Root;
                answer += current.cost;
                count += 1;
            }
        }

        System.out.println(answer);

    }

    private static int findRoot(int[] roots, int town) {
        if (roots[town] == town) {
            return town;
        }

        return roots[town] = findRoot(roots, roots[town]);
    }
}

class Path {
    int town1;
    int town2;
    int cost;

    public Path(int town1, int town2, int cost) {
        this.town1 = town1;
        this.town2 = town2;
        this.cost = cost;
    }
}
