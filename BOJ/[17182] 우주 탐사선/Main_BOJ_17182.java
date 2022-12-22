import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_17182 {
    static int N;
    static int K;
    static int[][] dist;
    static int answer;
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        dist = new int[N][N];
        answer = Integer.MAX_VALUE;
        visit = new boolean[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                dist[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int k = 0; k < N; k++) {//거쳐가는 노드
            for (int i = 0; i < N; i++) {//시작 노드
                for (int j = 0; j < N; j++) {//도착 노드
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        visit[K] = true;
        dfs(K, 0, 1);

        System.out.println(answer);
    }

    static void dfs(int now, int sum, int depth) {
        if (sum >= answer) {
            return;
        }
        if (depth == N) {
            answer = sum;
            return;
        }

        for (int i = 0; i < N; i++) {
            if (!visit[i]) {
                visit[i] = true;
                dfs(i, sum + dist[now][i], depth + 1);
                visit[i] = false;
            }
        }
    }
}
