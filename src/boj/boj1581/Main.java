package boj.boj1581;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // FF, FS, SF, SS
        int[] numOfSongs = new int[4];
        boolean[] isZero = new boolean[4];

        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < 4; i++) {
            numOfSongs[i] = Integer.parseInt(st.nextToken());
            if (numOfSongs[i] == 0)
                isZero[i] = true;
        }

        if (!isZero[0]) {
            startCountFF(numOfSongs, isZero);
        } else if (!isZero[1]) {
            startCountFS(numOfSongs, isZero);
        } else if (!isZero[3]) {
            startCountSS(numOfSongs, isZero);
        } else if (!isZero[2]) {
            countSF(numOfSongs, isZero);
        }

        System.out.println(answer);

    }

    private static void startCountFF(int[] numOfSongs, boolean[] isZero) {
        countFF(numOfSongs, isZero);

        if (!isZero[1]) {
            startCountFS(numOfSongs, isZero);
        }
    }

    private static void startCountFS(int[] numOfSongs, boolean[] isZero) {
        countFS(numOfSongs, isZero);

        if (!isZero[3]) {
            startCountSS(numOfSongs, isZero);
        } else if (!isZero[2]) {
            countSF(numOfSongs, isZero);
        }
    }

    private static void startCountSS(int[] numOfSongs, boolean[] isZero) {
        countSS(numOfSongs, isZero);

        if (!isZero[2]) {
            countSF(numOfSongs, isZero);
        }
    }

    private static void countFF(int[] numOfSongs, boolean[] isZero) {
        answer += numOfSongs[0];
        numOfSongs[0] = 0;
        isZero[0] = true;
    }

    private static void countFS(int[] numOfSongs, boolean[] isZero) {
        int FS = numOfSongs[1];
        int SF = numOfSongs[2];

        if (FS <= SF) {
            answer += (FS + (FS - 1));
            numOfSongs[1] = 0;
            numOfSongs[2] -= (FS - 1);
        } else {
            answer += ((SF + 1) + SF);
            numOfSongs[1] -= (SF + 1);
            numOfSongs[2] = 0;
        }

        for (int i = 1; i < 3; i++) {
            if (numOfSongs[i] == 0)
                isZero[i] = true;
        }
    }

    private static void countSF(int[] numOfSongs, boolean[] isZero) {
        if (!isZero[2]) {
            answer += 1;
            numOfSongs[2] -= 1;
            if (numOfSongs[2] == 0)
                isZero[2] = true;
        }
    }

    private static void countSS(int[] numOfSongs, boolean[] isZero) {
        answer += numOfSongs[3];
        numOfSongs[3] = 0;
        isZero[3] = true;
    }
}
