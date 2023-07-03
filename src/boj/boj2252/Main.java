package boj.boj2252;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Student[] students = new Student[N + 1];

        for (int i = 1; i <= N; i++) {
            students[i] = new Student(i);
        }

        int[] numOfShorterStudents = new int[N + 1];
        Queue<Integer> shortestStudents = new ArrayDeque<>();

        int A;
        int B;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());

            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());

            students[A].tallerStudents.add(B);
            numOfShorterStudents[B] += 1;
        }

        for (int i = 1; i <= N; i++) {
            if (numOfShorterStudents[i] == 0) {
                shortestStudents.offer(i);
            }
        }

        int current;
        int tallerStudent;
        StringBuilder sb = new StringBuilder();
        while (!shortestStudents.isEmpty()) {
            current = shortestStudents.poll();
            sb.append(current).append(" ");

            while (!students[current].tallerStudents.isEmpty()) {
                tallerStudent = students[current].tallerStudents.poll();
                numOfShorterStudents[tallerStudent] -= 1;

                if (numOfShorterStudents[tallerStudent] == 0) {
                    shortestStudents.offer(tallerStudent);
                }
            }
        }

        System.out.println(sb);
    }
}

class Student {
    int number;
    Queue<Integer> tallerStudents = new ArrayDeque<>();

    public Student(int number) {
        this.number = number;
    }
}
