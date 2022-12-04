import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int Q;
    static int[][] usados;
    static ArrayList<Integer>[] adjList;
    static boolean[] visit;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        usados = new int[N + 1][N + 1];
        adjList = new ArrayList[N + 1];
        visit = new boolean[N + 1];

        for (int i = 1; i < N + 1; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < N + 1; i++) {
            Arrays.fill(usados[i], Integer.MAX_VALUE);
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            usados[a][b] = Integer.parseInt(st.nextToken());
            usados[b][a] = usados[a][b];

            adjList[a].add(b);
            adjList[b].add(a);
        }

        getUSADO(1);

        int[] answer = new int[Q];
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());

            int k = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            int cnt = 0;
            for (int j = 1; j < N + 1; j++) {
                if (j == v) {
                    continue;
                }
                if (usados[v][j] >= k) {
                    cnt++;
                }
            }

            answer[i] = cnt;
        }

        for (int ans : answer) {
            System.out.println(ans);
        }
    }

    static void getUSADO(int idx) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(idx);
        visit[idx] = true;

        while (!queue.isEmpty()) {
            int now = queue.poll();

            for (int child : adjList[now]) {

                if (!visit[child]) {
                    int nextUsado = usados[now][child];

                    for (int i = 1; i <= N; i++) {
                        if (visit[i]) {
                            usados[i][child] = Math.min(usados[i][now], nextUsado);
                            usados[child][i] = usados[i][child];
                        }
                    }

                    visit[child] = true;
                    queue.add(child);
                }
            }
        }
    }
}
