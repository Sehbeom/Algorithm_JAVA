package boj.boj2239;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static int[][] answer = new int[9][9];
    private static boolean answerFound = false;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int[][] board = new int[9][9];

        String tmp;
        for (int i = 0; i < 9; i++) {
            tmp = in.readLine();
            for (int j = 0; j < 9; j++) {
                board[i][j] = tmp.charAt(j) - '0';
            }
        }

        int[] start = findNextPos(board, 0, 0);

        if (start != null)
            findAnswer(board, start[0], start[1]);
        else
            fillAnswer(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(answer[i][j]);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    private static void findAnswer(int[][] board, int curX, int curY) {
        if (!answerFound) {
            boolean[] canNotInsert = new boolean[10];
            canNotInsert[0] = true;

            checkSquare(board, curX, curY, canNotInsert);
            checkRow(board, curX, canNotInsert);
            checkCol(board, curY, canNotInsert);

            int[] next;
            for (int i = 1; i < 10; i++) {
                if (answerFound) break;
                if (!canNotInsert[i]) {
                    board[curX][curY] = i;
                    next = findNextPos(board, curX, curY);
                    if (next != null) {
                        findAnswer(board, next[0], next[1]);
                    } else {
                        fillAnswer(board);
                        break;
                    }
                    board[curX][curY] = 0;
                }
            }
        }
    }

    private static void fillAnswer(int[][] board) {
        for (int i = 0; i < 9; i++) {
            answer[i] = board[i].clone();
        }
        answerFound = true;
    }

    private static int[] findNextPos(int[][] board, int curX, int curY) {
        int[] next = null;
        for (int i = curX; i < 9; i++) {
            for (int j = curY; j < 9; j++) {
                if (board[i][j] == 0) {
                    next = new int[] {i, j};
                    return next;
                }
            }
            curY = 0;
        }

        return next;
    }

    private static void checkRow(int[][] board, int curX, boolean[] canNotInsert) {
        for (int i = 0; i < 9; i++) {
            if (board[curX][i] > 0) {
                canNotInsert[board[curX][i]] = true;
            }
        }
    }

    private static void checkCol(int[][] board, int curY, boolean[] canNotInsert) {
        for (int i = 0; i < 9; i++) {
            if (board[i][curY] > 0) {
                canNotInsert[board[i][curY]] = true;
            }
        }
    }

    private static void checkSquare(int[][] board, int curX, int curY, boolean[] canNotInsert) {
        int startX = (curX / 3) * 3;
        int startY = (curY / 3) * 3;

        for (int i = startX; i < startX + 3; i++) {
            for (int j = startY; j < startY + 3; j++) {
                if (board[i][j] > 0) {
                    canNotInsert[board[i][j]] = true;
                }
            }
        }
    }

}
