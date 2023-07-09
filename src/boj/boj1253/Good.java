package boj.boj1253;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Good {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());

        if (N < 3) {
            System.out.println(0);
            return;
        }

        st = new StringTokenizer(in.readLine());

        long[] numbers = new long[N];

        for (int i = 0; i < N; i++) {
            numbers[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(numbers);

        int leftPtr = 0;
        int rightPtr = 0;
        int answer = 0;

        for (int i = 0; i < N; i++) {
            if (i == 0) {
                leftPtr = 1;
                rightPtr = N - 1;
            } else if (i == (N - 1)) {
                leftPtr = 0;
                rightPtr = N - 2;
            } else {
                leftPtr = 0;
                rightPtr = N - 1;
            }

            while (leftPtr < rightPtr) {
                if (leftPtr == i) {
                    leftPtr += 1;
                    continue;
                }

                if (rightPtr == i) {
                    rightPtr -= 1;
                    continue;
                }

                if ((numbers[leftPtr] + numbers[rightPtr]) < numbers[i]) {
                    leftPtr += 1;
                } else if ((numbers[leftPtr] + numbers[rightPtr]) > numbers[i]) {
                    rightPtr -= 1;
                } else {
                    answer += 1;
                    break;
                }
            }
        }
        System.out.println(answer);

    }
}
