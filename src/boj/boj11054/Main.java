package boj.boj11054;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(in.readLine());

        int[] sequence = new int[N];
        st = new StringTokenizer(in.readLine());
        for (int i = 0; i < N; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }

        if (N == 1) {
            System.out.println(1);
            return;
        }

        List<Integer> biggerSeq = new ArrayList<>();
        int biggerCount = 0;

        List<ReverseInteger> smallerSeq = new ArrayList<>();
        int smallerCount = 0;

        biggerSeq.add(sequence[0]);
        biggerCount = 1;

        smallerCount = findSmallerSeqMax(Arrays.copyOfRange(sequence, 1, N), smallerSeq);

        int answer = biggerSeq.get(0) == smallerSeq.get(0).number ? biggerCount + smallerCount - 1 : biggerCount + smallerCount;

        for (int i = 1; i < N - 1; i++) {
            smallerSeq.clear();

            biggerCount = setBiggerSeq(biggerSeq, sequence[i]);
            smallerCount = findSmallerSeqMax(Arrays.copyOfRange(sequence, i + 1, N), smallerSeq);

            answer = (biggerSeq.get(biggerSeq.size() - 1) == smallerSeq.get(0).number) ?
                Math.max(answer, biggerCount + smallerCount - 1) : Math.max(answer, biggerCount + smallerCount);
        }

        System.out.println(answer);

    }

    private static int findSmallerSeqMax(int[] subArray, List<ReverseInteger> smallerSeq) {

        smallerSeq.add(new ReverseInteger(subArray[0]));

        for (int i = 1; i < subArray.length; i++) {
            setSmallerSeq(smallerSeq, subArray[i]);
        }

        return smallerSeq.size();
    }

    private static int setBiggerSeq(List<Integer> biggerSeq, int number) {
        int searchedIndex = 0;
        if (biggerSeq.get(biggerSeq.size() - 1) > number) {
            searchedIndex = Collections.binarySearch(biggerSeq, number);
            searchedIndex = searchedIndex < 0 ? (searchedIndex + 1) * -1 : searchedIndex;
            biggerSeq.set(searchedIndex, number);
        } else if (biggerSeq.get(biggerSeq.size() - 1) < number) {
            biggerSeq.add(number);
        }

        return biggerSeq.size();
    }

    private static int setSmallerSeq(List<ReverseInteger> smallerSeq, int number) {
        int searchedIndex = 0;
        if (smallerSeq.get(smallerSeq.size() - 1).number < number) {
            ReverseInteger reverseInteger = new ReverseInteger(number);
            searchedIndex = Collections.binarySearch(smallerSeq, reverseInteger);
            searchedIndex = searchedIndex < 0 ? (searchedIndex + 1) * -1 : searchedIndex;
            smallerSeq.set(searchedIndex, reverseInteger);
        } else if (smallerSeq.get(smallerSeq.size() - 1).number > number) {
            smallerSeq.add(new ReverseInteger(number));
        }

        return smallerSeq.size();
    }
}

class ReverseInteger implements Comparable<ReverseInteger> {

    int number;

    public ReverseInteger(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(ReverseInteger o) {
        return o.number - this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReverseInteger that = (ReverseInteger) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
