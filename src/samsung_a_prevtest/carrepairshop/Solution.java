package samsung_a_prevtest.carrepairshop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int N, M, K, A, B;
        Desk[] reception;
        Desk[] repair;
        Customer[] customers;
        TreeSet<Customer> toRecep;
        TreeSet<Customer> toRepair;
        Set<Customer> answerCustom;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            st = new StringTokenizer(in.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());
            answerCustom = new HashSet<>();

            reception = new Desk[N + 1];
            repair = new Desk[M + 1];
            customers = new Customer[K + 1];
            toRecep = new TreeSet<>((o1, o2) -> o1.customerNumber - o2.customerNumber);
            toRepair = new TreeSet<>(new Comparator<Customer>() {
                @Override
                public int compare(Customer o1, Customer o2) {
                    if (o1.toRepairTime > o2.toRepairTime) return 1;
                    else if (o1.toRepairTime < o2.toRepairTime) return -1;
                    else {
                        return o1.receptionNumber - o2.receptionNumber;
                    }
                }
            });

            st = new StringTokenizer(in.readLine());
            for (int i = 1; i < N + 1; i++) {
                reception[i] = new Desk(Integer.parseInt(st.nextToken()));
            }
            st = new StringTokenizer(in.readLine());
            for (int i = 1; i < M + 1; i++) {
                repair[i] = new Desk(Integer.parseInt(st.nextToken()));
            }
            st = new StringTokenizer(in.readLine());
            for (int i = 1; i < K + 1; i++) {
                customers[i] = new Customer(i, Integer.parseInt(st.nextToken()));
            }
            int minVisitTime = customers[1].visitTime;
            for (int i = 1; i < K + 1; i++) {
                customers[i].visitTime -= minVisitTime;
            }

            boolean receptionAllEmpty = false;
            boolean repairAllEmpty = false;
            int firstCustom = 1;
            Customer custTmp;
            int time = 0;
            while (!(firstCustom == K + 1 && toRecep.isEmpty() && receptionAllEmpty && toRepair.isEmpty() && repairAllEmpty)) {
                time += 1;
                receptionAllEmpty = true;
                repairAllEmpty = true;
                for (int i = firstCustom; i < K + 1; i++) {
                    if (customers[i].visitTime == 0) {
                        toRecep.add(customers[i]);
                        firstCustom = i + 1;
                    } else {
                        customers[i].visitTime -= 1;
                    }
                }

                for (int i = 1; i < N + 1; i++) {
                    if (reception[i].spentTime > 0) {
                        receptionAllEmpty = false;
                        if (reception[i].spendOneTime()) {
                            custTmp = reception[i].usingCustomer;
                            custTmp.toRepairTime = time;
                            toRepair.add(custTmp);
                        }
                    }
                    if (reception[i].spentTime == 0) {
                        if (!toRecep.isEmpty()) {
                            receptionAllEmpty = false;
                            reception[i].spentTime = 1;
                            custTmp = toRecep.pollFirst();
                            custTmp.receptionNumber = i;
                            reception[i].usingCustomer = custTmp;
                        }
                    }
                }

                for (int i = 1; i < M + 1; i++) {
                    if (repair[i].spentTime > 0) {
                        repairAllEmpty = false;
                        repair[i].spendOneTime();
                    }
                    if (repair[i].spentTime == 0) {
                        if (!toRepair.isEmpty()) {
                            repairAllEmpty = false;
                            repair[i].spentTime = 1;
                            custTmp = toRepair.pollFirst();
                            repair[i].usingCustomer = custTmp;
                            if (custTmp.receptionNumber == A && i == B) {
                                answerCustom.add(custTmp);
                            }
                        }
                    }
                }
            }

            int answer = 0;
            if (answerCustom.size() > 0) {
                for (Customer c : answerCustom) {
                    answer += c.customerNumber;
                }
            } else
                answer = -1;
            sb.append(answer).append("\n");
        }
        System.out.println(sb);
    }
}

class Customer {

    int customerNumber;
    int visitTime;
    int receptionNumber;
    int toRepairTime;

    Customer(int customerNumber, int visitTime) {
        this.customerNumber = customerNumber;
        this.visitTime = visitTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return customerNumber == customer.customerNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber);
    }
}

class Desk {

    int requireTime;
    int spentTime;
    Customer usingCustomer;

    Desk(int requireTime) {
        this.requireTime = requireTime;
    }

    boolean spendOneTime() {
        spentTime += 1;
        if (spentTime == requireTime + 1) {
            spentTime = 0;
            return true;
        }
        return false;
    }
}
