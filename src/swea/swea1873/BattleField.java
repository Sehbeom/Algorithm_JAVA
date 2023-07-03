package swea.swea1873;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BattleField {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(in.readLine());
        int H;
        int W;
        char[][] map;

        int N;

        Tank tank;

        for (int t = 1; t <= T; t++) {
            sb.append("#").append(t).append(" ");

            st = new StringTokenizer(in.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new char[H][W];
            tank = new Tank();
            String tmp;
            for (int i = 0; i < H; i++) {
                tmp = in.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = tmp.charAt(j);
                    if (tmp.charAt(j) >= 60) {
                        for (int k = 0; k < 4; k++) {
                            if (tmp.charAt(j) == tank.tankSymbol[k]) {
                                tank.x = i;
                                tank.y = j;
                                tank.direction = k;
                            }
                        }
                    }
                }
            }

            N = Integer.parseInt(in.readLine());
            tmp = in.readLine();
            for (int i = 0; i < N; i++)
                tank.commandTank(map, tmp.charAt(i));

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }
}

class Tank {
    int x;
    int y;
    int direction;
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    char[] tankSymbol = {'^', 'v', '<', '>'};

    void commandTank(char[][] map, char command) {
        if (command == 'S') {
            shootCommand(map);
        } else {
            if (command == 'U') direction = 0;
            else if (command == 'D') direction = 1;
            else if (command == 'L') direction = 2;
            else direction = 3;
            moveCommand(map);
        }
    }

    void shootCommand(char[][] map) {
        int cannonBallX = x + dx[direction];
        int cannonBallY = y + dy[direction];

        while (-1 < cannonBallX && cannonBallX < map.length &&
        -1 < cannonBallY && cannonBallY < map[0].length) {
            if (map[cannonBallX][cannonBallY] == '*') {
                map[cannonBallX][cannonBallY] = '.';
                break;
            }

            if (map[cannonBallX][cannonBallY] == '#') {
                break;
            }

            cannonBallX = cannonBallX + dx[direction];
            cannonBallY = cannonBallY + dy[direction];
        }
    }

    void moveCommand(char[][] map) {
        int curX = x + dx[direction];
        int curY = y + dy[direction];

        if (-1 < curX && curX < map.length &&
        -1 < curY && curY < map[0].length &&
        map[curX][curY] == '.') {
            map[x][y] = '.';
            x = curX;
            y = curY;
        }
        map[x][y] = tankSymbol[direction];
    }
}