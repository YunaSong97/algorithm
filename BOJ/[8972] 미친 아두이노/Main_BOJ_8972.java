import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_8972 {
    static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getDist() {
            return Math.abs(this.x - JongWoo.x) + Math.abs(this.y - JongWoo.y);
        }

        public void setXY(int dir) {
            this.x = this.x + dx[dir];
            this.y = this.y + dy[dir];
        }
    }

    static int R;
    static int C;
    static int[] dx = {1, 1, 1, 0, 0, 0, -1, -1, -1};
    static int[] dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    static int[][] map;
    static int[][] temp;
    static Node JongWoo;
    static int[] dirs;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new int[R][C];
        temp = new int[R][C];

        for (int i = 0; i < R; i++) {
            String s = br.readLine();

            for (int j = 0; j < C; j++) {
                char c = s.charAt(j);

                if (c == 'R') {
                    map[i][j] = 1;
                } else if (c == 'I') {
                    JongWoo = new Node(i, j);
                    map[i][j] = -1;
                } else if (c == '.') {
                    map[i][j] = 0;
                }
            }
        }

        String s = br.readLine();
        dirs = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            dirs[i] = Character.getNumericValue(s.charAt(i)) - 1;
        }

        for (int d = 0; d < dirs.length; d++) {
            moveJongWoo(dirs[d], d + 1);

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (map[i][j] == 1) {
                        moveCrazy(i, j, d + 1);
                    }
                }
            }

            removeCrazy();

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    map[i][j] = temp[i][j];
                    temp[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] == 0) {
                    System.out.print('.');
                } else if (map[i][j] == -1) {
                    System.out.print('I');
                } else {
                    System.out.print('R');
                }
            }
            System.out.println();
        }
    }

    static void moveJongWoo(int dir, int index) {
        JongWoo.setXY(dir);

        if (map[JongWoo.x][JongWoo.y] == 1) {
            System.out.println("kraj " + index);
            System.exit(0);
        }

        temp[JongWoo.x][JongWoo.y] = -1;
    }

    static void moveCrazy(int x, int y, int index) {
        int shortest = 201;
        int dir = -1;

        for (int d = 0; d < 9; d++) {
            Node next = new Node(x + dx[d], y + dy[d]);

            if (next.getDist() < shortest) {
                dir = d;
                shortest = next.getDist();
            }
        }

        Node t = new Node(x + dx[dir], y + dy[dir]);
        if (temp[t.x][t.y] == -1) {
            System.out.println("kraj " + index);
            System.exit(0);
        } else {
            temp[t.x][t.y]++;
        }
    }

    static void removeCrazy() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (temp[i][j] >= 2) {
                    temp[i][j] = 0;
                }
            }
        }
    }
}
