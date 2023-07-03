package boj.boj11286;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(in.readLine());

        PriorityQueue<Number> numbers = new PriorityQueue<>();

        int input;
        for (int i = 0; i < N; i++) {
            input = Integer.parseInt(in.readLine());
            if (input == 0) {
                if (numbers.isEmpty()) sb.append("0").append("\n");
                else sb.append(numbers.poll()).append("\n");
            } else {
                numbers.offer(new Number(input));
            }
        }

        System.out.println(sb);
    }
}

class Number implements Comparable<Number> {
    int number = 0;

    Number(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(Number o) {
        if (Math.abs(this.number) > Math.abs(o.number)) return 1;
        else if (Math.abs(this.number) < Math.abs(o.number)) return -1;
        else {
            if (this.number > o.number) return 1;
            else if (this.number < o.number) return -1;
            else return 0;
        }
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }
}
