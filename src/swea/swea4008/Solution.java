package swea.swea4008;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {

    static int[] numbers;
    static Operator[] operators;
    static int min;
    static int max;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int N;
        // + - * /
        operators = new Operator[4];
        for (int i = 0; i < 4; i++) {
            operators[i] = new Operator(i);
        }
        int[] operInfo = new int[4];
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;

            N = Integer.parseInt(in.readLine());
            numbers = new int[N];

            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < 4; i++) {
                operInfo[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < N; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            makeCases(0, operInfo, new int[N - 1], N);

            sb.append(max - min).append("\n");

        }

        out.write(sb.toString());
        out.flush();
        out.close();
    }

    private static void makeCases(int cnt, int[] operInfo, int[] operCase, int N) {
        if (cnt == (N - 1)) {
            int answer = makeResult(operCase);
            if (min > answer) {
                min = answer;
            }
            if (max < answer) {
                max = answer;
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operInfo[i] > 0) {
                operCase[cnt] = i;
                operInfo[i] -= 1;
                makeCases(cnt + 1, operInfo, operCase, N);
                operInfo[i] += 1;
            }
        }
    }

    private static int makeResult(int[] operCase) {
        int result = operators[operCase[0]].operation(numbers[0], numbers[1]);
        for (int i = 1; i < operCase.length; i++) {
            result = operators[operCase[i]].operation(result, numbers[i + 1]);
        }

        return result;
    }
}

class Operator {

    int index;

    Operator(int index) {
        this.index = index;
    }

    int operation(int a, int b) {
        switch (index) {
            case 0:
                return a + b;
            case 1:
                return a - b;
            case 2:
                return a * b;
            default:
                return a / b;
        }
    }
}