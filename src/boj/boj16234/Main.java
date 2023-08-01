package boj.boj16234;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int answer = 0;
    private static int[] dx = new int[]{-1, 1, 0, 0};
    private static int[] dy = new int[]{0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int R = Integer.parseInt(st.nextToken());

        int[][] A = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        SearchResult searchResult = new SearchResult();

        boolean peopleMoved = true;

        while (peopleMoved && (searchResult.count < N * N)) {
            boolean[][] visited = new boolean[N][N];
            peopleMoved = false;

            breakPoint:
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!visited[i][j]) {
                        searchResult = searchCountries(A, visited, N, L, R, new int[]{i, j});

                        if (searchResult.count > 1) {
                            peopleMoved = true;
                        }

                        if (searchResult.count == (N * N)) {
                            break breakPoint;
                        }

                        for (int[] searched : searchResult.searchedPos) {
                            A[searched[0]][searched[1]] =
                                searchResult.totalSum / searchResult.count;
                        }
                    }
                }
            }

            if (peopleMoved) {
                answer += 1;
            }
        }

        System.out.println(answer);
    }

    private static SearchResult searchCountries(int[][] A, boolean[][] visited, int N, int L, int R,
        int[] start) {
        Queue<int[]> queue = new ArrayDeque<>();
        SearchResult searchResult = new SearchResult();

        queue.offer(start);
        visited[start[0]][start[1]] = true;
        searchResult.searchedPos.add(start);
        searchResult.count = 1;
        searchResult.totalSum = A[start[0]][start[1]];

        int[] current;
        int nextX;
        int nextY;
        int diff;

        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int i = 0; i < 4; i++) {
                nextX = current[0] + dx[i];
                nextY = current[1] + dy[i];

                if (canGo(N, nextX, nextY)) {
                    diff = Math.abs(A[current[0]][current[1]] - A[nextX][nextY]);
                    if (!visited[nextX][nextY] && (L <= diff && diff <= R)) {
                        visited[nextX][nextY] = true;

                        int[] next = new int[]{nextX, nextY};
                        queue.offer(next);

                        searchResult.searchedPos.add(next);
                        searchResult.count += 1;
                        searchResult.totalSum += A[nextX][nextY];
                    }
                }
            }
        }

        return searchResult;
    }

    private static boolean canGo(int N, int x, int y) {
        if ((0 <= x && x < N) &&
            (0 <= y && y < N)) {
            return true;
        }
        return false;
    }
}

class SearchResult {

    int count = 0;
    int totalSum = 0;
    List<int[]> searchedPos = new ArrayList<>();
}