import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    int e, cost, cnt;

    public Node(int e, int cost, int cnt) {
        this.e = e;
        this.cost = cost;
        this.cnt = cnt;
    }

    public Node(int e, int cnt) {
        this.e = e;
        this.cnt = cnt;
    }

    public int getCost() {
        return cost;
    }
}

public class Main_BOJ_1800 {
    static int N;
    static int P;
    static int K;
    static int answer = 1000001;
    static List<Node>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        adjList = new List[N + 1];

        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adjList[x].add(new Node(y, c, 0));
            adjList[y].add(new Node(x, c, 0));
        }


        int start = 0;
        int end = 1000001;

        while (start <= end) {
            int mid = (start + end) / 2;

            if (dijkstra(mid)) {
                answer = mid;
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        if (answer == 1000001) {
            System.out.println(-1);
        } else {
            System.out.println(answer);
        }
    }

    static boolean dijkstra(int longest) {
        int[] cost = new int[N + 1];
        Arrays.fill(cost, 1000001);
        cost[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparing(Node::getCost));
        pq.add(new Node(1,0));

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if (now.e == N) {
                return true;
            }

            for (Node n : adjList[now.e]) {
                n.cnt = now.cnt;

                if (n.cost > longest) {
                    if (n.cnt >= K) {
                        continue;
                    } else {
                        n.cnt++;
                    }
                }

                if (cost[n.e] <= n.cnt) {
                    continue;
                }
                cost[n.e] = n.cnt;

                pq.add(n);
            }
        }

        return false;
    }
}
