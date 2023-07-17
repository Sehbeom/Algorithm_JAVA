package boj.boj1082;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static int curCost = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());
        Room[] P = new Room[N];

        StringTokenizer st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            P[i] = new Room(i, Integer.parseInt(st.nextToken()));
        }
        int M = Integer.parseInt(in.readLine());

        if (N == 1) {
            System.out.println(0);
            return;
        }
        Arrays.sort(P, new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return o1.cost - o2.cost;
            }
        });

        if ((P[0].number == 0) && (P[1].cost > M)) {
            System.out.println(0);
            return;
        }

        List<Integer> answer = new ArrayList<>();

        setFirstAnswer(P, answer, M);

        Arrays.sort(P, new Comparator<Room>() {
            @Override
            public int compare(Room o1, Room o2) {
                return o1.number - o2.number;
            }
        });

        for (int i = 0; i < answer.size(); i++) {
            for (int j = N - 1; j >= 0; j--) {
                if ((curCost - P[answer.get(i)].cost + P[j].cost) <= M) {
                    curCost = curCost - P[answer.get(i)].cost + P[j].cost;
                    answer.set(i, P[j].number);
                    break;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answer.size(); i++) {
            sb.append(answer.get(i));
        }
        System.out.println(sb);

    }

    private static void setFirstAnswer(Room[] P, List<Integer> answer, int M) {
        if (P[0].number == 0) {
            answer.add(P[1].number);
            M -= P[1].cost;
            curCost += P[1].cost;

            while ((M - P[0].cost) >= 0) {
                answer.add(0);
                M -= P[0].cost;
                curCost += P[0].cost;
            }
        } else {
            while ((M - P[0].cost) >= 0) {
                answer.add(P[0].number);
                M -= P[0].cost;
                curCost += P[0].cost;
            }
        }
    }

}

class Room {
    int number;
    int cost;

    public Room(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }
}
