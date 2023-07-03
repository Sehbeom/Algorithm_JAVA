package swea.swea1992;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int H;
        int W;
        char[][] map;

        int N;
        String control;

        char[] controlCase = {'U', 'D', 'L', 'R', 'S'};
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int[] tankPos;
        int tankDirection;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            st = new StringTokenizer(in.readLine());

            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H][W];
            String tmp;
            for (int i = 0; i < H; i++) {
                tmp = in.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = tmp.charAt(j);
                    if (tmp.charAt(j) >= 60) tankPos = new int[] {i, j};
                }
            }

            N = Integer.parseInt(in.readLine());
            control = in.readLine();
            tankDirection = 0;


        }
    }
}
