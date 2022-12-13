import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    int x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return -x;
    }
}

public class Main_BOJ_18500 {
    static int R;
    static int C;
    static int N;
    static Character[][] cave;
    static List<Node> cluster;
    static int[] heights;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static boolean[][] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        cave = new Character[R][C];
        visit = new boolean[R][C];

        for (int i = 0; i < R; i++) {
            String str = br.readLine();

            for (int j = 0; j < C; j++) {
                cave[i][j] = str.charAt(j);
            }
        }

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        heights = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        int turn = 0;
        while (turn++ <= N - 1) {
            throwStick(heights[turn - 1], (turn - 1) % 2);

            visit = new boolean[R][C];
            fallCluster();
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                System.out.print(cave[i][j]);
            }
            System.out.println();
        }
    }

    static void throwStick(int height, int turn) {
        if (turn == 0) {
            for (int i = 0; i < C; i++) {
                if (cave[R - height][i] == 'x') {
                    cave[R - height][i] = '.';
                    return;
                }
            }
        } else if (turn == 1) {
            for (int i = C - 1; i >= 0; i--) {
                if (cave[R - height][i] == 'x') {
                    cave[R - height][i] = '.';
                    return;
                }
            }
        }
    }

    static int getHeight() {
        int h = 0;

        while (true) {
            for (Node c : cluster) {
                if (!isIn(c.x + h + 1, c.y)) {
                    return h;
                }
                int finalH = h;
                if (cluster.stream().anyMatch(mineral -> mineral.x == c.x + finalH + 1 && mineral.y == c.y)) {//본인
                    continue;
                }
                if (cave[c.x + h + 1][c.y] == 'x') {
                    return h;
                }
            }
            h++;
        }
    }

    static void fallCluster() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                cluster = new ArrayList<>();

                if (!visit[i][j] && cave[i][j] == 'x') {
                    if (isFloat(i, j)) {
                        int h = getHeight();
                        if (h == 0) {
                            continue;
                        }

                        cluster.sort(Comparator.comparing(Node::getX));
                        for (Node c : cluster) {
                            cave[c.x + h][c.y] = 'x';
                            cave[c.x][c.y] = '.';
                            visit[c.x + h][c.y] = true;
                            visit[c.x][c.y] = false;
                        }
                    }
                }
            }
        }
    }

    static boolean isFloat(int x, int y) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(x, y));
        visit[x][y] = true;
        cluster.add(new Node(x, y));

        int bottom = x;
        while (!queue.isEmpty()) {
            Node now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (isIn(nx, ny) && !visit[nx][ny] && cave[nx][ny] == 'x') {
                    queue.add(new Node(nx, ny));
                    visit[nx][ny] = true;
                    cluster.add(new Node(nx, ny));

                    bottom = Math.max(bottom, nx);
                }
            }
        }

        return bottom != R - 1;
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < R && y < C;
    }
}
