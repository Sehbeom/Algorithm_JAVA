package boj.boj1135;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());
        st = new StringTokenizer(in.readLine());

        int[] employeeInfo = new int[N];

        for (int i = 0; i < N; i++) {
            employeeInfo[i] = Integer.parseInt(st.nextToken());
        }

        int[] depthInfo = makeDepthInfo(employeeInfo, N);
        int[] childsCountInfo = makeChildsCount(employeeInfo, N);

//        System.out.println(Arrays.toString(depthInfo));
        Employee[] employees = new Employee[N];
//        employees[0] = new Employee(0, -1, depthInfo[0], childsCountInfo[0]);
        for (int i = 1; i < N; i++) {
//            employees[i] = new Employee(i, employeeInfo[i], depthInfo[i], childsCountInfo[i]);
//            employees[employeeInfo[i]].childs.offer(employees[i]);
        }

        TreeSet<Employee>[] treeSets = new TreeSet[] {new TreeSet<Employee>(), new TreeSet<Employee>()};
        int curSet = 0;
        int nextSet = 1;
        treeSets[curSet].add(employees[0]);

        Iterator<Employee> iterator;
        Employee tmp;
        int answer = 0;
        while (!treeSets[curSet].isEmpty()) {
//            System.out.println(treeSets[curSet]);
            answer += 1;
            treeSets[nextSet].clear();
            iterator = treeSets[curSet].iterator();

            while (iterator.hasNext()) {
                tmp = iterator.next();

                if (tmp.childs.isEmpty())
                    continue;

//                treeSets[nextSet].add(tmp.childs.poll());
                if (!tmp.childs.isEmpty())
                    treeSets[nextSet].add(tmp);
            }

            curSet = (curSet + 1) % 2;
            nextSet = (nextSet + 1) % 2;
        }

        System.out.println(answer - 1);
    }

    private static int[] makeDepthInfo(int[] employeeInfo, int N) {
        int[] depthInfo = new int[N];

//        for (int i = 1; i < N; i++) {
//            depthInfo[i] = depthInfo[employeeInfo[i]] + 1;
//        }

        for (int i = N - 1; i > 0; i--) {
            depthInfo[employeeInfo[i]] = depthInfo[i] + 1;
        }

        return depthInfo;
    }

    private static int[] makeChildsCount(int[] employeeInfo, int N) {
        int[] childsCount = new int[N];

        for (int i = N - 1; i >= 1; i--) {
            childsCount[employeeInfo[i]] += (childsCount[i] + 1);
        }

        return childsCount;
    }
}

//class Employee implements Comparable<Employee> {
//    int number;
//    int parent;
//    int depth;
//    int childsCount;
//    PriorityQueue<Employee> childs = new PriorityQueue<>();
//
//    public Employee() {
//
//    }
//
//    public Employee(int number, int parent, int depth, int childsCount) {
//        this.number = number;
//        this.parent = parent;
//        this.depth = depth;
//        this.childsCount = childsCount;
//    }
//
//
//    @Override
//    public int compareTo(Employee o) {
////        if (this.number == o.number)
////            return 0;
////        else {
////            if (this.depth < o.depth)
////                return 1;
////            else if (this.depth > o.depth)
////                return -1;
////            else {
////                if (this.childsCount < o.childsCount)
////                    return 1;
////                else if (this.childsCount > o.childsCount)
////                    return -1;
////                else
////                    return 0;
////            }
////        }
//        if (this.number == o.number)
//            return 0;
//        else {
//            if (this.childsCount < o.childsCount)
//                return 1;
//            else if (this.childsCount > o.childsCount)
//                return -1;
//            else {
//                if (this.depth < o.depth)
//                    return 1;
//                else if (this.depth > o.depth)
//                    return -1;
//                else
//                    return 0;
//            }
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Employee{" +
//                "number=" + number +
//                '}';
//    }
//}