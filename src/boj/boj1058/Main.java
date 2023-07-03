package boj.boj1058;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {

    private static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());
        boolean[][] friends = new boolean[N][N];

        String tmp;
        for (int i = 0; i < N; i++) {
            tmp = in.readLine();
            for (int j = 0; j < N; j++) {
                friends[i][j] = tmp.charAt(j) == 'Y';
            }
        }

        for (int i = 0; i < N; i++) {
            findAnswer(friends, N, i);
        }

        System.out.println(answer);
    }

    private static void findAnswer(boolean[][] friends, int N, int start) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N];
        visited[start] = true;
        int count = 0;

        queue.offer(new int[] {start, 0});
        int[] current;

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (current[1] == 3) {
                break;
            }

            visited[current[0]] = true;

            for (int i = 0; i < N; i++) {
                if (friends[current[0]][i]) {
                    queue.offer(new int[] {i, current[1] + 1});
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (i == start) {
                continue;
            }

            if (visited[i]) {
                count += 1;
            }
        }

        answer = Math.max(answer, count);
    }

}
