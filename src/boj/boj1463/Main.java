package boj.boj1463;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int X = Integer.parseInt(in.readLine());
        int[] answer = new int[X + 1];
        if (X > 3) {
            answer[1] = 0;
            answer[2] = 1;
            answer[3] = 1;

            for (int i = 4; i <= X; i++) {
                if (i % 2 == 0 && i % 3 != 0) {
                    answer[i] = Math.min(answer[i - 1], answer[i / 2]) + 1;
                } else if (i % 2 != 0 && i % 3 == 0) {
                    answer[i] = Math.min(answer[i - 1], answer[i / 3]) + 1;
                } else if (i % 2 == 0 && i % 3 == 0) {
                    answer[i] = Math.min(answer[i - 1], Math.min(answer[i / 2], answer[i / 3])) + 1;
                } else {
                    answer[i] = answer[i - 1] + 1;
                }
            }

            System.out.println(answer[X]);
        } else {
            System.out.println(X == 1 ? 0 : 1);
        }
    }
}

