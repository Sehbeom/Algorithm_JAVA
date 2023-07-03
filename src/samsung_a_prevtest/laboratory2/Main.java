package samsung_a_prevtest.laboratory2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static boolean canSpread = false;
    private static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N];
        List<int[]> canBeVirus = new ArrayList<>();
        int numOfEmpty = 0;

        int tmp;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                tmp = Integer.parseInt(st.nextToken());
                if (tmp == 2) {
                    canBeVirus.add(new int[]{i, j});
                    numOfEmpty += 1;
                    continue;
                }

                map[i][j] = tmp;
                if (tmp == 0) numOfEmpty += 1;
            }
        }
        numOfEmpty -= M;

        spreadVirus(map, numOfEmpty, M, canBeVirus, 0 ,0, new int[M]);
        if (canSpread) {
            System.out.println(answer);
        } else {
            System.out.println(-1);
        }

    }

    private static void spreadVirus(int[][] map, int numOfEmpty, int M, List<int[]> canBeVirus, int cnt, int index, int[] selected) {
        if (cnt == M) {
            int time = spreadVirusOneCase(map, numOfEmpty, canBeVirus, selected);
            if (time != -1) {
                canSpread = true;
                answer = Math.min(answer, time);
            }
            return;
        }

        for (int i = index; i < canBeVirus.size(); i++) {
            selected[cnt] = i;
            spreadVirus(map, numOfEmpty, M, canBeVirus, cnt + 1, i + 1, selected);
        }

    }

    private static int spreadVirusOneCase(int[][] map, int numOfEmpty, List<int[]> canBeVirus, int[] selected) {
        int time = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[map.length][map.length];

        int[] tmp;
        for (int i = 0; i < selected.length; i++) {
            tmp = canBeVirus.get(selected[i]);
            queue.offer(new int[] {tmp[0], tmp[1], 0});
            visited[tmp[0]][tmp[1]] = true;
        }

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        int[] current;
        int nx;
        int ny;
        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < 4; i++) {
                nx = current[0] + dx[i];
                ny = current[1] + dy[i];

                if (-1 < nx && nx < map.length &&
                -1 < ny && ny < map.length &&
                !visited[nx][ny] &&
                map[nx][ny] != 1) {
                    visited[nx][ny] = true;
                    queue.offer(new int[] {nx, ny, current[2] + 1});
                    time = current[2] + 1;
                    numOfEmpty -= 1;
                }
            }
        }

        if (numOfEmpty > 0) time = -1;

        return time;
    }
}
