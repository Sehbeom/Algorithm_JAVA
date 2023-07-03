package boj.boj1091;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        int[] P = new int[N];
        int[] S = new int[N];

        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            P[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            S[i] = Integer.parseInt(st.nextToken());
        }

        Card[] cards = new Card[N];
        for (int i = 0; i < N; i++) {
            cards[i] = new Card(i, N);
        }

        Set<Integer> minCycles = new HashSet<>();
        for (int i = 0; i < N; i++) {
            cards[i].setMinMoveCount(S);
            cards[i].setDst(P[i], N);
            minCycles.add(cards[i].minCycle);
        }

        int minCycleLcm = 1;

        for(int minCycle : minCycles) {
            minCycleLcm = lcm(minCycleLcm, minCycle);
        }

        Map<Integer, Integer> shuffleCounts = new HashMap<>();
        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            answer = Math.min(answer, cards[i].addShuffleCount(shuffleCounts, minCycleLcm, N));
        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    public static int lcm(int x, int y) {
        return (x * y) / gcd(x, y);
    }

    private static int gcd(int a, int b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}

class Card {
    int number;
    int[] minMoveCount;
    int minCycle;
    List<Integer> dst = new ArrayList<>();
    List<Integer> dstMoveCount = new ArrayList<>();

    public Card(int number, int N) {
        this.number = number;
        this.minMoveCount = new int[N];
        Arrays.fill(this.minMoveCount, -1);
    }

    public void setMinMoveCount(int[] S) {
        this.minMoveCount[this.number] = 0;

        int count = 1;
        int curNumber = S[this.number];

        while (curNumber != this.number) {
            this.minMoveCount[curNumber] = count;
            curNumber = S[curNumber];
            count += 1;
        }

        this.minCycle = count;
    }

    public void setDst(int d, int N) {
        for (int i = 0; (d + i) < N; i += 3) {
            this.dst.add(d + i);
        }

        for (int oneDst : dst) {
            if (this.minMoveCount[oneDst] != -1) {
                this.dstMoveCount.add(this.minMoveCount[oneDst]);
            }
        }
    }

    public int addShuffleCount(Map<Integer, Integer> shuffleCounts, int minCycleLcm, int N) {
        int count = 0;
        for (int moveCount : this.dstMoveCount) {
            count = moveCount;
            while (count <= minCycleLcm) {
                if (!shuffleCounts.containsKey(count)) {
                    shuffleCounts.put(count, 1);
                } else {
                    shuffleCounts.put(count, shuffleCounts.get(count) + 1);
                    if (shuffleCounts.get(count) == N) {
                        return count;
                    }
                }
                count += this.minCycle;
            }
        }
        return Integer.MAX_VALUE;
    }
}
