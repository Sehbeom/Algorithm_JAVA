package swea.swea3307;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());

        int N;
        int[] seq;
        List<Integer> lis;
        int answer = 1;
        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            N = Integer.parseInt(in.readLine());

            seq = new int[N];
            st = new StringTokenizer(in.readLine());
            for (int i = 0; i < N; i++)
                seq[i] = Integer.parseInt(st.nextToken());

            lis = new ArrayList<>();
            lis.add(seq[0]);

            if (N == 1) {
                answer = 1;
            } else {
                for (int i = 1; i < seq.length; i++) {
                    if (lis.get(lis.size() - 1) < seq[i]) {
                        lis.add(seq[i]);
                    } else {
                        int pos = (Collections.binarySearch(lis, seq[i]) + 1) * -1;
                        lis.set(pos, seq[i]);
                    }
                }
                answer = lis.size();
            }

            sb.append(answer).append("\n");
        }

        System.out.println(sb);
    }
}
