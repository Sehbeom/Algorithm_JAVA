package boj.boj1992;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        String tmp;
        int[][] image = new int[N][N];
        for (int i = 0; i < N; i++) {
            tmp = in.readLine();
            for (int j = 0; j < N; j++) {
                image[i][j] = tmp.charAt(j) - '0';
            }
        }

        zipImage(image, 0, 0, N);
        System.out.println(sb);
    }

    private static void zipImage(int[][] image, int startX, int startY, int size) {
        int num = image[startX][startY];
        boolean callRecur = false;
        if (size == 2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("(");
            for (int i = startX; i < startX + 2; i++) {
                for (int j = startY; j < startY + 2; j++) {
                    sb2.append(image[i][j]);
                    if (num != image[i][j]) callRecur = true;
                }
            }
            sb2.append(")");

            sb.append(callRecur ? sb2 : num);

            return;
        }

        bp : for (int i = startX; i < startX + size; i++) {
            for (int j = startY; j < startY + size; j++) {
                if (image[i][j] != num) {
                    callRecur = true;
                    sb.append("(");
                    zipImage(image, startX, startY, size / 2);
                    zipImage(image, startX, startY + size / 2, size / 2);
                    zipImage(image, startX + size / 2, startY, size / 2);
                    zipImage(image, startX + size / 2, startY + size / 2, size / 2);
                    sb.append(")");
                    break bp;
                }
            }
        }

        if (!callRecur)
            sb.append(num);
    }
}
