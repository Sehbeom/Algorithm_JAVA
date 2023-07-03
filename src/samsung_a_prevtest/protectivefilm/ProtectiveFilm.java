package samsung_a_prevtest.protectivefilm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ProtectiveFilm {

    private static boolean passed;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int D, W, K;
        int[][] film;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            passed = false;

            st = new StringTokenizer(in.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            film = new int[D][W];

            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(in.readLine());
                for (int j = 0; j < W; j++) {
                    film[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            if (K == 1 || canPass(film, K)) {
                sb.append("0").append("\n");
            } else {
                breakPoint : for (int i = 1; i <= D; i++) {
                    for (int j = 0; j <= i; j++) {
                        setAChemicalPosition(film, D, K, new int[] {j, i - j}, 0, 0, new boolean[D]);
                        if (passed) {
                            sb.append(i).append("\n");
                            break breakPoint;
                        }
                    }
                }
            }
        }
        System.out.println(sb);
    }

    private static boolean canPass(int[][] film, int K) {
        int current = 0;
        int numOfCurrent = 0;
        boolean isPassed = false;

        for (int c = 0; c < film[0].length; c++) {
            current = film[0][c];
            numOfCurrent = 1;
            isPassed = false;
            for (int r = 1; r < film.length; r++) {
                if (current == film[r][c]) {
                    if (++numOfCurrent == K) {
                        isPassed = true;
                        break;
                    }
                }
                else {
                    current = film[r][c];
                    numOfCurrent = 1;
                }

            }
            if (!isPassed) return false;
        }

        return true;
    }

    private static void setAChemicalPosition(int[][] film, int D, int K, int[] numOfAB, int cnt, int index, boolean[] ASelected) {
        if (cnt == numOfAB[0]) {
            setBChemicalPosition(film, D, K, numOfAB, 0, 0, ASelected);
            return;
        }

        if (!passed) {
            int[][] clonedFilm = makeClone(film);

            for (int i = index; i < D; i++) {
                useAChemical(clonedFilm, i);
                ASelected[i] = true;
                setAChemicalPosition(clonedFilm, D, K, numOfAB, cnt + 1, i + 1, ASelected);
                if (passed) return;
                ASelected[i] = false;
                rollBackChemical(film, clonedFilm, i);
            }
        }
    }

    private static void setBChemicalPosition(int[][] film, int D, int K, int[] numOfAB, int cnt, int index, boolean[] ASelected) {
        if (cnt == numOfAB[1]) {
            passed = canPass(film, K);
            return;
        }
        if (!passed) {
            int[][] clonedFilm = makeClone(film);

            for (int i = index; i < D; i++) {
                if (!ASelected[i]) {
                    useBChemical(clonedFilm, i);
                    setBChemicalPosition(clonedFilm, D, K, numOfAB, cnt + 1, i + 1, ASelected);
                    if (passed) return;
                    rollBackChemical(film, clonedFilm, i);
                }
            }
        }
    }

    private static int[][] makeClone(int[][] film) {
        int[][] cloned = new int[film.length][film[0].length];

        for (int i = 0; i < film.length; i++) {
            cloned[i] = film[i].clone();
        }

        return cloned;
    }

    private static void useAChemical(int[][] film, int pos) {
        for (int i = 0; i < film[0].length; i++)
            film[pos][i] = 0;
    }

    private static void useBChemical(int[][] film, int pos) {
        for (int i = 0; i < film[0].length; i++) {
            film[pos][i] = 1;
        }
    }

    private static void rollBackChemical(int[][] film, int[][] cloned, int pos) {
        for (int i = 0; i < film[0].length; i++)
            cloned[pos][i] = film[pos][i];
    }
}
