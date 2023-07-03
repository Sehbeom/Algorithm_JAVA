package boj.boj1135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class SendNews {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        st = new StringTokenizer(in.readLine());

        Employee[] employees = new Employee[N];
        employees[0] = new Employee(0, Integer.parseInt(st.nextToken()));


        int parent;
        for (int i = 1; i < N; i++) {
            parent = Integer.parseInt(st.nextToken());
            employees[i] = new Employee(i, parent);
            employees[parent].childs.add(employees[i]);
        }

        int rootMaxTime = checkTimes(employees[0]);

        System.out.println(rootMaxTime);

    }

    private static int checkTimes(Employee curEmployee) {
        if (curEmployee.childs.isEmpty())
            return 0;

        if (curEmployee.time > 0)
            return curEmployee.time;

        PriorityQueue<Integer> sortedTimes = new PriorityQueue<>(Comparator.reverseOrder());

        for (Employee e : curEmployee.childs) {
            sortedTimes.offer(checkTimes(e));
        }

        int maxTime = 0;
        int oneTime = 0;
        int sendingTime = 1;
        while (!sortedTimes.isEmpty()) {
            oneTime = sortedTimes.poll() + sendingTime;
            if (oneTime > maxTime) {
                maxTime = oneTime;
            }
            sendingTime += 1;
        }

        curEmployee.time = maxTime;

        return maxTime;
    }

}

class Employee {
    int number;
    int parent;
    int time = 0;
    List<Employee> childs = new ArrayList<>();


    public Employee() {

    }

    public Employee(int number, int parent) {
        this.number = number;
        this.parent = parent;
    }
}
