package samsung_a_prevtest.blockbreak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    private static int answer;
    private static int[] dx = {1, 0, 0, -1};
    private static int[] dy = {0, 1, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int N, W, H;
        int[][] blocks;
        int[] tops;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            answer = Integer.MAX_VALUE;

            st = new StringTokenizer(in.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            blocks = new int[H][W];
            tops = new int[W];
            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < W; j++) {
                    blocks[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            makeTops(blocks, tops);

//            findBestPlay(blocks, tops, N, 0, new int[N]);
            findBestPlay(blocks, tops, N, 0, new int[N]);
            sb.append(answer).append("\n");
        }

        System.out.println(sb);
    }

    private static void makeTops(int[][] blocks, int[] tops) {
        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j][i] > 0) {
                    tops[i] = j;
                    break;
                }
            }
        }
    }

    private static void findBestPlay(int[][] blocks, int[] tops, int N, int cnt, int[] selected) {
        if (cnt == N) {
            int[][] clonedBlocks = new int[blocks.length][blocks[0].length];
            for (int i = 0; i < blocks.length; i++)
                clonedBlocks[i] = blocks[i].clone();

            int[] clonedTops = tops.clone();

            for (int i = 0; i < N; i++) {
                clonedBlocks = shootOneMarble(clonedBlocks, clonedTops, clonedTops[selected[i]], selected[i]);
            }

            answer = Math.min(answer, countBlocks(clonedBlocks, clonedTops));
            return;
        }

        for (int i = 0; i < blocks[0].length; i++) {
            selected[cnt] = i;
            findBestPlay(blocks, tops, N, cnt + 1, selected);
        }
    }

    private static int[][] shootOneMarble(int[][] blocks, int[] tops, int x, int y) {
        breakBlocks(blocks, x, y);
        return fallBlocks(blocks, tops);
    }

    private static void breakBlocks(int[][] blocks, int x, int y) {
        int spread = blocks[x][y];
        blocks[x][y] = 0;

        int nX;
        int nY;
        for (int i = 0; i < 4; i++) {
            nX = x;
            nY = y;
            for (int j = 1; j < spread; j++) {
                nX += dx[i];
                nY += dy[i];
                if (-1 < nX && nX < blocks.length &&
                -1 < nY && nY < blocks[0].length) {
                    if (blocks[nX][nY] == 0) continue;
                    else if (blocks[nX][nY] == 1) blocks[nX][nY] = 0;
                    else breakBlocks(blocks, nX, nY);
                } else {
                    break;
                }
            }
        }
    }

    private static int[][] fallBlocks(int[][] blocks, int[] tops) {
        int[][] fallen = new int[blocks.length][blocks[0].length];

        int index;
        for (int i = 0; i < blocks[0].length; i++) {
            index = blocks.length - 1;
            for (int j = blocks.length - 1; j > -1; j--) {
                if (blocks[j][i] > 0) {
                    fallen[index][i] = blocks[j][i];
                    index--;
                }
            }
            tops[i] = index == blocks.length - 1 ? index : index + 1;
        }

        return fallen;
    }

    private static int countBlocks(int[][] blocks, int[] tops) {
        int count = 0;
        for (int i = 0; i < tops.length; i++) {
            for (int j = tops[i]; j < blocks.length; j++) {
                if (blocks[j][i] > 0)
                    count += 1;
            }
        }

        return count;
    }
}
