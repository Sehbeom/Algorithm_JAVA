package boj.boj2565;

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

        int N = Integer.parseInt(in.readLine());
        PriorityQueue<PoleSection> pq = new PriorityQueue<>(new Comparator<PoleSection>() {
            @Override
            public int compare(PoleSection o1, PoleSection o2) {
                return o1.from - o2.from;
            }
        });

        int from;
        int to;

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(in.readLine());
            from = Integer.parseInt(st.nextToken());
            to = Integer.parseInt(st.nextToken());
            pq.offer(new PoleSection(from, to));
        }

        int[] tos = new int[N];

        for (int i = 0; i < N; i++) {
            tos[i] = pq.poll().to;
        }

        int[] lis = new int[N];
        for (int i = 0; i < N; i++) {
            lis[i] = 1;
        }

        int maxLength = 0;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (tos[j] < tos[i]) {
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                    maxLength = Math.max(maxLength, lis[i]);
                }
            }
        }

        System.out.println(N - maxLength);

    }
}

class PoleSection {
    int from;
    int to;

    public PoleSection(int from, int to) {
        this.from = from;
        this.to = to;
    }
}
