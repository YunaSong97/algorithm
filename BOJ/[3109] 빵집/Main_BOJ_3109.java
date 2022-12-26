import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_3109 {
    static int R;
    static int C;
    static char[][] grid;
    static int answer;
    static int[] dx = {-1, 0, 1};//오위, 오, 오아
    static int[] dy = {1, 1, 1};
    static boolean[][] visit;
    static boolean[] connect;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        grid = new char[R][C];
        answer = 0;
        visit = new boolean[R][C];
        connect = new boolean[R];

        for (int i = 0; i < R; i++) {
            String str = br.readLine();

            for (int j = 0; j < C; j++) {
                grid[i][j] = str.charAt(j);
            }
        }

        for (int i = 0; i < R; i++) {
            connectGas(i, i, 0, visit);
        }

        System.out.println(answer);
    }

    static void connectGas(int start, int x, int y, boolean[][] visit) {
        if (y == C - 1) {
            connect[start] = true;
            answer++;
        }

        for (int d = 0; d < 3; d++) {
            int nx = dx[d] + x;
            int ny = dy[d] + y;

            if (isIn(nx, ny) && !visit[nx][ny] && grid[nx][ny] == '.') {
                visit[nx][ny] = true;
                connectGas(start, nx, ny, visit);

                if (connect[start]) {
                    return;
                }
            }
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < R && y < C;
    }
}
