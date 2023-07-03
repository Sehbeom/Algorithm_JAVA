package boj.boj2563;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2 {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[][] white = new int[100][100];
        int[][] papers = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            papers[i][0] = Integer.parseInt(st.nextToken());
            papers[i][1] = Integer.parseInt(st.nextToken());
        }

        for (int[] onePaper : papers)
            fillPaper(white, onePaper);

        int answer = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (white[i][j] == 1) answer++;
            }
        }

        System.out.println(answer);
    }

    private static void fillPaper(int[][] white, int[] coordinate) {
        for (int i = coordinate[0]; i < coordinate[0] + 10; i++) {
            for (int j = coordinate[1]; j < coordinate[1] + 10; j++) {
                white[i][j] = 1;
            }
        }
    }
}