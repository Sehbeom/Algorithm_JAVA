package boj.boj1094;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int X = Integer.parseInt(in.readLine());

        int curLength = 64;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(64);

        int shortest = 0;
        int divided = 0;
        while (curLength != X) {
            shortest = pq.poll();
            divided = shortest / 2;

            if ((curLength - divided) >= X) {
                curLength -= divided;
                pq.offer(divided);
            } else {
                pq.offer(divided);
                pq.offer(divided);
            }
        }

        System.out.println(pq.size());
    }
}
