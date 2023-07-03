package boj.boj1043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Person[] persons = new Person[N + 1];
        for (int i = 1; i < N + 1; i++) {
            persons[i] = new Person(i);
        }
        boolean[] isKnow = new boolean[N + 1];
        boolean[] containsKnow = new boolean[M];

        st = new StringTokenizer(in.readLine());
        int numOfKnows = Integer.parseInt(st.nextToken());

        Queue<Integer> queue = new ArrayDeque<>();

        int tmp;
        for (int i = 0; i < numOfKnows; i++) {
            tmp = Integer.parseInt(st.nextToken());
            isKnow[tmp] = true;
            queue.offer(tmp);
        }

        int personTmp = 0;
        int[][] parties = new int[M][];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            parties[i] = new int[Integer.parseInt(st.nextToken())];
            for (int j = 0; j < parties[i].length; j++) {
                personTmp = Integer.parseInt(st.nextToken());
                parties[i][j] = personTmp;
                persons[personTmp].participated.add(i);
            }
        }

        System.out.println(setContainsKnow(persons, queue, isKnow, containsKnow, parties));

    }

    private static int setContainsKnow(Person[] persons, Queue<Integer> queue, boolean[] isKnow,
            boolean[] containsKnow, int[][] parties) {
        int numOfNotContainsKnow = containsKnow.length;
        int current;
        while (!queue.isEmpty()) {
            current = queue.poll();

            for (int p : persons[current].participated) {
                if (!containsKnow[p]) {
                    containsKnow[p] = true;
                    numOfNotContainsKnow -= 1;
                    for (int pt : parties[p]) {
                        if (!isKnow[pt]) {
                            isKnow[pt] = true;
                            queue.offer(pt);
                        }
                    }
                }
            }
        }

        return numOfNotContainsKnow;
    }

}

class Person {

    int number;
    List<Integer> participated = new ArrayList<>();

    public Person(int number) {
        this.number = number;
    }
}
