package boj.boj1715;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        PriorityQueue<Long> pq = new PriorityQueue<>();

        for (int i = 0; i < N; i++) {
            pq.offer(Long.parseLong(in.readLine()));
        }

        if (N == 1) {
            System.out.println(0);
            return;
        }

        long minCompares = 0;
        long minNumber1;
        long minNumber2;

        while (pq.size() > 1) {
            minNumber1 = pq.poll();
            minNumber2 = pq.poll();

            minCompares += (minNumber1 + minNumber2);
            pq.offer(minNumber1 + minNumber2);
        }

        System.out.println(minCompares);
    }
}
