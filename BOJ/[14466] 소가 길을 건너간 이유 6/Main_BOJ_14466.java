import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Cow {
    int x, y;

    public Cow(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Ground {
    boolean[] road;

    public Ground() {
        this.road = new boolean[4];
    }
}

public class Main_BOJ_14466 {
    static int N;
    static int K;
    static int R;
    static Ground[][] grounds;
    static int[] dx = {-1, 1, 0, 0}; //상, 하, 우, 좌
    static int[] dy = {0, 0, 1, -1};
    static List<Cow> cows = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        grounds = new Ground[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grounds[i][j] = new Ground();
            }
        }

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int rp = Integer.parseInt(st.nextToken()) - 1;
            int cp = Integer.parseInt(st.nextToken()) - 1;

            grounds[r][c].road[getDir(r, c, rp, cp)] = true;
            grounds[rp][cp].road[getDir(rp, cp, r, c)] = true;
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            cows.add(new Cow(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1));
        }

        int answer = 0;
        for (int i = 0; i < K; i++) {
            for (int j = i + 1; j < K; j++) {
                boolean[][] visit = new boolean[N][N];
                if (!bfs(visit, cows.get(i), cows.get(j))) {
                    answer++;
                }
            }
        }

        System.out.println(answer);
    }

    static boolean bfs(boolean[][] visit, Cow start, Cow end) {
        Queue<Cow> queue = new ArrayDeque<>();
        queue.add(start);
        visit[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Cow now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (isIn(nx, ny) && !visit[nx][ny] && !grounds[now.x][now.y].road[i]) {
                    if (nx == end.x && ny == end.y) {
                        return true;
                    }

                    visit[nx][ny] = true;
                    queue.add(new Cow(nx, ny));
                }
            }
        }

        return false;
    }

    static int getDir(int r, int c, int rp, int cp) {
        for (int i = 0; i < 4; i++) {
            if (rp == r + dx[i] && cp == c + dy[i]) {
                return i;
            }
        }
        return -1;
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
}
