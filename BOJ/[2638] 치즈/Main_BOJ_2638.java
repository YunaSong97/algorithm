import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    int x;
    int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main_BOJ_2638 {
    static int N;
    static int M;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static int totalCheeseCnt;
    static int time = 0;
    static int[][] cheeses;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        cheeses = new int[N][M];
        List<Node> temp = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                cheeses[i][j] = Integer.parseInt(st.nextToken());

                if (cheeses[i][j] == 1) {
                    totalCheeseCnt++;
                }
            }
        }

        boolean[][] visit = new boolean[N][M];
        setOuts(visit, 0, 0);

        while (totalCheeseCnt > 0) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (cheeses[i][j] >= 1) {
                        setContactCount(i, j);
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (cheeses[i][j] >= 3) {
                        cheeses[i][j] = 0;
                        temp.add(new Node(i, j));
                        totalCheeseCnt--;
                    } else if (cheeses[i][j] > 0) {
                        cheeses[i][j] = 1;
                    }
                }
            }

            for (Node out : temp) {
                setOuts(visit, out.x, out.y);
            }

            temp.clear();
            time++;
        }

        System.out.println(time);
    }

    static void setOuts(boolean[][] visit, int x, int y) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(x, y));
        visit[x][y] = true;
        cheeses[x][y] = -1;

        while (!queue.isEmpty()) {
            Node now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];
                if (isIn(nx, ny) && !visit[nx][ny] && cheeses[nx][ny] <= 0) {
                    visit[nx][ny] = true;
                    queue.add(new Node(nx, ny));
                    cheeses[nx][ny] = -1;
                }
            }
        }
    }

    static void setContactCount(int x, int y) {
        for (int i = 0; i < 4; i++) {
            int nx = dx[i] + x;
            int ny = dy[i] + y;

            if (cheeses[nx][ny] == -1) {
                cheeses[x][y]++;
            }
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}
