import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

class Node {
    int x;
    int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Enemy extends Node implements Comparable<Enemy> {
    int dist;

    public Enemy(int x, int y) {
        super(x, y);
    }

    public void getDist(Node node) {
        this.dist = Math.abs(node.x - this.x) + Math.abs(node.y - this.y);
    }

    @Override
    public int compareTo(Enemy o) {
        if (o.dist == this.dist) {
            return this.y - o.y;
        }
        return this.dist - o.dist;
    }
}

class Main_BOJ_17135 {
    static int[][] map;
    static int n;
    static int m;
    static int d;
    static int ans;
    static ArrayList<Enemy> enemyList;
    static ArrayList<Enemy> delList;
    static PriorityQueue<Enemy> priorityQueue;
    static ArrayList<Enemy> copyList;

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        ans = 0;
        n = sc.nextInt();
        m = sc.nextInt();
        d = sc.nextInt();
        map = new int[n + 1][m];
        enemyList = new ArrayList<>();
        priorityQueue = new PriorityQueue<>();
        copyList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = sc.nextInt();

                if (map[i][j] == 1) {
                    enemyList.add(new Enemy(i, j));
                }
            }
        }

        boolean[] v = new boolean[m];
        combination(v, 0, 0);

        System.out.println(ans);
    }

    static void combination(boolean[] visit, int depth, int start) {
        if (depth == 3) {
            int cnt = 0;

            for (int i = 0; i < enemyList.size(); i++) {
                Enemy e = new Enemy(enemyList.get(i).x, enemyList.get(i).y);
                copyList.add(e);
            }

            delList = new ArrayList<>();
            priorityQueue.clear();

            while (copyList.size() > 0) {
                for (int i = 0; i < m; i++) {
                    if (visit[i]) {
                        findEnemy(new Node(n, i));
                    }
                }
                
                for (Enemy e : delList) {

                    if (copyList.remove(e)) {
                        cnt++;
                    }
                }

                moveEnemy();
            }

            ans = Math.max(ans, cnt);
            return;
        }

        for (int i = start; i < m; i++) {
            visit[i] = true;
            combination(visit, depth + 1, i + 1);
            visit[i] = false;
        }
    }

    static void findEnemy(Node archer) {

        for (Enemy enemy : copyList) {
            enemy.getDist(archer);
            if (enemy.dist <= d) {
                priorityQueue.add(enemy);
            }
        }

        if (!priorityQueue.isEmpty()) {
            delList.add(priorityQueue.poll());
            priorityQueue.clear();
        }
    }

    static void moveEnemy() {
        for (int i = copyList.size() - 1; i >= 0; i--) {
            copyList.get(i).x++;
            
            if (copyList.get(i).x >= n) {
                copyList.remove(i);
            }
        }
    }
}
