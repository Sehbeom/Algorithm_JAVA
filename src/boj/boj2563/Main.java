package boj.boj2563;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[][] papers = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            papers[i][0] = Integer.parseInt(st.nextToken());
            papers[i][1] = Integer.parseInt(st.nextToken());
        }

        int answer = N * 100;
        int xDiff = 0;
        int yDiff = 0;
        for (int standard = 0; standard < N - 1; standard++) {
            for (int compare = standard + 1; compare < N; compare++) {
                xDiff = Math.abs(papers[standard][0] - papers[compare][0]);
                yDiff = Math.abs(papers[standard][1] - papers[compare][1]);
                if (xDiff < 10 && yDiff < 10) {
                    xDiff = 10 - xDiff;
                    yDiff = 10 - yDiff;
                    answer -= xDiff * yDiff;
                }
            }
        }

        System.out.println(answer);
    }

    private void fillPaper(int[][] white, int[] coordinate) {
        for (int i = coordinate[0]; i < coordinate[0] + 10; i++) {
            for (int j = coordinate[1]; j < coordinate[1] + 10; j++) {
                white[i][j] = 1;
            }
        }
    }
}
