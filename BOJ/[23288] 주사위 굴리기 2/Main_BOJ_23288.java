package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Node {
    int x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Dice {
    int front, back, left, right, top, bottom;
    int dir = 0;//0,1,2,3(동남서북)
    int x = 0;
    int y = 0;

    public Dice(int front, int back, int left, int right, int top, int bottom) {
        this.front = front;
        this.back = back;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public void goRight() {
        int temp = this.left;
        this.left = this.bottom;
        this.bottom = this.right;
        this.right = this.top;
        this.top = temp;
    }

    public void goLeft() {
        int temp = this.left;
        this.left = this.top;
        this.top = this.right;
        this.right = this.bottom;
        this.bottom = temp;
    }

    public void goFront() {
        int temp = this.front;
        this.front = this.top;
        this.top = this.back;
        this.back = this.bottom;
        this.bottom = temp;
    }

    public void goBack() {
        int temp = this.front;
        this.front = this.bottom;
        this.bottom = this.back;
        this.back = this.top;
        this.top = temp;
    }

    public void move(int dir, int x, int y) {
        this.x = x;
        this.y = y;
        switch (dir) {
            case 0:
                this.goRight();
                break;
            case 1:
                this.goFront();
                break;
            case 2:
                this.goLeft();
                break;
            case 3:
                this.goBack();
                break;
        }
    }

    public void turn(int A, int B) {
        if (A > B) {
            dir++;
            if (dir == 4) {
                dir = 0;
            }
        } else if (A < B) {
            dir--;
            if (dir == -1) {
                dir = 3;
            }
        }
    }
}

public class Main {
    static int N, M, K;
    static int[][] map;
    static int[] dx = {0, 1, 0, -1};//동남서북
    static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        Dice dice = new Dice(5, 2, 4, 3, 1, 6);

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int score = 0;

        while (K-- > 0) {
            int nx = dice.x + dx[dice.dir];
            int ny = dice.y + dy[dice.dir];

            if (!isIn(nx, ny)) {//칸이 없으면 방향 반대
                dice.dir = (dice.dir + 2) % 4;
                nx = dice.x + dx[dice.dir];
                ny = dice.y + dy[dice.dir];
            }

            dice.move(dice.dir, nx, ny);
            score += bfs(nx, ny) * map[nx][ny];
            dice.turn(dice.bottom, map[nx][ny]);
        }

        System.out.println(score);
    }

    static int bfs(int x, int y) {
        boolean[][] visit = new boolean[N][M];
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(x, y));
        visit[x][y] = true;
        int size = 1;

        while (!queue.isEmpty()) {
            Node now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (isIn(nx, ny) && !visit[nx][ny] && map[nx][ny] == map[x][y]) {
                    queue.add(new Node(nx, ny));
                    visit[nx][ny] = true;
                    size++;
                }
            }
        }

        return size;
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}
