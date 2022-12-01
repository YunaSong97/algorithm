import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_BOJ_15684 {
    static int N, M, H;
    static boolean res;
    static int[][] ladder;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        ladder = new int[H][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            ladder[a - 1][b - 1] = 1;
        }

        for (int line = 0; line <= 3; line++) {
            //line개 추가로 선택해 사다리 만들었을 때 조건 만족하는지
            makeLadder(0, line, 0, 0);

            if (minCnt != -1) {
                break;
            }
        }

        System.out.println(minCnt);
    }

    static int minCnt = -1;

    static void makeLadder(int cnt, int r, int x, int y) {
        if (cnt == r) {
            for (int i = 0; i < N; i++) {
                move(i, 0, i);

                if (!res) {
                    return;
                }
            }

            minCnt = cnt;

            return;
        }

        if (x > H - 2 && y > N - 1) {
            return;
        }

        if (y == N) {
            makeLadder(cnt, r, x + 1, 0);
            return;
        }

        if (ladder[x][y] == 0) {
            ladder[x][y] = 1;
            makeLadder(cnt + 1, r, x, y + 1);
            ladder[x][y] = 0;
            makeLadder(cnt, r, x, y + 1);
        } else {
            makeLadder(cnt, r, x, y + 1);
        }
    }

    static void move(int start, int x, int y) {
        if (x == H) {
            res = y == start;

            return;
        }

        if (isIn(x, y) && ladder[x][y] == 1) {
            move(start, x + 1, y + 1);
        } else if (isIn(x, y - 1) && ladder[x][y - 1] == 1) {
            move(start, x + 1, y - 1);
        } else {
            move(start, x + 1, y);
        }
    }

    static boolean isIn(int x, int y) {

        return x >= 0 && y >= 0 && x < H && y < N;
    }
}
