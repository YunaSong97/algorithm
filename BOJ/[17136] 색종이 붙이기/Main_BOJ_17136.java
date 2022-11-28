import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
길이가 5,4,3,2,1인 것으로 덮을 수 있는 모든 경우로 덮고,
        그다음은 남은 종이와 덮여진 배열을 넘기면서 다시 1 나오는걸 덮는다.

        배열 끝까지 다 검사 하면, 색종이 숫자 갱신한다.*/
public class Main_BOJ_17136 {
    static int[][] paper = new int[10][10];
    static int minCnt = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 10; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < 10; j++) {
                paper[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, 0);

        if (minCnt == Integer.MAX_VALUE) {
            minCnt = -1;
        }

        System.out.println(minCnt);
    }

    static int[] paperCnt = {0, 5, 5, 5, 5, 5};

    static void dfs(int cnt, int x, int y) {
        if (x == 9 && y > 9) {
            minCnt = Math.min(cnt, minCnt);
            return;
        }

        if (cnt >= minCnt) {
            return;
        }

        if (y > 9) {
            x++;
            y = 0;
        }

        if (paper[x][y] == 1) {
            for (int l = 5; l > 0; l--) {
                if (paperCnt[l] <= 0) {
                    continue;
                }

                if (isCovered(paper, l, x, y)) {

                    for (int i = 0; i < l; i++) {
                        for (int j = 0; j < l; j++) {
                            paper[x + i][y + j] = 0;
                        }
                    }

                    paperCnt[l]--;
                    dfs(cnt + 1, x, y + 1);
                    paperCnt[l]++;

                    for (int i = 0; i < l; i++) {
                        for (int j = 0; j < l; j++) {
                            paper[x + i][y + j] = 1;
                        }
                    }

                }
            }
        }else {
            dfs(cnt, x, y + 1);
        }
    }

    static boolean isCovered(int[][] temp, int len, int x, int y) {//길이 len인 색종이로 덮을 수 있는지 확인

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (!isIn(x + i, y + j)) {
                    return false;
                }

                if (temp[x + i][y + j] != 1) {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }
}
