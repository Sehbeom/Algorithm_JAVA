package boj.boj17298;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[] A = new int[N + 1];
        int[] nge = new int[N + 1];
        int[] ngeIndex = new int[N + 1];

        st = new StringTokenizer(in.readLine());
        int maxValue = 0;
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
            maxValue = Math.max(maxValue, A[i]);
        }

        List<Integer> maxValueIndex = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (A[i] == maxValue) {
                maxValueIndex.add(i);
            }
        }

        nge[N] = -1;
        for (int i = N; i > 1; i--) {
            if (A[i] > A[i - 1]) {
                nge[i - 1] = A[i];
                ngeIndex[i - 1] = i;
            } else if (A[i] == A[i - 1]) {
                nge[i - 1] = nge[i];
                ngeIndex[i - 1] = ngeIndex[i];
            } else {
                findNGE(A, nge, ngeIndex, i - 1, i);
            }
        }

        for (int i : maxValueIndex) {
            nge[i] = -1;
        }

        for (int i = 1; i <= N; i++) {
            sb.append(nge[i]).append(" ");
        }

        System.out.println(sb);
    }

    private static void findNGE(int[] A, int[] nge, int[] ngeIndex, int compare, int current) {
        if (A[compare] < nge[current]) {
            nge[compare] = nge[current];
            ngeIndex[compare] = ngeIndex[current];
        } else {
            if (nge[current] == -1) {
                nge[compare] = -1;
            } else {
                findNGE(A, nge, ngeIndex, compare, ngeIndex[current]);
            }
        }
    }
}
