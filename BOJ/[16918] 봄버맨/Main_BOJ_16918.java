import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    int x, y;
    int time;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    static int R;
    static int C;
    static int N;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static Node[][] grid;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        grid = new Node[R][C];
        int time = 0;

        for (int i = 0; i < R; i++) {
            String str = br.readLine();

            for (int j = 0; j < C; j++) {
                if (str.charAt(j) == '.') {
                    grid[i][j] = new Node(i, j);
                    grid[i][j].time = -1;
                } else if (str.charAt(j) == 'O') {
                    grid[i][j] = new Node(i, j);
                    grid[i][j].time = time;
                }
            }
        }

        time++;

        while (time < N) {
            time++;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (grid[i][j].time == -1) {
                        grid[i][j].time = time;
                    }
                }
            }
            if (time == N) {
                break;
            }

            time++;
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if (grid[i][j].time == time - 3) {//터질차례
                        for (int d = 0; d < 4; d++) {
                            int nx = dx[d] + i;
                            int ny = dy[d] + j;

                            if (isIn(nx, ny)) {
                                if (grid[nx][ny].time != grid[i][j].time) {
                                    grid[nx][ny].time = -1;
                                }
                            }

                        }
                        grid[i][j].time = -1;
                    }
                }
            }
            if (time == N) {
                break;
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (grid[i][j].time == -1) {
                    System.out.print('.');
                } else {
                    System.out.print('O');
                }
            }
            System.out.println();
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < R && y < C;
    }
}
