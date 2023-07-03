package boj.boj1239;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[] numOfDogs = new int[N];

        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            numOfDogs[i] = Integer.parseInt(st.nextToken());
        }

        makePermutation(N, numOfDogs, new boolean[N], new int[N], 0);

        System.out.println(answer);
    }

    private static void makePermutation(int N, int[] numOfDogs, boolean[] isSelected, int[] permutation, int curIndex) {
        if (curIndex == N) {
            findAnswer(N, numOfDogs, permutation);

            return;
        }

        for (int i = 0; i < N; i++) {
            if (!isSelected[i]) {
                isSelected[i] = true;
                permutation[curIndex] = i;
                makePermutation(N, numOfDogs, isSelected, permutation, curIndex + 1);
                isSelected[i] = false;
            }
        }
    }

    private static void findAnswer(int N, int[] numOfDogs, int[] permutation) {
        int count = 0;
        int sumOfDogs = 0;
        for (int i = 0; i < N; i++) {
            sumOfDogs = numOfDogs[permutation[i]];
            if (sumOfDogs == 50) {
                count += 1;
                continue;
            }
            for (int j = 1; j < N; j++) {
                sumOfDogs += numOfDogs[permutation[(i + j) % N]];

                if (sumOfDogs == 50) {
                    count += 1;
                    break;
                } else if (sumOfDogs > 50) {
                    break;
                }

            }
        }

        answer = Math.max(answer, count / 2);
    }
}
