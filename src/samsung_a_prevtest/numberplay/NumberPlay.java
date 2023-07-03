package samsung_a_prevtest.numberplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class NumberPlay {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(in.readLine());

        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        PriorityQueue<Number> pq = new PriorityQueue<>();

        for (int i = M; i <= N; i++)
            pq.offer(new Number(i));

        Number sorted;
        int index = 0;
        while (!pq.isEmpty()) {
            sorted = pq.poll();
            sb.append(sorted.number).append(" ");
            if (index % 10 == 9) {
                sb.append("\n");
            }
            index++;
        }

        System.out.println(sb);
    }
}

class Number implements Comparable<Number> {
    String engNumber;
    int number;
    String[] numMatch = {"ze", "on", "tw", "th", "fo", "fi", "si", "se", "ei", "ni"};

    Number(int number) {
        this.number = number;
        StringBuilder sb = new StringBuilder();
        if (number > 9) {
            sb.append(numMatch[number / 10]).append(" ").append(numMatch[number % 10]);
        } else {
            sb.append(numMatch[number]);
        }
        engNumber = sb.toString();
    }

    @Override
    public int compareTo(Number n) {
        if (engNumber.length() > n.engNumber.length()) return 1;
        else if (engNumber.length() < n.engNumber.length()) return -1;
        else {
            return engNumber.compareTo(n.engNumber);
        }
    }

}