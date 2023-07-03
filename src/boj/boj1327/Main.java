package boj.boj1327;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    private static int answer = -1;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());
        StringBuilder str = new StringBuilder();
        StringBuilder dst = new StringBuilder();
        PriorityQueue<String> dstPQ = new PriorityQueue<>();

        String tmp;
        for (int i = 0; i < N; i++) {
            tmp = st.nextToken();
            str.append(tmp);
            dstPQ.offer(tmp);
        }

        while (!dstPQ.isEmpty()) {
            dst.append(dstPQ.poll());
        }

        if (str.toString().equals(dst.toString())) {
            System.out.println(0);
            return;
        }

        findAnswer(str.toString(), dst.toString(), N, K);

        System.out.println(answer);
    }

    private static void findAnswer(String str, String dst, int N, int K) {
        Queue<Sequence> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new Sequence(str, 0));
        Sequence current;
        StringBuilder reversed = new StringBuilder();

        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i <= (N - K); i++) {
                if (i == 0) {
                    addReversed(current.sequence.substring(0, K), reversed, K);
                    reversed.append(current.sequence, K, current.sequence.length());
                } else {
                    reversed.append(current.sequence, 0, i);
                    addReversed(current.sequence.substring(i, i + K), reversed, K);
                    reversed.append(current.sequence, i + K, current.sequence.length());
                }

                if (dst.equals(reversed.toString())) {
                    answer = current.count + 1;
                    return;
                }

                if (visited.add(reversed.toString())) {
                    queue.offer(new Sequence(reversed.toString(), current.count + 1));
                }
                reversed.setLength(0);
            }
        }
    }

    private static void addReversed(String subStr, StringBuilder reversed, int K) {
        for (int i = K - 1; i >= 0; i--) {
            reversed.append(subStr.charAt(i));
        }
    }
}

class Sequence {
    String sequence;
    int count;

    public Sequence(String sequence, int count) {
        this.sequence = sequence;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sequence sequence1 = (Sequence) o;
        return Objects.equals(sequence, sequence1.sequence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence);
    }
}