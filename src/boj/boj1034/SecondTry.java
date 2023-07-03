package boj.boj1034;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class SecondTry {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        String tmp;
        int zeroCount = 0;
        Map<Lamp, Integer> lampCount = new HashMap<>();
        for (int i = 0; i < N; i++) {
            tmp = in.readLine();
            zeroCount = 0;
            Lamp lamp = new Lamp(tmp);

            if (lampCount.containsKey(lamp)) {
                zeroCount = lampCount.get(lamp);
                lampCount.put(lamp, zeroCount + 1);
            } else {
                for (int j = 0; j < M; j++) {
                    if (tmp.charAt(j) == '0') {
                        zeroCount += 1;
                    }
                }
                lamp.zeroCount = zeroCount;
                lampCount.put(lamp, 1);
            }
        }

        int K = Integer.parseInt(in.readLine());
        int answer = 0;
        for (Map.Entry<Lamp, Integer> me : lampCount.entrySet()) {
            zeroCount = me.getKey().zeroCount;
            if ((zeroCount > K) || ((zeroCount % 2) != (K % 2))) {
                continue;
            }
            if (answer < me.getValue()) {
                answer = me.getValue();
            }
        }

        System.out.println(answer);
    }
}

class Lamp {

    String pattern;
    int zeroCount;

    public Lamp(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lamp lamp = (Lamp) o;
        return Objects.equals(pattern, lamp.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern);
    }
}
