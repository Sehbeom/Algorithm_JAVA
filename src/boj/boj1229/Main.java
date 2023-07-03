package boj.boj1229;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int answer = 0;
    private static boolean answerFound = false;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        if (N == 11 || N == 26) {
            System.out.println(6);
            return;
        } else {
            List<Integer> hs = new ArrayList<>();
            int h = 1;
            int gap = 5;

            while (h <= N) {
                hs.add(h);

                if (h == N) {
                    System.out.println(1);
                    return;
                }

                h += gap;
                gap += 4;
            }

            findAnswer(hs, N);

            System.out.println(answer);
        }
    }

    private static void findAnswer(List<Integer> hs, int N) {
        int size = 0;
        if (N <= 130) {
            size = 4;
        } else if (130 < N && N <= 146858) {
            size = 3;
        } else {
            size = 2;
        }

        for (int i = 1; i <= size; i++) {
            findSubSet(hs, N, i, 0, 0);
            if (answerFound) {
                return;
            }
        }

        answer = size + 1;
    }

    private static void findSubSet(List<Integer> hs, int N, int size, int curSize, int sumOfSelected) {
        if (answerFound) {
            return;
        }

        for (int i = 0; i < hs.size(); i++) {
            if (answerFound) {
                return;
            }

            if (curSize == (size - 1)) {
                if ((sumOfSelected + hs.get(i)) == N) {
                    answer = size;
                    answerFound = true;
                    return;
                }
            } else {
                if ((sumOfSelected + hs.get(i)) < N) {
                    findSubSet(hs, N, size, curSize + 1, sumOfSelected + hs.get(i));
                }
            }
        }
    }
}
