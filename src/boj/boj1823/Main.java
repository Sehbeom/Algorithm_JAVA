package boj.boj1823;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());

        Rice[] rices = new Rice[N];
        for (int i = 0; i < N; i++) {
            rices[i] = new Rice(i, Integer.parseInt(in.readLine()));
        }

        int answer = 0;
        int currentValue = 0;
        for (int i = 0; i < N; i++) {
            currentValue = calculateEachMax(rices, i);
            answer = Math.max(answer, currentValue);
        }
        System.out.println(answer);
    }

    private static int calculateEachMax(Rice[] rices, int lastRice) {
        int leftPtr = 0;
        int rightPtr = rices.length - 1;
        int days = 1;
        int value = 0;

        while (leftPtr < rightPtr) {
            if (leftPtr == lastRice) {
                value += days * rices[rightPtr].cost;
                rightPtr -= 1;
            } else if (rightPtr == lastRice) {
                value += days * rices[leftPtr].cost;
                leftPtr += 1;
            } else {
                if (rices[leftPtr].cost <= rices[rightPtr].cost) {
                    value += days * rices[leftPtr].cost;
                    leftPtr += 1;
                } else {
                    value += days * rices[rightPtr].cost;
                    rightPtr -= 1;
                }
            }

            days += 1;
        }
        return value + (days * rices[lastRice].cost);
    }
}

class Rice {
    int number;
    int cost;

    public Rice (int number, int cost) {
        this.number = number;
        this.cost = cost;
    }
}
