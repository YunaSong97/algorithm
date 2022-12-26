import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_18428 {
    static int N;
    static char[][] grid;
    static int[] dx = {-1, 0, 1, 0};//위, 오, 아, 왼
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        grid = new char[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                grid[i][j] = st.nextToken().charAt(0);
            }
        }

        setWall(0, 0, 0);

        System.out.println("NO");
    }

    static void setWall(int x, int y, int cnt) {
        if (cnt == 3) {
            if (isAvoided()) {
                System.out.println("YES");
                System.exit(0);
            }
            return;
        }

        if (y == N) {
            y = 0;
            x++;
        }
        if (x == N) {
            return;
        }

        if (grid[x][y] == 'X') {
            grid[x][y] = 'O';
            setWall(x, y + 1, cnt + 1);
            grid[x][y] = 'X';
            setWall(x, y + 1, cnt);
        } else {
            setWall(x, y + 1, cnt);
        }
    }

    static boolean isAvoided() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 'S') {
                    for (int d = 0; d < 4; d++) {
                        if (!check(d, i, j)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    static boolean check(int dir, int x, int y) {
        while (true) {
            x = x + dx[dir];
            y = y + dy[dir];

            if (!isIn(x, y)) {
                return true;
            }

            if (grid[x][y] == 'O') {
                return true;
            }

            if (grid[x][y] == 'T') {
                return false;
            }
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
}
