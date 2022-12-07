import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge {
    int s;
    int e;
    long cost;

    public Edge(int s, int e, long cost) {
        this.s = s;
        this.e = e;
        this.cost = cost;
    }

    public long getCost() {
        return cost;
    }
}

class Node {
    int x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main_BOJ_10021 {
    static int N;
    static int C;
    static int[] parent;
    static Node[] nodes;
    static List<Edge> edges;
    static long answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        parent = new int[N];
        nodes = new Node[N];
        edges = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            nodes[i] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                Edge e = new Edge(i, j, (long) (Math.pow(nodes[i].x - nodes[j].x, 2) + Math.pow(nodes[i].y - nodes[j].y, 2)));
                if (e.cost >= C) {
                    edges.add(e);
                }
            }
        }

        if (edges.size() < N - 1) {
            System.out.println(-1);
            return;
        }

        edges.sort(Comparator.comparing(Edge::getCost));

        for (int i = 0; i < N; i++) {
            parent[i] = i;
        }

        kruskal();

        if (Arrays.stream(parent).distinct().count() == 1) {
            System.out.println(answer);
        } else {
            System.out.println(-1);
        }
    }

    static int find(int x) {
        if (parent[x] == x) {
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a < b) {
            parent[b] = a;
        } else {
            parent[a] = b;
        }
    }

    static void kruskal() {
        for (Edge e : edges) {
            if (find(e.s) != find(e.e)) {
                union(e.s, e.e);
                answer += e.cost;
            }
        }
    }
}
